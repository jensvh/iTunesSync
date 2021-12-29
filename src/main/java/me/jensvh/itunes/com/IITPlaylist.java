package me.jensvh.itunes.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jacob.com.Dispatch;

public class IITPlaylist extends IITObject {

    public IITPlaylist(Dispatch d) {
        super(d);
    }
    
    @SuppressWarnings("serial")
    private static final List<String> defaultPlaylists = Collections.unmodifiableList(new ArrayList<String>() {{
        add("Music");
        add("Movies");
        add("TV Shows");
        add("Podcasts");
        add("Audiobooks");
        add("Genius");
    }});
    
    /**
     * Delete this object.
     */
    public void delete() {
        Dispatch.call(object, "Delete");
    }

    public ITPlaylistKind getKind() {
        return ITPlaylistKind.values()[Dispatch.get(object, "Kind").getInt()];
    }
    
    public boolean isVisible() {
        return Dispatch.get(object, "Visible").getBoolean();
    }
    
    public boolean isUserPlaylist() {
        return (getKind() == ITPlaylistKind.ITPlaylistKindUser
                && !defaultPlaylists.contains(getName()));
    }
    
    public IITrackCollection getTracks() {
        Dispatch playlists = Dispatch.get(object, "Tracks").toDispatch();
        return new IITrackCollection(playlists);
    }
    
    
    
}
