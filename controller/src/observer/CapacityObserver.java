package observer;

import cargoManagement.CargoStorage;
import domainLogicManagement.ManagementFacade;

public class CapacityObserver implements Observer {
    private final ManagementFacade managementFacade;
    private boolean exceeded;

    public CapacityObserver(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    @Override
    public void update() {
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        double percentageOfMaxSize = cargoStorage.getCapacity() * 0.9;
        boolean[] usedStorageLocationsArray = cargoStorage.getUsedStorageLocations();
        int usedStorageLocations = 0;
        for (boolean usedStorageLocation : usedStorageLocationsArray) {
            if (usedStorageLocation) {
                usedStorageLocations++;
            }
        }
        if (usedStorageLocations > percentageOfMaxSize && !exceeded) {
            exceeded = true;
            System.out.println("90% of storage capacity exceeded");
        }
        if (usedStorageLocations <= percentageOfMaxSize) {
            exceeded = false;
        }
    }
}
