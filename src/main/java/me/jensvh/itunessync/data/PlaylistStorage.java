package me.jensvh.itunessync.data;

import java.util.HashSet;
import java.util.Set;

import me.jensvh.itunes.com.IITPlaylist;
import me.jensvh.itunes.com.IITTrack;
import me.jensvh.itunes.com.IITrackCollection;

public class PlaylistStorage {
    
    private Set<TrackStorage> tracks;
    private int count;
    private boolean isUserPlaylist = false;
    
    public PlaylistStorage(IITPlaylist playlist) {
        IITrackCollection collection = playlist.getTracks();
        count = collection.getCount();
        
        tracks = new HashSet<TrackStorage>(count, 1.0f);
        isUserPlaylist = playlist.isUserPlaylist();
        
        for (int i = 0; i < count; i++) {
            IITTrack track = collection.getItem(i+1);
            tracks.add(new TrackStorage(track));
        }
        System.out.println("Tracks initialised, count: " + count);
    }

    public boolean contains(TrackStorage song) {
        return tracks.contains(song);
    }

    public boolean isUserPlaylist() {
        return isUserPlaylist;
    }
    
}
