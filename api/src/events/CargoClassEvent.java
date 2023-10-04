package events;

import java.util.EventObject;

public class CargoClassEvent extends EventObject {
    private final String cargoClass;

    public CargoClassEvent(Object source, String cargoClass) {
        super(source);
        this.cargoClass = cargoClass;
    }

    public String getCargoClass() {
        return cargoClass;
    }
}
