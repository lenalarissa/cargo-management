package cargoManagement;

import cargo.Cargo;

import java.util.Date;

interface CargoInternal extends Cargo {
    void setLastInspectionDate(Date lastInspectionDate);
    void setStorageLocation(int storageLocation);
}
