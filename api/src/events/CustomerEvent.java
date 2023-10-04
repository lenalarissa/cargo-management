package events;

import java.util.EventObject;

public class CustomerEvent extends EventObject {
    private final String name;

    public CustomerEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
