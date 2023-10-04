package eventSystemInfrastructure;

import org.junit.jupiter.api.Test;

import java.util.EventObject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HandlerTest {

    @Test
    void add() {
        Handler<EventObject> handler = new Handler<>();
        Listener<EventObject> listener = mock(Listener.class);
        handler.add(listener);
        EventObject eventObject = new EventObject(this);
        handler.handle(eventObject);
        verify(listener).onEvent(eventObject);
    }
}