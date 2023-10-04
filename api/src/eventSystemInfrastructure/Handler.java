package eventSystemInfrastructure;

import java.util.EventObject;
import java.util.HashSet;

public class Handler<E extends EventObject> {
    private final HashSet<Listener<E>> listenerList = new HashSet<>();

    public void add(Listener<E> listener) {
        listenerList.add(listener);
    }

    /*
    public void remove(Listener<E> listener){
        listenerList.remove(listener);
    }
     */
    public void handle(E event) {
        for (Listener<E> listener : listenerList) {
            listener.onEvent(event);
        }
    }
}
