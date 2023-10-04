package observer;

import cargoManagement.CargoStorage;
import domainLogicManagement.ManagementFacade;

public class CargosObserver implements Observer {

    private final ManagementFacade managementFacade;

    public CargosObserver(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    @Override
    public void update() {
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        boolean[] usedStorageLocationsArray = cargoStorage.getUsedStorageLocations();
        int usedStorageLocations = 0;
        for (boolean usedStorageLocation : usedStorageLocationsArray) {
            if (usedStorageLocation) {
                usedStorageLocations++;
            }
        }
        System.out.println("cargos in warehouse: " + usedStorageLocations);
    }
}
