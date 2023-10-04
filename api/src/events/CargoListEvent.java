package events;

import cargo.Cargo;

import java.util.EventObject;
import java.util.HashMap;

public class CargoListEvent extends EventObject {

    private final HashMap<Integer, Cargo> cargos;

    public CargoListEvent(Object source, HashMap<Integer, Cargo> cargos) {
        super(source);
        this.cargos = cargos;
    }

    public HashMap<Integer, Cargo> getCargos() {
        return cargos;
    }
}
