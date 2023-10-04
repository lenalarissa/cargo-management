package simulations;

import cargo.Cargo;
import cargo.Hazard;
import eventSystemInfrastructure.CargosSettable;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import events.CargoEvent;

import java.math.BigDecimal;
import java.util.*;


public class CreateCargos implements Runnable, CargosSettable {

    private boolean isSim3;
    private int capacity;
    private CargoStorageWatcher cargoStorageWatcher;
    private final Object monitor;
    private HashMap<Integer, Cargo> cargos;
    private Handler<CargoClassEvent> readCargosHandler;
    private Handler<CargoEvent> createCargoHandler;

    public CreateCargos(Object monitor) {
        this.monitor = monitor;
    }

    public CreateCargos(Object monitor, boolean isSim3, int capacity, CargoStorageWatcher cargoStorageWatcher) {
        this.monitor = monitor;
        this.isSim3 = isSim3;
        this.capacity = capacity;
        this.cargoStorageWatcher = cargoStorageWatcher;
    }

    public void setCreateCargoHandler(Handler<CargoEvent> createCargoHandler) {
        this.createCargoHandler = createCargoHandler;
    }

    public void setReadCargosHandler(Handler<CargoClassEvent> readCargosHandler) {
        this.readCargosHandler = readCargosHandler;
    }

    public void setCargos(HashMap<Integer, Cargo> cargos) {
        this.cargos = cargos;
    }

    @Override
    public void run() {
        while (true) {
            // Create random properties for cargos
            Random random = new Random();
            int cargoIndex = random.nextInt(6);
            String cargoClass = randomCargoClass(cargoIndex);
            int nameIndex = random.nextInt(25);
            String name = randomName(nameIndex);
            BigDecimal value = BigDecimal.valueOf(random.nextInt(1000000));
            int hazardsIndex = random.nextInt(16);
            HashSet<Hazard> hazards = randomHazards(hazardsIndex);
            boolean fragile = random.nextBoolean();
            boolean pressurized = random.nextBoolean();
            int grainSize = random.nextInt(100);

            createCargo(cargoClass, name, value, hazards, fragile, pressurized, grainSize);
        }
    }

    String randomCargoClass(int index) {
        String[] cargoArray = {"DryBulkAndUnitisedCargo", "DryBulkCargo", "LiquidAndDryBulkCargo", "LiquidBulkAndUnitisedCargo", "LiquidBulkCargo", "UnitisedCargo"};
        ArrayList<String> cargos = new ArrayList<>(Arrays.asList(cargoArray));
        return cargos.get(index);
    }

    String randomName(int index) {
        String[] namesArray = {"Abel", "Berbel", "Claudia", "Doris", "Eva", "Fabian", "Gustav", "Hans", "Inga", "Justus", "Kai", "Lena", "Maria", "Nina", "Olaf", "Pia", "Rabea", "Sebastian", "Tabea", "Ulf", "Viktor", "Wolfgang", "Xavier", "Yael", "Zac"};
        ArrayList<String> names = new ArrayList<>(Arrays.asList(namesArray));
        return names.get(index);
    }

    HashSet<Hazard> randomHazards(int index) {
        String[] hazardArray = {",", "explosive", "flammable", "toxic", "radioactive", "explosive,flammable", "explosive,toxic", "explosive,radioactive", "flammable,toxic", "flammable,radioactive", "toxic,radioactive", "explosive,flammable,toxic", "explosive,flammable,radioactive", "explosive,toxic,radioactive", "flammable,toxic,radioactive", "explosive,flammable,toxic,radioactive"};
        ArrayList<String> hazards = new ArrayList<>(Arrays.asList(hazardArray));
        String hazard = hazards.get(index);
        HashSet<Hazard> hazardSet = new HashSet<>();
        if (!hazard.equals(",")) {
            String[] hazardList = hazard.split(",");
            for (String h : hazardList) {
                hazardSet.add(Hazard.valueOf(h));
            }
        }
        return hazardSet;
    }

    void createCargo(String cargoClass, String name, BigDecimal value, HashSet<Hazard> hazards, boolean fragile, boolean pressurized, int grainSize) {
        CargoEvent cargoEvent = new CargoEvent(this, cargoClass, name, value, hazards, fragile, pressurized, grainSize);
        if (createCargoHandler != null) {
            synchronized (monitor) {
                if (isSim3 && !cargoStorageWatcher.isStorageIsFull()) {
                    if (readCargosHandler != null) {
                        readCargosHandler.handle(new CargoClassEvent(this, "all"));
                        if(cargos != null) {
                            wait(cargos);
                        }
                    }
                }
                System.out.println("Create-" + Thread.currentThread().getName() + " tries to create a cargo");
                createCargoHandler.handle(cargoEvent);
            }
        }
    }
    boolean wait(HashMap<Integer, Cargo> cargos){
        if (cargos.size() == capacity) {
            cargoStorageWatcher.setStorageIsFull(true);
            monitor.notifyAll();
            try {
                monitor.wait();
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
