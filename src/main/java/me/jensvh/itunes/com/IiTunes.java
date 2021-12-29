package me.jensvh.itunes.com;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class IiTunes {
    
    private ActiveXComponent iTunes;
    
    public IiTunes() {
        iTunes = new ActiveXComponent("iTunes.Application");
    }
    
    public IITSource getLibrarySource() {
        Variant variant = iTunes.getProperty("LibrarySource");
        
        if (variant.isNull()) {
            return null;
        }
        Dispatch item = variant.toDispatch();
        return new IITSource(item);
    }
    
    public IITPlaylist createPlaylist(String name) {
        Variant variant = Dispatch.call(iTunes, "CreatePlaylist", name);
        if (variant.isNull()) {
            return null;
        }
        Dispatch item = variant.toDispatch();
        return new IITPlaylist(item);
    }
    
    public void close() {
        iTunes.safeRelease();
    }
    
}
