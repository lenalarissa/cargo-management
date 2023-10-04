package events;

import cargo.Hazard;

import java.math.BigDecimal;
import java.util.EventObject;
import java.util.HashSet;

public class CargoEvent extends EventObject {

    private final String cargoClass;
    private final String owner;
    private final BigDecimal value;
    private final HashSet<Hazard> hazards;
    private final boolean fragile;
    private final boolean pressurized;
    private final int grainSize;

    public CargoEvent(Object source, String cargoClass, String owner, BigDecimal value, HashSet<Hazard> hazards, boolean fragile, boolean pressurized, int grainSize) {
        super(source);
        this.cargoClass = cargoClass;
        this.owner = owner;
        this.value = value;
        this.hazards = hazards;
        this.fragile = fragile;
        this.pressurized = pressurized;
        this.grainSize = grainSize;
    }

    public String getCargoClass() {
        return cargoClass;
    }

    public BigDecimal getValue() {
        return value;
    }

    public HashSet<Hazard> getHazards() {
        return hazards;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isFragile() {
        return fragile;
    }

    public boolean isPressurized() {
        return pressurized;
    }

    public int getGrainSize() {
        return grainSize;
    }
}
