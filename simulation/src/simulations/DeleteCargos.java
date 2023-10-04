package simulations;

import java.util.HashMap;
import java.util.Random;

import cargo.Cargo;
import eventSystemInfrastructure.CargosSettable;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import events.StorageLocationEvent;

public class DeleteCargos implements Runnable, CargosSettable {

    private boolean isSim3;
    private CargoStorageWatcher cargoStorageWatcher;
    private final Object monitor;
    private Handler<CargoClassEvent> readCargosHandler;
    private Handler<StorageLocationEvent> deleteCargoHandler;
    private HashMap<Integer, Cargo> cargos;

    public DeleteCargos(Object monitor) {
        this.monitor = monitor;
    }

    public DeleteCargos(Object monitor, boolean isSim3, CargoStorageWatcher cargoStorageWatcher) {
        this.monitor = monitor;
        this.isSim3 = isSim3;
        this.cargoStorageWatcher = cargoStorageWatcher;
    }

    public void setReadCargosHandler(Handler<CargoClassEvent> readCargosHandler) {
        this.readCargosHandler = readCargosHandler;
    }

    public void setDeleteCargoHandler(Handler<StorageLocationEvent> deleteCargoHandler) {
        this.deleteCargoHandler = deleteCargoHandler;
    }

    public void setCargos(HashMap<Integer, Cargo> cargos) {
        this.cargos = cargos;
    }

    @Override
    public void run() {
        while (true) {
            execute();
        }
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

    void deleteCargo(int storageLocation) {
        if (storageLocation != -1) {
            StorageLocationEvent storageLocationEvent = new StorageLocationEvent(this, storageLocation);
            if (deleteCargoHandler != null) {
                System.out.println("Delete-" + Thread.currentThread().getName() + " tries to delete a cargo");
                deleteCargoHandler.handle(storageLocationEvent);
            }
        }
    }

    void execute() {
        if (readCargosHandler != null) {
            synchronized (monitor) {
                readCargosHandler.handle(new CargoClassEvent(this, "all"));
                if (cargos != null) {
                    if (isSim3) {
                        if (cargoStorageWatcher.isStorageIsFull()) {
                            wait(cargos);
                        }
                    } else if (cargos.size() > 0) {
                        Random random = new Random();
                        int key = random.nextInt((cargos.size()));
                        int storageLocation = randomStorageLocation(key);
                        deleteCargo(storageLocation);
                    }
                }
            }
        }
    }

    Cargo findOldestCargo(HashMap<Integer, Cargo> cargos) {
        Cargo cargoOldest = null;
        for (Cargo cargo : cargos.values()) {
            cargoOldest = cargo;
            break;
        }
        for (Cargo cargo : cargos.values()) {
            if (cargo.getDurationOfStorage().compareTo(cargoOldest.getDurationOfStorage()) > 0) {
                cargoOldest = cargo;
            }
        }
        return cargoOldest;
    }
    boolean wait(HashMap<Integer, Cargo> cargos){
        if (cargos.size() == 0) {
            try {
                cargoStorageWatcher.setStorageIsFull(false);
                monitor.notifyAll();
                monitor.wait();
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Cargo cargo = findOldestCargo(cargos);
            deleteCargo(cargo.getStorageLocation());
        }
        return false;
    }
}
