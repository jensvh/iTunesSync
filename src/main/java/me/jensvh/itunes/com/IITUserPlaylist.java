package me.jensvh.itunes.com;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class IITUserPlaylist extends IITPlaylist {

    public IITUserPlaylist(IITPlaylist d) {
        super(d.object);
    }
    
    
    public ITUserPlaylistSpecialKind getSpecialKind() {
        return ITUserPlaylistSpecialKind.values()[Dispatch.get(object, "Kind").getInt()];
    }
    
    public boolean addFile(String path) {
        Variant variant = Dispatch.call(object, "AddFile", path);
        
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (variant.isNull()) {
            return false;
        }
        return true;
    }
    
    public boolean addFiles(String[] path) {
        Variant variant = Dispatch.call(object, "AddFiles", (Object[]) path);
        if (variant.isNull()) {
            return false;
        }
        return true;
    }

}
