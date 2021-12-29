package me.jensvh.itunessync;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import me.jensvh.itunes.com.IITPlaylist;
import me.jensvh.itunes.com.IITTrack;
import me.jensvh.itunes.com.IITrackCollection;

public class Utils {

    @SuppressWarnings("serial")
    private static final List<String> foldersToIgnore = Collections.unmodifiableList(new ArrayList<String>() {{
        add("iTunes");
        add("MEmu Music");
        add("spotdl-temp");
    }});
    
    public static FileFilter filterPlaylistFolders = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.isDirectory() && !foldersToIgnore.contains(file.getName());
        }
    };
    
    public static FilenameFilter filterSongs = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".mp3");
        }
    };
    
    public static boolean equalSongs(IITTrack track, File song) {
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
            String trackName = Optional.ofNullable(track.getName()).orElse("");
            String trackArtist = Optional.ofNullable(track.getArtist()).orElse("");
            String trackAlbum = Optional.ofNullable(track.getAlbum()).orElse("");
            
            return tagTitle.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(trackName.replaceAll("[^A-Za-z0-9]", ""))
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
    
    public static boolean trackExistInPlaylist(File song, IITPlaylist playlist) {
        IITrackCollection tracks = playlist.getTracks();
        int trackCount = tracks.getCount();
        
        for (int i = 1; i <= trackCount; i++) {
            IITTrack track = tracks.getItem(i);
            
            if (equalSongs(track, song))
                return true;
            
        }
        
        return false;
    }
    
}
