package events;

import java.util.EventObject;

public class ContainedHazardsEvent extends EventObject {
    private final boolean contained;

    public ContainedHazardsEvent(Object source, boolean contained) {
        super(source);
        this.contained = contained;
    }

    public boolean isContained() {
        return contained;
    }
}
