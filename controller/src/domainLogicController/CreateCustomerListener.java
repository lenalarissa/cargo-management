package domainLogicController;

import customerManagement.CustomerManagement;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import log.ActionDLEvent;
import events.CustomerEvent;
import events.NetEvent;
import log.ActionDL;
import domainLogicManagement.ManagementFacade;

import java.util.EventObject;

public class CreateCustomerListener implements Listener<CustomerEvent> {

    private final ManagementFacade managementFacade;
    private Handler<ActionDLEvent> writeLogHandler;
    private Handler<NetEvent> serverHandler;

    public CreateCustomerListener(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    public void setWriteLogHandler(Handler<ActionDLEvent> writeLogHandler) {
        this.writeLogHandler = writeLogHandler;
    }

    public void setServerHandler(Handler<NetEvent> serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(CustomerEvent event) {
        if (writeLogHandler != null) {
            writeLogHandler.handle(new ActionDLEvent(this, ActionDL.CREATECUSTOMER));
        }
        CustomerManagement customerManagement = managementFacade.getCustomerManagement();
        boolean result = customerManagement.createCustomer(event.getName());
        if (serverHandler != null) {
            NetEvent netEvent = new NetEvent(this, new EventObject(this), 'x');
            serverHandler.handle(netEvent);
        }
        if (result) {
            if (writeLogHandler != null) {
                writeLogHandler.handle(new ActionDLEvent(this, ActionDL.CUSTOMERCREATED));
            }
        }
    }
}
