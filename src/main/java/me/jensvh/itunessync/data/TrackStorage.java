package me.jensvh.itunessync.data;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Optional;
import java.util.function.Supplier;

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
    
    public TrackStorage(final File file) {
        try {
            Mp3File mp3 = new Mp3File(file);
            
            if (!mp3.hasId3v2Tag()) {
                System.out.println("Mp3 tag not supported");
                return;
            }
            ID3v2 tag = mp3.getId3v2Tag();
            
            title = Optional.ofNullable(tag.getTitle()).orElseGet(new Supplier<String>() {
                public String get() {
                    return file.getName().replaceFirst("[.][^.]+$", "");
                }
            });
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
        
        boolean titleRet = Normalizer.normalize(this.title, Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(Normalizer.normalize(other.title, Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9]", ""));
        boolean artistRet = Normalizer.normalize(this.artist, Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(Normalizer.normalize(other.artist, Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9]", ""));
        boolean albumRet = Normalizer.normalize(this.album, Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(Normalizer.normalize(other.album, Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9]", ""));
        
        /* Debug
            String thisArtists = Normalizer.normalize(this.artist, Normalizer.Form.NFD);
            String otherArtists = Normalizer.normalize(other.artist, Normalizer.Form.NFD);
            
            System.out.println(" --- Equals --- ");
            System.out.println(this.hashCode() + " vs " + other.hashCode());
            System.out.println(this.title.replaceAll("[^A-Za-z0-9]", "") + " vs " + other.title.replaceAll("[^A-Za-z0-9]", "") + " -> " + titleRet);
            System.out.println(thisArtists.replaceAll("[^A-Za-z0-9]", "") + " vs " + otherArtists.replaceAll("[^A-Za-z0-9]", "") + " -> " + artistRet);
            System.out.println(this.album.replaceAll("[^A-Za-z0-9]", "") + " vs " + other.album.replaceAll("[^A-Za-z0-9]", "") + " -> " + albumRet);
            System.out.println((titleRet && artistRet && albumRet));
        */
        
        return titleRet && artistRet && albumRet;
        
    }

    // Because a hashset uses hashcode to check equality
    @Override
    public int hashCode() {
        //System.out.println(" --- hashCode ---");
        //System.out.println(this.title.hashCode());
        //System.out.println(this.artist.hashCode());
        //System.out.println((this.title.hashCode() + this.artist.hashCode()));
        return this.title.replaceAll("[^A-Za-z0-9]", "").hashCode() + this.artist.replaceAll("[^A-Za-z0-9]", "").hashCode();
    }
}
