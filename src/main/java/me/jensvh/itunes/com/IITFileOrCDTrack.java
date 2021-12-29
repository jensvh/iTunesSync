package me.jensvh.itunes.com;

import com.jacob.com.Dispatch;

public class IITFileOrCDTrack extends IITTrack {

    public IITFileOrCDTrack(IITTrack d) {
        super(d.object);
    }
    
    public String getLocation() {
        return Dispatch.get(object, "Location").getString();
    }

}
