package persistenceController;

import persistence.JOS;
import org.junit.jupiter.api.Test;

import java.util.EventObject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SaveJOSListenerTest {

    @Test
    void onEvent_test_if_decode_is_called() {
        JOS jos = mock(JOS.class);
        SaveJOSListener saveJOSListener = new SaveJOSListener(jos);
        saveJOSListener.onEvent(new EventObject(this));
        verify(jos).serialize();
    }
}