package simulations;

import cargo.Cargo;
import eventSystemInfrastructure.CargosSettable;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import events.StorageLocationEvent;

import java.util.HashMap;
import java.util.Random;

public class UpdateCargos implements Runnable, CargosSettable {

    private final Object monitor;
    private Handler<CargoClassEvent> readCargosHandler;
    private Handler<StorageLocationEvent> updateCargoHandler;
    private HashMap<Integer, Cargo> cargos;

    public UpdateCargos(Object monitor) {
        this.monitor = monitor;
    }

    public void setReadCargosHandler(Handler<CargoClassEvent> readCargosHandler) {
        this.readCargosHandler = readCargosHandler;
    }

    public void setUpdateCargoHandler(Handler<StorageLocationEvent> updateCargoHandler) {
        this.updateCargoHandler = updateCargoHandler;
    }

    public void setCargos(HashMap<Integer, Cargo> cargos) {
        this.cargos = cargos;
    }

    @Override
    public void run() {
        execute();
    }

    int randomStorageLocation(int key) {
        while (true) {
            boolean deletable = cargos.containsKey(key);
            if (deletable) {
                return key;
            }
            break;
        }
        return -1;
    }

    void updateCargo(int storageLocation) {
        if (storageLocation != -1) {
            StorageLocationEvent storageLocationEvent = new StorageLocationEvent(this, storageLocation);
            if (updateCargoHandler != null) {
                System.out.println("Update-" + Thread.currentThread().getName() + " tries to update a cargo");
                updateCargoHandler.handle(storageLocationEvent);
            }
        }
    }

    void execute() {
        if (readCargosHandler != null) {
            synchronized (monitor) {
                readCargosHandler.handle(new CargoClassEvent(this, "all"));
                if (cargos != null) {
                    if (cargos.size() > 0) {
                        Random random = new Random();
                        int key = random.nextInt((cargos.size()));
                        int storageLocation = randomStorageLocation(key);
                        updateCargo(storageLocation);
                    }
                }
            }
        }
    }
}
