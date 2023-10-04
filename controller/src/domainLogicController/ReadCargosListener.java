package domainLogicController;

import cargo.Cargo;
import cargoManagement.CargoStorage;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import log.ActionDLEvent;
import events.CargoClassEvent;
import events.CargoListEvent;
import events.NetEvent;
import log.ActionDL;
import domainLogicManagement.ManagementFacade;

import java.util.HashMap;

public class
ReadCargosListener implements Listener<CargoClassEvent> {

    private final ManagementFacade managementFacade;
    private Handler<CargoListEvent> returnCargosHandler;
    private Handler<ActionDLEvent> writeLogHandler;
    private Handler<NetEvent> serverHandler;

    public ReadCargosListener(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    public void setReturnCargosHandler(Handler<CargoListEvent> returnCargosHandler) {
        this.returnCargosHandler = returnCargosHandler;
    }

    public void setWriteLogHandler(Handler<ActionDLEvent> writeLogHandler) {
        this.writeLogHandler = writeLogHandler;
    }

    public void setServerHandler(Handler<NetEvent> serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(CargoClassEvent event) {
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos(event.getCargoClass());
        CargoListEvent cargoListEvent = new CargoListEvent(this, cargos);
        if (returnCargosHandler != null) {
            returnCargosHandler.handle(cargoListEvent);
            if (writeLogHandler != null) {
                writeLogHandler.handle(new ActionDLEvent(this, ActionDL.READCARGOS));
            }
        } else if (serverHandler != null) {
            NetEvent netEvent = new NetEvent(this, cargoListEvent, 'r');
            serverHandler.handle(netEvent);
        }
    }
}
