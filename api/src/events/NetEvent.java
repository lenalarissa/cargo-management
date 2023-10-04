package events;

import java.util.EventObject;

public class NetEvent extends EventObject {

    private final EventObject eventObject;
    private final char controlChar;

    public NetEvent(Object source, EventObject eventObject, char controlChar) {
        super(source);
        this.eventObject = eventObject;
        this.controlChar = controlChar;
    }

    public EventObject getEventObject() {
        return eventObject;
    }

    public char getControlChar() {
        return controlChar;
    }
}
