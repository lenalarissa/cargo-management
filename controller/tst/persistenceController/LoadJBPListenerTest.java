package persistenceController;

import cargoManagement.CargoStorageDecoder;
import cargoManagement.CargoStorageEncoder;
import customerManagement.CustomerManagementDecoder;
import domainLogicManagement.ManagementFacade;
import eventSystemInfrastructure.Handler;
import events.NetEvent;
import persistence.JBP;
import org.junit.jupiter.api.Test;

import java.util.EventObject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoadJBPListenerTest {

    @Test
    void onEvent_test_if_decode_is_called() {
        JBP jbp = mock(JBP.class);
        LoadJBPListener loadJBPListener = new LoadJBPListener(jbp);
        loadJBPListener.onEvent(new EventObject(this));
        verify(jbp).decode();
    }

    @Test
    void onEvent_test_if_handler_is_set_and_handle_is_called() {
        JBP jbp = new JBP(mock(ManagementFacade.class), new CargoStorageEncoder(), new CargoStorageDecoder(new CustomerManagementDecoder()));
        LoadJBPListener loadJBPListener = new LoadJBPListener(jbp);
        Handler<NetEvent> handler = mock(Handler.class);
        loadJBPListener.setServerHandler(handler);
        loadJBPListener.onEvent(new EventObject(this));
        verify(handler).handle(any());
    }
}