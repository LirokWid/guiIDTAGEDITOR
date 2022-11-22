package com.example.guiidtageditor;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class Mp3 {
    private String ID3FileIdent;
    private byte[] ID3Version;
    private String StrId3MainVersion;
    private byte[] ID3Flags;
    private byte[] ID3SizeBytes;
    private int ID3Size;
    private boolean ID3Unsynchronisation;
    private boolean ID3Compression;

    private byte[] ID3Frame;
    private File mp3File;
    private byte[] mp3Content;

    //List IDv3 tags in an array
    ArrayList<V3Frame> frameArrayList = new ArrayList<>();
    private int Id3RevVersion;


    public byte[] getContent() {
        return this.mp3Content;
    }
    public Mp3(File mp3File) throws Exception {
        String extension = mp3File.toString().substring(mp3File.toString().length()-4);
        if(!extension.equals(".mp3"))//Proceed if file is a mp3 file
        {
            throw new Exception("File in not in .mp3 format");
        }else{
            this.mp3File = mp3File;
            try {
                this.mp3Content = getMp3Content();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Populate ID3 ident variables from ID3 header
            getID3IdentVar();

            //Get ID3 tag frame
            this.ID3Frame = Arrays.copyOfRange(this.mp3Content, 10, this.ID3Size - 10);
            //Get data frame depending on ID3 version
            switch (this.StrId3MainVersion)
            {
                case "ID3V2.2" -> getID3v22Frames();
                case "ID3V2.3" -> getID3v23Frames();
                case "ID3V2.4" -> getID3v24Frames();
                default -> throw new Exception("ID3 version not supported");
            }
        }
    }
    private void getID3v22Frames()
    {
        System.out.println("Parsing ID3v2.2 frames");
        throw new UnsupportedOperationException("ID3v2.2 not supported yet");
    }
    private void getID3v23Frames() throws IOException {
        System.out.println("Parsing ID3v2.3 frames");
        byte[] frame = this.ID3Frame;
        byte[] tagFrame;
        byte[] frameHeader;
        byte[] ident;
        byte[] size;
        byte[] flags;

        int bytesNb=0;
        int count=0;

        //Get the first frame
        frameHeader = Arrays.copyOfRange(frame, 0, 10);
        ident= Arrays.copyOfRange(frameHeader, 0, 4);
        size = Arrays.copyOfRange(frameHeader, 4, 8);
        flags= Arrays.copyOfRange(frameHeader, 8, 10);

        //loop until the end of all frames
        while (isIdentAscii(ident))
        {
            //get frame size
            bytesNb= ((size[0]&0xFF)<<24)+((size[1]&0xFF)<<16)+((size[2]&0xFF)<<8)+(size[3]&0xFF)+10;
            //get frame data from size
            tagFrame = Arrays.copyOfRange(frame, 0, bytesNb);
            System.out.println("Frame header : "+new String(ident));
            //add frame to list
            frameArrayList.add(new V3Frame(new String(ident),tagFrame,this.StrId3MainVersion));
            //subtract frame size from frame
            frame = Arrays.copyOfRange(frame, tagFrame.length, frame.length);

            frameHeader = Arrays.copyOfRange(frame, 0, 10);
            //getHeadersVar
            ident= Arrays.copyOfRange(frameHeader, 0, 4);
            size = Arrays.copyOfRange(frameHeader, 4, 8);
            flags= Arrays.copyOfRange(frameHeader, 8, 10);
            count++;
        }

    }
    private void getID3v24Frames()
    {
        System.out.println("Parsing ID3v2.4 frames");
    }


    public V3Frame findFrameByName(String name)
    {
        for (V3Frame frame : frameArrayList) {
            if(frame.getName().equals(name))
            {
                return frame;
            }
        }
        return null;
    }
    private boolean isIdentAscii(byte[] ident) {
        for (byte b : ident) {
            if (b < 0x20 || b > 0x7E) {
                return false;
            }
        }
        return true;
    }

    private void getDataFrames()
    {
    }

    private void getID3Flags(byte[] ID3Flags) {
        if((ID3Flags[0] & 128) == 1)
        {
            this.ID3Unsynchronisation = true;
        }else{
            this.ID3Unsynchronisation = false;
        }
        if((ID3Flags[0] & 64) == 1)
        {
            this.ID3Compression = true;
        }else{
            this.ID3Compression = false;
        }
    }

    private void getID3IdentVar() {
        // Get the ID3 frame identifiers
        this.ID3FileIdent = new String(mp3Content, 0, 3,Charset.defaultCharset());

        // Get the ID3 version
        this.ID3Version = Arrays.copyOfRange(mp3Content, 3, 5);

        // Get the ID3Flags
        this.ID3Flags = Arrays.copyOfRange(mp3Content, 5, 6);

        //get the ID3 frame size
        this.ID3Size = getID3FrameSize(Arrays.copyOfRange(mp3Content, 6, 10));

        //get the ID3 version as a string
        switch (this.ID3Version[0]) {
            case 2 -> {
                this.StrId3MainVersion = "ID3V2.2";
                this.Id3RevVersion = this.ID3Version[1];
            }
            case 3 ->
            {
                this.StrId3MainVersion = "ID3V2.3";
                this.Id3RevVersion = this.ID3Version[1];
            }
            case 4 -> {
                this.StrId3MainVersion = "ID3V2.4";
                this.Id3RevVersion = this.ID3Version[1];
            }
            default -> {
                this.StrId3MainVersion = "Unknown";
                throw new IllegalStateException("Unexpected value in tag version : " + this.ID3Version[0]);
            }
        }
        //TODO manage flags ffs
        //get the ID3 flags
        getID3Flags(this.ID3Flags);
    }




    private int getID3FrameSize(byte[] sizeField)
    {
        /*
        Size is encoded in 7 bits on four bytes
        Mask the first bit of each byte (7bit) and add to get the size
        +10 to include header size (10 bytes)
        */
        return ((sizeField[0]&0x7F)<<21)+((sizeField[1]&0x7F)<<14)+((sizeField[2]&0x7F)<<7)+(sizeField[3]&0x7F)+10;
    }

    private @NotNull byte[] getMp3Content() throws IOException
    {
        return Files.readAllBytes(mp3File.toPath());
    }

    //Getters
    public String getID3Version()
    {
        return this.StrId3MainVersion;
    }
    public File getMp3File ()
    {
        return mp3File;
    }
    public String getStrId3MainVersion()
    {
        return this.StrId3MainVersion;
    }
    public int getID3Size()
    {
        return this.ID3Size;
    }
    public byte[] getID3Frame()
    {
        return this.ID3Frame;
    }
}


