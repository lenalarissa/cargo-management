package domainLogicController;

import cargoManagement.CargoStorage;
import customerManagement.CustomerManagement;
import eventSystemInfrastructure.Handler;
import events.CustomerListEvent;
import domainLogicManagement.ManagementFacade;
import events.NetEvent;
import log.ActionDLEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.EventObject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class ReadCustomersListenerTest {

    private Number monitor = new Integer(0);
    private ReadCustomersListener readCustomersListener;
    private EventObject eventObject;

    @BeforeEach
    void setUp() {
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        CargoStorage cargoStorage = new CargoStorage(5, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);
        readCustomersListener = new ReadCustomersListener(managementFacade);
        eventObject = new EventObject(this);
    }

    // Testing Reading Customers using onEvent()-method
    @Test
    void on_event_tests_if_read_customers_was_called_verify_true() {
        CustomerManagement customerManagementMock = mock(CustomerManagement.class);
        CargoStorage cargoStorage = new CargoStorage(5, customerManagementMock, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagementMock);
        ReadCustomersListener customersListener = new ReadCustomersListener(managementFacade);
        customersListener.onEvent(eventObject);
        Mockito.verify(customerManagementMock, Mockito.times(1)).readCustomers();
    }

    // Testing setReturnCustomersHandler()
    @Test
    void set_return_customers_handler_test_if_handle_is_called_verify_true() {
        Handler<CustomerListEvent> returnCustomersHandler = mock(Handler.class);
        readCustomersListener.setReturnCustomersHandler(returnCustomersHandler);
        EventObject eventObject = new EventObject(this);
        readCustomersListener.onEvent(eventObject);
        Mockito.verify(returnCustomersHandler, Mockito.times(1)).handle(any());
    }

    @Test
    void on_event_test_if_server_handler_is_called_verify_true() {
        Handler<NetEvent> serverHandler = mock(Handler.class);
        readCustomersListener.setServerHandler(serverHandler);

        EventObject eventObject = new EventObject(this);
        readCustomersListener.onEvent(eventObject);
        Mockito.verify(serverHandler, Mockito.times(1)).handle(any());
    }

    @Test
    void on_event_test_if_log_handler_is_called_verify_true() {
        Handler<CustomerListEvent> returnCustomersHandler = new Handler<>();
        readCustomersListener.setReturnCustomersHandler(returnCustomersHandler);

        Handler<ActionDLEvent> logHandler = mock(Handler.class);
        readCustomersListener.setWriteLogHandler(logHandler);

        EventObject eventObject = new EventObject(this);
        readCustomersListener.onEvent(eventObject);
        Mockito.verify(logHandler, Mockito.times(1)).handle(any());
    }
}