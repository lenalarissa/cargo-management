package events;

import cargo.Hazard;

import java.util.EventObject;
import java.util.HashSet;

public class HazardListEvent extends EventObject {

    private final HashSet<Hazard> hazards;

    public HazardListEvent(Object source, HashSet<Hazard> hazards) {
        super(source);
        this.hazards = hazards;
    }

    public HashSet<Hazard> getHazards() {
        return hazards;
    }
}
