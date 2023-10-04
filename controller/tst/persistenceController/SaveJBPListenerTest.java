package persistenceController;

import persistence.JBP;
import org.junit.jupiter.api.Test;

import java.util.EventObject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SaveJBPListenerTest {

    @Test
    void onEvent_test_if_decode_is_called() {
        JBP jbp = mock(JBP.class);
        SaveJBPListener saveJBPListener = new SaveJBPListener(jbp);
        saveJBPListener.onEvent(new EventObject(this));
        verify(jbp).encode();
    }
}