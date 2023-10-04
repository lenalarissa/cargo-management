package domainLogicController;

import administration.Customer;
import customerManagement.CustomerManagement;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import log.ActionDLEvent;
import events.CustomerListEvent;
import events.NetEvent;
import log.ActionDL;
import domainLogicManagement.ManagementFacade;

import java.util.EventObject;
import java.util.HashSet;

public class ReadCustomersListener implements Listener<EventObject> {

    private final ManagementFacade managementFacade;
    private Handler<CustomerListEvent> returnCustomersHandler;
    private Handler<ActionDLEvent> writeLogHandler;
    private Handler<NetEvent> serverHandler;

    public ReadCustomersListener(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    public void setReturnCustomersHandler(Handler<CustomerListEvent> returnCustomersHandler) {
        this.returnCustomersHandler = returnCustomersHandler;
    }

    public void setWriteLogHandler(Handler<ActionDLEvent> writeLogHandler) {
        this.writeLogHandler = writeLogHandler;
    }

    public void setServerHandler(Handler<NetEvent> serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        CustomerManagement customerManagement = managementFacade.getCustomerManagement();
        HashSet<Customer> customers = customerManagement.readCustomers();
        CustomerListEvent customerListEvent = new CustomerListEvent(this, customers);
        if (returnCustomersHandler != null) {
            returnCustomersHandler.handle(customerListEvent);
            if (writeLogHandler != null) {
                writeLogHandler.handle(new ActionDLEvent(this, ActionDL.READCUSTOMERS));
            }
        } else if (serverHandler != null) {
            NetEvent netEvent = new NetEvent(this, customerListEvent, 'r');
            serverHandler.handle(netEvent);
        }
    }
}
