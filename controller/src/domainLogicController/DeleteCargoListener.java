package domainLogicController;

import cargoManagement.CargoStorage;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import log.ActionDLEvent;
import events.NetEvent;
import events.StorageLocationEvent;
import log.ActionDL;
import domainLogicManagement.ManagementFacade;

import java.util.EventObject;

public class DeleteCargoListener implements Listener<StorageLocationEvent> {
    private final ManagementFacade managementFacade;
    private Handler<ActionDLEvent> writeLogHandler;
    private Handler<NetEvent> serverHandler;

    public DeleteCargoListener(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    public void setWriteLogHandler(Handler<ActionDLEvent> writeLogHandler) {
        this.writeLogHandler = writeLogHandler;
    }

    public void setServerHandler(Handler<NetEvent> serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(StorageLocationEvent event) {
        if (writeLogHandler != null) {
            writeLogHandler.handle(new ActionDLEvent(this, ActionDL.DELETECARGO));
        }
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        boolean result = cargoStorage.deleteCargo(event.getStorageLocation());
        if (serverHandler != null) {
            NetEvent netEvent = new NetEvent(this, new EventObject(this), 'x');
            serverHandler.handle(netEvent);
        }
        if(result){
            if (writeLogHandler != null) {
                writeLogHandler.handle(new ActionDLEvent(this, ActionDL.CARGODELETED));
            }
        }
    }
}
