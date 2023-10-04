package simulations;

import cargo.Cargo;
import eventSystemInfrastructure.CargosSettable;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;

import java.util.HashMap;

public class WatchCargoStorage implements Runnable, CargosSettable {

    private final Object monitor;
    private Handler<CargoClassEvent> readCargosHandler;
    private HashMap<Integer, Cargo> cargos;

    public WatchCargoStorage(Object monitor) {
        this.monitor = monitor;
    }

    public void setReadCargosHandler(Handler<CargoClassEvent> readCargosHandler) {
        this.readCargosHandler = readCargosHandler;
    }

    public void setCargos(HashMap<Integer, Cargo> cargos) {
        this.cargos = cargos;
    }

    @Override
    public void run() {
        synchronized (monitor) {
            if (readCargosHandler != null) {
                readCargosHandler.handle(new CargoClassEvent(this, "all"));
                System.out.println("cargos in warehouse: " + cargos.size());
            }
        }
    }
}
