package me.jensvh.itunessync;

import java.io.File;
import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import me.jensvh.itunes.com.IITPlaylist;
import me.jensvh.itunes.com.IITSource;
import me.jensvh.itunes.com.IITUserPlaylist;
import me.jensvh.itunes.com.IiTunes;
import me.jensvh.itunessync.data.PlaylistStorage;
import me.jensvh.itunessync.data.TrackStorage;

public class SyncItunesToMusicFolder {

    // TODO: sync all playlists from spotify
    // Or use a different list, some alfabetic one
    public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException {
        // Start itunes connection
        ComImpl.initialize();
        
        IiTunes itunes = new IiTunes();
        IITSource library = itunes.getLibrarySource();
        System.out.println("Connection to iTunes established.");
        
        // Get playlist folders
        File currentFolder = new File(System.getProperty("user.home") + "/Music"); // <=> System.getProperty("user.dir")
        File[] folders = currentFolder.listFiles(Utils.filterPlaylistFolders);
        int folderCount = folders.length;
        
        // Loop through all playlist folders
        for (int i = 0; i < folderCount; i++) {
            File folder = folders[i];
            String playListName = folder.getName();
            
            // get songs for playlist
            // TODO: it seems there is a max on this +-136
            File[] songs = folder.listFiles(Utils.filterSongs);
            int songCount = songs.length;
            System.out.println(songCount);
            // Check if playlist already exist, if not create one
            IITPlaylist playlist = library.getPlaylists().getItemByName(playListName);
            if (playlist == null) {
                playlist = itunes.createPlaylist(playListName);
                System.out.println("playlist created.");
            } else if (!playlist.isUserPlaylist())
                continue;
            PlaylistStorage storage = new PlaylistStorage(playlist);
            
            for (int j = 0; j < songCount; j++) {
                File song = songs[j];
                TrackStorage track = new TrackStorage(song);
                
                // Check if this song already exist in playlist, if not add to playlist
                if (!storage.contains(track) && storage.isUserPlaylist()) {
                    IITUserPlaylist userPlaylist = new IITUserPlaylist(playlist);

                    userPlaylist.addFile(song.getPath());
                }
            }
            
            System.out.println(playListName);
        }
        
        itunes.close();
    }
    
}
