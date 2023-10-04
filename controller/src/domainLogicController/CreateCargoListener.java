package domainLogicController;

import cargoManagement.CargoStorage;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import log.ActionDLEvent;
import events.CargoEvent;
import events.NetEvent;
import log.ActionDL;
import domainLogicManagement.ManagementFacade;

import java.util.EventObject;

public class CreateCargoListener implements Listener<CargoEvent> {

    private final ManagementFacade managementFacade;
    private Handler<ActionDLEvent> writeLogHandler;
    private Handler<NetEvent> serverHandler;

    public CreateCargoListener(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    public void setWriteLogHandler(Handler<ActionDLEvent> writeLogHandler) {
        this.writeLogHandler = writeLogHandler;
    }

    public void setServerHandler(Handler<NetEvent> serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(CargoEvent event) {
        if (writeLogHandler != null) {
            writeLogHandler.handle(new ActionDLEvent(this, ActionDL.CREATECARGO));
        }
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        boolean result = cargoStorage.createCargo(event.getCargoClass(), event.getOwner(), event.getValue(), event.getHazards(), event.isFragile(), event.isPressurized(), event.getGrainSize());
        if (serverHandler != null) {
            NetEvent netEvent = new NetEvent(this, new EventObject(this), 'x');
            serverHandler.handle(netEvent);
        }
        if(result) {
            if (writeLogHandler != null) {
                writeLogHandler.handle(new ActionDLEvent(this, ActionDL.CARGOCREATED));
                writeLogHandler.handle(new ActionDLEvent(this, ActionDL.NUMOFCARGOSSET));
            }
        }
    }
}
