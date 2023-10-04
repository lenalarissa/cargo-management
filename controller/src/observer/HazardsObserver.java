package observer;

import cargo.Hazard;
import cargoManagement.CargoStorage;
import domainLogicManagement.ManagementFacade;

import java.util.HashSet;

public class HazardsObserver implements Observer {

    private final ManagementFacade managementFacade;
    private HashSet<Hazard> hazards = new HashSet<>();

    public HazardsObserver(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    public void update() {
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        HashSet<Hazard> newHazards = cargoStorage.readHazards(true);
        if (hazards.size() != newHazards.size()) {
            System.out.println("Hazards changed to: " + newHazards);
        }
        hazards = newHazards;
    }
}
