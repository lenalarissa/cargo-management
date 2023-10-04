package events;

import java.util.EventObject;

public class StorageLocationEvent extends EventObject {

    private final int storageLocation;

    public StorageLocationEvent(Object source, int storageLocation) {
        super(source);
        this.storageLocation = storageLocation;
    }

    public int getStorageLocation() {
        return storageLocation;
    }
}
