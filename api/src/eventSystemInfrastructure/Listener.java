package eventSystemInfrastructure;

import java.io.Serializable;
import java.util.EventObject;

public interface Listener<E extends EventObject> extends Serializable {
    void onEvent(E event);
}
