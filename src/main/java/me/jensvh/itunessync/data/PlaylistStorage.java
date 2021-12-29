package me.jensvh.itunessync.data;

import java.io.File;

import me.jensvh.itunes.com.IITPlaylist;
import me.jensvh.itunes.com.IITTrack;
import me.jensvh.itunes.com.IITrackCollection;

public class PlaylistStorage {
    
    private TrackStorage[] tracks;
    private int count;
    private boolean isUserPlaylist = false;
    
    public PlaylistStorage(IITPlaylist playlist) {
        IITrackCollection collection = playlist.getTracks();
        count = collection.getCount();
        
        tracks = new TrackStorage[count];
        isUserPlaylist = playlist.isUserPlaylist();
        
        for (int i = 0; i < count; i++) {
            IITTrack track = collection.getItem(i+1);
            tracks[i] = new TrackStorage(track);
        }
        System.out.println("Tracks initialised, count: " + count);
    }

    public boolean contains(File song) {
        for (int i = 0; i < count; i++) {
            if (tracks[i].equals(song))
                return true;
        }
        return false;
    }

    public boolean isUserPlaylist() {
        return isUserPlaylist;
    }
    
}
