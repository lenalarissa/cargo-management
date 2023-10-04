package persistenceController;
import persistence.JOS;
import org.junit.jupiter.api.Test;

import java.util.EventObject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoadJOSListenerTest {

    @Test
    void onEvent_test_if_decode_is_called() {
        JOS jos = mock(JOS.class);
        LoadJOSListener loadJOSListener = new LoadJOSListener(jos);
        loadJOSListener.onEvent(new EventObject(this));
        verify(jos).deserialize();
    }
}