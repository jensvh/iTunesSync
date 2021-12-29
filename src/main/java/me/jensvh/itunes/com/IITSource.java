package me.jensvh.itunes.com;

import com.jacob.com.Dispatch;

public class IITSource extends IITObject {

    public IITSource(Dispatch d) {
        super(d);
    }
    
    public IITPlaylistCollection getPlaylists() {
        Dispatch playlists = Dispatch.get(object, "Playlists").toDispatch();
        return new IITPlaylistCollection(playlists);
    }

}