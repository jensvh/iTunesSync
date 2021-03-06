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
        System.out.println("Linking..");
        ComImpl.initialize();
        
        IiTunes itunes = new IiTunes();
        IITSource library = itunes.getLibrarySource();
        System.out.println("Connection to iTunes established.");
        
        // Get playlist folders
        File currentFolder = new File(System.getProperty("user.dir")); // <=> System.getProperty("user.dir")
        //File currentFolder = new File("C:\\Users\\ikke\\Music");
        System.out.println(currentFolder.getPath());
        File[] folders = currentFolder.listFiles(Utils.filterPlaylistFolders);
        int folderCount = folders.length;
        
        // Loop through all playlist folders
        for (int i = 0; i < folderCount; i++) {
            System.out.println();
            File folder = folders[i];
            String playListName = folder.getName();
            
            // get songs for playlist
            // TODO: it seems there is a max on this +-136
            File[] songs = folder.listFiles(Utils.filterSongs);
            int songCount = songs.length;
            System.out.println("Playlist " + playListName + " has " + songCount + " songs.");
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
                //System.out.println(storage.contains(track));
                //System.out.println(storage.isUserPlaylist());
                //System.out.println();
                
                if (!storage.contains(track) && storage.isUserPlaylist()) {
                    IITUserPlaylist userPlaylist = new IITUserPlaylist(playlist);
                    
                    // Print some stats out
                    /*System.out.println(" --- Song added ---");
                    System.out.println(track.getTitle() + " -> " + track.getTitle().replaceAll("[^A-Za-z0-9]", ""));
                    System.out.println(track.getArtist() + " -> " + track.getArtist().replaceAll("[^A-Za-z0-9]", ""));
                    System.out.println(track.getAlbum() + " -> " + track.getAlbum().replaceAll("[^A-Za-z0-9]", ""));
                    */
                    userPlaylist.addFile(song.getPath());
                    //System.out.println("A song has been added.");
                }
                
            }
            
            System.out.println(playListName);
        }
        
        itunes.close();
    }
    
}
