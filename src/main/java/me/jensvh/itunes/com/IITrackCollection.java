package me.jensvh.itunes.com;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class IITrackCollection {
    
    private Dispatch object;

    public IITrackCollection(Dispatch d) {
        object = d;
    }
    
    /**
     * Returns the number of playlists in the collection.
     *
     * @return Returns the number of playlists in the collection.
     */
    public int getCount() {
        return Dispatch.get(object, "Count").getInt();
    }

    /**
     * Returns an ITArtwork object corresponding to the given index (1-based).
     *
     * @param index Index of the playlist to retrieve, must be less than or
     *              equal to <code>ITArtworkCollection.getCount()</code>.
     * @return Returns an ITArtwork object corresponding to the given index.
     * Will be set to NULL if no playlist could be retrieved.
     */
    public IITTrack getItem(int index) {
        Variant variant = Dispatch.call(object, "Item", index);
        if (variant.isNull()) {
            return null;
        }
        Dispatch item = variant.toDispatch();
        return new IITTrack(item);
    }
    
    public IITTrack getItemByName(String name) {
        Variant variant = Dispatch.call(object, "ItemByName", name);
        if (variant.isNull()) {
            return null;
        }
        Dispatch item = variant.toDispatch();
        return new IITTrack(item);
    }
    
    public IITTrack getItemByPersistentID(int highID, int lowID) {
        Variant variant = Dispatch.call(object, "ItemByPersistentID", highID, lowID);
        if (variant.isNull()) {
            return null;
        }
        Dispatch item = variant.toDispatch();
        return new IITTrack(item);
    }

}
