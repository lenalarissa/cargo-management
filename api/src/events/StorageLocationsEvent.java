package events;

import java.util.EventObject;

public class StorageLocationsEvent extends EventObject {

    private final int storageLocation1;
    private final int storageLocation2;

    public StorageLocationsEvent(Object source, int storageLocation1, int storageLocation2) {
        super(source);
        this.storageLocation1 = storageLocation1;
        this.storageLocation2 = storageLocation2;
    }

    public int getStorageLocation1() {
        return storageLocation1;
    }

    public int getStorageLocation2() {
        return storageLocation2;
    }
}
