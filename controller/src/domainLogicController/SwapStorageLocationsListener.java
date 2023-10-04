package domainLogicController;

import cargoManagement.CargoStorage;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import log.ActionDLEvent;
import events.StorageLocationsEvent;
import log.ActionDL;
import domainLogicManagement.ManagementFacade;

public class SwapStorageLocationsListener implements Listener<StorageLocationsEvent> {

    private final ManagementFacade managementFacade;
    private Handler<ActionDLEvent> writeLogHandler;

    public SwapStorageLocationsListener(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    public void setWriteLogHandler(Handler<ActionDLEvent> writeLogHandler) {
        this.writeLogHandler = writeLogHandler;
    }

    @Override
    public void onEvent(StorageLocationsEvent event) {
        if (writeLogHandler != null) {
            writeLogHandler.handle(new ActionDLEvent(this, ActionDL.SWAPSTORAGELOCATIONS));
        }
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        boolean result = cargoStorage.swapStorageLocations(event.getStorageLocation1(), event.getStorageLocation2());
        if(result){
            if (writeLogHandler != null) {
                writeLogHandler.handle(new ActionDLEvent(this, ActionDL.CARGOCHANGED));
            }
        }
    }
}
