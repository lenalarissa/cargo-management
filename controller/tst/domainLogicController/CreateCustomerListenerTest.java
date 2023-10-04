package domainLogicController;

import cargoManagement.CargoStorage;
import customerManagement.CustomerManagement;
import eventSystemInfrastructure.Handler;
import events.CustomerEvent;
import domainLogicManagement.ManagementFacade;
import events.NetEvent;
import log.ActionDLEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class CreateCustomerListenerTest {

    private Number monitor = new Integer(0);
    private CreateCustomerListener createCustomerListener;
    private ManagementFacade managementFacade;

    @BeforeEach
    void setUp(){
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        CargoStorage cargoStorage = new CargoStorage(5, customerManagement, monitor);
        managementFacade = new ManagementFacade(cargoStorage, customerManagement);
        createCustomerListener = new CreateCustomerListener(managementFacade);
    }

    // Testing creating customers using onEvent()-method
    @Test
    void on_event_tests_if_get_name_was_called_verify_true() {
        CustomerEvent customerEvent = mock(CustomerEvent.class);
        createCustomerListener.onEvent(customerEvent);
        Mockito.verify(customerEvent, Mockito.times(1)).getName();
    }

    @Test
    void on_event_tests_if_create_customers_was_called_verify_true() {
        CustomerManagement customerManagementMock = mock(CustomerManagement.class);
        CargoStorage cargoStorageAlt = new CargoStorage(5, customerManagementMock, monitor);
        ManagementFacade managementFacadeAlt = new ManagementFacade(cargoStorageAlt, customerManagementMock);

        CreateCustomerListener createCustomerListener = new CreateCustomerListener(managementFacadeAlt);
        CustomerEvent customerEvent = new CustomerEvent(this, "Emil");
        createCustomerListener.onEvent(customerEvent);
        Mockito.verify(customerManagementMock, Mockito.times(1)).createCustomer(customerEvent.getName());
    }
    @Test
    void on_event_tests_if_write_log_handler_is_called_twice_verify_true() {
        Handler<ActionDLEvent> handler = mock(Handler.class);
        CreateCustomerListener createCustomerListener = new CreateCustomerListener(managementFacade);
        createCustomerListener.setWriteLogHandler(handler);
        CustomerEvent customerEvent = mock(CustomerEvent.class);
        createCustomerListener.onEvent(customerEvent);
        Mockito.verify(handler, Mockito.times(2)).handle(any());
    }
    @Test
    void on_event_tests_if_server_handler_is_called_verify_true() {
        Handler<NetEvent> handler = mock(Handler.class);
        CreateCustomerListener createCustomerListener = new CreateCustomerListener(managementFacade);
        createCustomerListener.setServerHandler(handler);
        CustomerEvent customerEvent = mock(CustomerEvent.class);
        createCustomerListener.onEvent(customerEvent);
        Mockito.verify(handler, Mockito.times(1)).handle(any());
    }
}