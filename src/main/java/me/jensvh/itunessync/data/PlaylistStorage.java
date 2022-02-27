package me.jensvh.itunessync.data;

import java.util.ArrayList;
import java.util.List;

import me.jensvh.itunes.com.IITPlaylist;
import me.jensvh.itunes.com.IITTrack;
import me.jensvh.itunes.com.IITrackCollection;

public class PlaylistStorage {
    
    private List<TrackStorage> tracks;
    private int count;
    private boolean isUserPlaylist = false;
    
    public PlaylistStorage(IITPlaylist playlist) {
        IITrackCollection collection = playlist.getTracks();
        count = collection.getCount();
        
        tracks = new ArrayList<TrackStorage>(count);
        isUserPlaylist = playlist.isUserPlaylist();
        
        for (int i = 0; i < count; i++) {
            IITTrack track = collection.getItem(i+1);
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
