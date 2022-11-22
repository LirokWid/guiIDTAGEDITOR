package com.example.guiidtageditor;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.lang.System.exit;

public class V3Frame {
    private String ident;
    private String frameVersion;
    private Image image;
    private final String[] id3Tags = {"APIC","APIC-1","APIC-2","APIC-3","COMM","GRP1","IPLS","ITNU","MCDI","MVIN","MVNM","OWNE","PCNT","PCST","POPM","PRIV","SYLT","TALB","TBPM","TCAT","TCMP","TCOM","TCON","TCOP","TDAT","TDES","TDLY","TENC","TEXT","TFLT","TGID","TIME","TIT1","TIT2","TIT3","TKEY","TKWD","TLAN","TLEN","TMED","TOAL","TOFN","TOLY","TOPE","TORY","TOWN","TPE1","TPE2","TPE3","TPE4","TPOS","TPUB","TRCK","TRDA","TRSN","TRSO","TSIZ","TSO2","TSOC","TSRC","TSSE","TXXX","TYER","USER","USLT","WCOM","WCOP","WFED","WOAF","WOAR","WOAS","WORS","WPAY","WPUB","WXXX","XDOR","XOLY","XSOA","XSOP","XSOT"};

    private byte[] tagFrame;
    private int length;

    private String textData;

    public V3Frame(String ident, byte[] tagFrame, String strId3MainVersion) throws IOException {
        if (ident.length() != 4) {
            throw new IllegalArgumentException("Frame identifier must be 4 characters long");
        }
        if (tagFrame == null) {
            throw new IllegalArgumentException("Frame data cannot be null");
        }
        if(!Arrays.asList(id3Tags).contains(ident)){
            throw new IllegalArgumentException("Frame identifier must be a valid ID3 tag");
        }
        this.ident = ident;
        this.tagFrame = tagFrame;

        manageFrame();
    }



    private void manageFrame()
    {//Decide what to do depending on the frame identifier
        switch (this.ident)
        {
            case "APIC", "APIC-1", "APIC-2", "APIC-3":
                try {
                    createAPICPicture();
                }catch (Exception e){
                    e.printStackTrace();
                    exit(1);
                }
                break;
            case "TPE1":
                createTextValue();
                break;
            default:
                createTextValue();
                break;
        }

    }

    private void createTextValue()
    {//TODO: Create a string value from the frame data
        //Get encoding from tag start
        byte encoding = this.tagFrame[11];
        //print encoding for debug
        System.out.printf("Encoding: %s%n", encoding);
        switch (encoding) {
            case 0 ->
                //ISO-8859-1
                    this.textData = new String(this.tagFrame, 11, this.tagFrame.length - 12, StandardCharsets.ISO_8859_1);
            case 1 ->
                //UTF-16
                    this.textData = new String(this.tagFrame, 11, this.tagFrame.length - 12, StandardCharsets.UTF_16);
            case 2 ->
                //UTF-16BE
                    this.textData = new String(this.tagFrame, 11, this.tagFrame.length - 12, StandardCharsets.UTF_16BE);
            case 3 ->
                //UTF-8
                    this.textData = new String(this.tagFrame, 11, this.tagFrame.length - 12, StandardCharsets.UTF_8);
            default ->
                //ISO-8859-1
                    this.textData = new String(this.tagFrame, 11, this.tagFrame.length - 12, StandardCharsets.UTF_8);
        }
    }

    public String getTextData()
    {
        return this.textData;
    }

    private void createAPICPicture() throws IOException {
        System.out.println("APIC");
        System.out.println(this.tagFrame.length);
        //check flags in header
        byte[] flags = Arrays.copyOfRange(this.tagFrame, 8, 10);

        Image image = null;
        if (flags[0] == 0x00 && flags[1] == 0x00) {//no compression and no extended frame header -> create image
            byte[] data = Arrays.copyOfRange(this.tagFrame, 27, this.tagFrame.length);
            try {
                InputStream in = new ByteArrayInputStream(data);
                image = new Image(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.image = image;
    }

    //return the frame identifier
    public String getName()
    {
        return this.ident;
    }

    //return the frame image
    public Image getImage()
    {
        return this.image;
    }

}
