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
        title = Optional.ofNullable(track.getName()).orElse("");
        artist = Optional.ofNullable(track.getArtist()).orElse("");
        album = Optional.ofNullable(track.getAlbum()).orElse("");
    }
    
    public TrackStorage(File file) {
        try {
            Mp3File mp3 = new Mp3File(file);
            
            if (!mp3.hasId3v2Tag()) {
                System.out.println("Mp3 tag not supported");
                return;
            }
            ID3v2 tag = mp3.getId3v2Tag();
            
            title = Optional.ofNullable(tag.getTitle()).orElse("");
            artist = Optional.ofNullable(tag.getArtist()).orElse("");
            album = Optional.ofNullable(tag.getAlbum()).orElse("");
            
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        TrackStorage other = (TrackStorage) o;
            
        return title.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(other.title.replaceAll("[^A-Za-z0-9]", ""))
                && artist.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(other.artist.replaceAll("[^A-Za-z0-9]", ""))
                && album.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(other.album.replaceAll("[^A-Za-z0-9]", ""));
    }
}
