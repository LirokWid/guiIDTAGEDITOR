package com.example.guiidtageditor;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class Mp3file {
    //TODO: Add all the fields that are in the ID3v2.3.0 standard
    //TODO: Add a method to write the fields to a file
    //TODO: Add a method to read the fields from a file
    //TODO: Add a method to read the fields from a file and return a mp3file object
    enum mp3Version {
        ID3v1,
        ID3v2
    }

    private File mp3File;
    private String mp3Content;
    ArrayList<String> mp3tags;
    //List IDv3 tags in an array
    private final String[] id3Tags = {"TIT2", "TPE1", "TALB", "TYER", "TRCK", "TCON", "TPE2", "TPOS", "TCOM", "TPE3", "TPE4", "TOPE", "TEXT", "TOLY", "TCOP", "TPUB", "TENC", "TBPM", "TLEN", "TKEY", "TLAN", "TIT1", "TIT3", "TIT4", "TOFN", "TDLY", "TDEN", "TDOR", "TDRC", "TDRL", "TDTG", "TIPL", "TMCL", "TMOO", "TPRO", "TSOA", "TSOP", "TSOT", "TSST", "TSRC", "TSSE", "TSST", "TXXX", "WCOM", "WCOP", "WOAF", "WOAR", "WOAS", "WORS", "WPAY", "WPUB", "WXXX"};
    private String[] id3TagsContent = new String[id3Tags.length];


    public Mp3file(File mp3File) {
        this.mp3File = mp3File;
        this.mp3Content = getMp3Content();
        this.mp3tags = new ArrayList<String>();

        this.mp3tags = listTags();
    }


        public File getMp3File () {
            return mp3File;
        }

        public void setMp3File (File mp3File){
            this.mp3File = mp3File;
        }

        private @NotNull String getMp3Content() {

            StringBuilder line = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(mp3File));

                while (br.readLine() != null) {
                    line.append(br.readLine());
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return line.toString();
        }

        private ArrayList<String> listTags () {
            ArrayList<String> tagList = new ArrayList<String>();
            try {
                for (String tag:id3Tags) {
                    if (mp3Content.contains(tag)) {
                        System.out.println(tag);
                        tagList.add(tag);
                    }
                }
                }catch (Exception e){
                    e.printStackTrace();
                }
            return tagList;
        }
/*
        private String getMp3Tag (String tag){
            switch (tag) {
                case "TIT2":
                    return mp3tags[0];
                case "TPE1":
                    return mp3tags[1];
                case "TALB":
                    return mp3tags[2];
                case "TYER":
                    return mp3tags[3];
                case "TRCK":
                    return mp3tags[4];
                case "TCON":
                    return mp3tags[5];
                case "APIC":
                    return mp3tags[6];
                default:
                    return null;


                Title = "TIT2",
                        Artist = "TPE1",
                        AlbumArtist = "TPE2",
                        Album = "TALB",
                        Key = "TKEY",
                        BPM = "TBPM",
                        Genre = "TCON",
                        Label = "TPUB",
                        Style = "STYLE",
                        ISRC = "TSRC",
                        CatalogNumber = "CATALOGNUMBER",
                        Version = "TIT3",
                        TrackNumber = "TRCK",
                        Duration = "TLEN",
                        Remixer = "TPE4",
                        Mood = "TMOO",
                        TrackTotal = "TRCK",
                        DiscNumber = "TPOS"
            }


            /// Convert to VORBIS frame name
            pub fn vorbis( & self) -> &'static str {
            match self {
                Title = "TITLE",
                        Artist = "ARTIST",
                        AlbumArtist = "ALBUMARTIST",
                        Album = "ALBUM",
                        Key = "INITIALKEY",
                        BPM = "BPM",
                        Genre = "GENRE",
                        Label = "LABEL",
                        Style = "STYLE",
                        ISRC = "ISRC",
                        CatalogNumber = "CATALOGNUMBER",
                        Version = "SUBTITLE",
                        TrackNumber = "TRACKNUMBER",
                        Duration = "LENGTH",
                        Remixer = "REMIXER",
                        Mood = "MOOD",
                        TrackTotal = "TRACKTOTAL",
                        DiscNumber = "DISCNUMBER",
            }
        }

        /// Convert to MP4 frame name
        pub fn mp4( & self) -> &'static str {
        match self {
            Title = "©nam",
                    Artist = "©ART",
                    AlbumArtist = "aART",
                    Album = "©alb",
                    BPM = "tmpo",
                    Genre = "©gen",
                    Label = "com.apple.iTunes:LABEL",
                    ISRC = "com.apple.iTunes:ISRC",
                    CatalogNumber = "com.apple.iTunes:CATALOGNUMBER",
                    Version = "desc",
                    TrackNumber = "trkn",
                    Remixer = "com.apple.iTunes:REMIXER",
                    Key = "com.apple.iTunes:KEY",
                    Style = "com.apple.iTunes:STYLE",
                    Duration = "com.apple.iTunes:LENGTH",
                    Mood = "com.apple.iTunes:MOOD",
                    TrackTotal = "trkn",
                    DiscNumber = "disk",
        }*/
    }


