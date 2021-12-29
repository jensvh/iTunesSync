package me.jensvh.itunessync.data;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import me.jensvh.itunes.com.IITTrack;

public class TrackStorage {
    
    private String title;
    private String artist;
    private String album;
    
    public TrackStorage(IITTrack track) {
        title = track.getName();
        artist = track.getArtist();
        album = track.getAlbum();
    }
    
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public boolean equals(File song) {
        try {
            Mp3File mp3 = new Mp3File(song);
            
            if (!mp3.hasId3v2Tag()) {
                System.out.println("Mp3 tag not supported");
                return false; // Not supported
            }
            ID3v2 tag = mp3.getId3v2Tag();
            
            String tagTitle = Optional.ofNullable(tag.getTitle()).orElse("");
            String tagArtist = Optional.ofNullable(tag.getArtist()).orElse("");
            String tagAlbum = Optional.ofNullable(tag.getAlbum()).orElse("");
            
            String trackTitle = Optional.ofNullable(tag.getTitle()).orElse("");
            String trackArtist = Optional.ofNullable(tag.getArtist()).orElse("");
            String trackAlbum = Optional.ofNullable(tag.getAlbum()).orElse("");
            
            return tagTitle.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(trackTitle.replaceAll("[^A-Za-z0-9]", ""))
                    && tagArtist.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(trackArtist.replaceAll("[^A-Za-z0-9]", ""))
                    && tagAlbum.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(trackAlbum.replaceAll("[^A-Za-z0-9]", ""));
            
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
