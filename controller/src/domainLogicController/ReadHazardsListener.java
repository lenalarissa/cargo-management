package domainLogicController;

import cargo.Hazard;
import cargoManagement.CargoStorage;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import log.ActionDLEvent;
import events.ContainedHazardsEvent;
import events.HazardListEvent;
import events.NetEvent;
import log.ActionDL;
import domainLogicManagement.ManagementFacade;

import java.util.HashSet;

public class ReadHazardsListener implements Listener<ContainedHazardsEvent> {


    private final ManagementFacade managementFacade;
    private Handler<HazardListEvent> returnHazardsHandler;
    private Handler<ActionDLEvent> writeLogHandler;
    private Handler<NetEvent> serverHandler;

    public ReadHazardsListener(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    public void setReturnHazardsHandler(Handler<HazardListEvent> returnHazardsHandler) {
        this.returnHazardsHandler = returnHazardsHandler;
    }

    public void setWriteLogHandler(Handler<ActionDLEvent> writeLogHandler) {
        this.writeLogHandler = writeLogHandler;
    }

    public void setServerHandler(Handler<NetEvent> serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(ContainedHazardsEvent event) {
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        HashSet<Hazard> hazards = cargoStorage.readHazards(event.isContained());
        HazardListEvent hazardListEvent = new HazardListEvent(this, hazards);
        if (returnHazardsHandler != null) {
            returnHazardsHandler.handle(hazardListEvent);
            if (writeLogHandler != null) {
                writeLogHandler.handle(new ActionDLEvent(this, ActionDL.READHAZARDS));
            }
        } else if (serverHandler != null) {
            NetEvent netEvent = new NetEvent(this, hazardListEvent, 'r');
            serverHandler.handle(netEvent);
        }
    }
}
