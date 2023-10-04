package cargoManagement;

import customerManagement.CustomerManagementPojo;

import java.util.HashMap;

public class CargoStoragePojo {
    public CustomerManagementPojo customerManagementPojo;
    public int capacity;
    public HashMap<Integer, CargoPojo> cargos;
    public boolean[] usedStorageLocations;
}
