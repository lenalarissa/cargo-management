package cargoManagement;

import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import customerManagement.CustomerManagement;
import observer.Observer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class CargoStorage implements Serializable {

    private final static long serialVersionUID = 1L;
    private final CustomerManagement customerManagement;
    private final int capacity;
    private final HashMap<Integer, CargoInternal> cargos;
    private final boolean[] usedStorageLocations;
    private final HashSet<Observer> observers;
    private final Number monitor;

    public CargoStorage(int capacity, CustomerManagement customerManagement, Number monitor) {
        this.capacity = capacity;
        this.usedStorageLocations = new boolean[capacity];
        this.customerManagement = customerManagement;
        this.monitor = monitor;
        observers = new HashSet<>();
        cargos = new HashMap<>();
    }

    CargoStorage(int capacity, CustomerManagement customerManagement, Number monitor, boolean[] usedStorageLocations, HashMap<Integer, CargoInternal> cargos) {
        this.capacity = capacity;
        this.customerManagement = customerManagement;
        this.monitor = monitor;
        this.usedStorageLocations = usedStorageLocations;
        this.cargos = cargos;
        observers = new HashSet<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public CustomerManagement getCustomerManagement() {
        return customerManagement;
    }

    public boolean[] getUsedStorageLocations() {
        return Arrays.copyOf(usedStorageLocations, usedStorageLocations.length);
    }

    public HashSet<Observer> getObservers() {
        HashSet<Observer> observersCopy = new HashSet<>();
        for (Observer observer : observers) {
            observersCopy.add(observer);
        }
        return observersCopy;
    }

    public boolean createCargo(String cargoClass, String customerName, BigDecimal value, HashSet<Hazard> hazards, boolean fragile, boolean pressurized, int grainSize) {
        synchronized (monitor) {
            if (cargos.size() == capacity) {
                return false;
            }
            HashSet<Customer> customers = customerManagement.readCustomers();
            for (Customer customer : customers) {
                if (customer.getName().equals(customerName)) {
                    int storageLocation = 0;
                    for (boolean usedStorageLocation : usedStorageLocations) {
                        if (!usedStorageLocation) {
                            CargoInternal cargo;
                            switch (cargoClass) {
                                case "DryBulkAndUnitisedCargo":
                                    cargo = new DryBulkAndUnitisedCargoImpl(customer, storageLocation, new Date(), value, hazards, fragile, grainSize);
                                    break;
                                case "DryBulkCargo":
                                    cargo = new DryBulkCargoImpl(customer, storageLocation, new Date(), value, hazards, grainSize);
                                    break;
                                case "LiquidAndDryBulkCargo":
                                    cargo = new LiquidAndDryBulkCargoImpl(customer, storageLocation, new Date(), value, hazards, pressurized, grainSize);
                                    break;
                                case "LiquidBulkAndUnitisedCargo":
                                    cargo = new LiquidBulkAndUnitisedCargoImpl(customer, storageLocation, new Date(), value, hazards, fragile, pressurized);
                                    break;
                                case "LiquidBulkCargo":
                                    cargo = new LiquidBulkCargoImpl(customer, storageLocation, new Date(), value, hazards, pressurized);
                                    break;
                                case "UnitisedCargo":
                                    cargo = new UnitisedCargoImpl(customer, storageLocation, new Date(), value, hazards, fragile);
                                    break;
                                default:
                                    return false;
                            }
                            usedStorageLocations[storageLocation] = true;
                            cargos.put(storageLocation, cargo);
                            customerManagement.setNumOfCargos(customer, 1);
                            notifyObservers();
                            return true;
                        }
                        storageLocation++;
                    }
                }
            }
            return false;
        }
    }

    public boolean deleteCargo(int storageLocation) {
        synchronized (monitor) {
            CargoInternal cargo = cargos.remove(storageLocation);
            if (cargo == null) {
                return false;
            }
            usedStorageLocations[storageLocation] = false;
            HashSet<Customer> customers = customerManagement.readCustomers();
            for (Customer customer : customers) {
                if (customer == cargo.getOwner()) {
                    customerManagement.setNumOfCargos(customer, -1);
                }
            }
            notifyObservers();
            return true;
        }
    }

    public HashMap<Integer, Cargo> readCargos(String cargoClass) {
        HashMap<Integer, Cargo> cargosCopy = new HashMap<>();
        switch (cargoClass) {
            case "DryBulkAndUnitisedCargo":
                for (Cargo cargo : cargos.values()) {
                    if (cargo instanceof DryBulkAndUnitisedCargoImpl) {
                        cargosCopy.put(cargo.getStorageLocation(), cargo);
                    }
                }
                break;
            case "DryBulkCargo":
                for (Cargo cargo : cargos.values()) {
                    if (cargo instanceof DryBulkCargoImpl) {
                        cargosCopy.put(cargo.getStorageLocation(), cargo);
                    }
                }
                break;
            case "LiquidAndDryBulkCargo":
                for (Cargo cargo : cargos.values()) {
                    if (cargo instanceof LiquidAndDryBulkCargoImpl) {
                        cargosCopy.put(cargo.getStorageLocation(), cargo);
                    }
                }
                break;
            case "LiquidBulkCargo":
                for (Cargo cargo : cargos.values()) {
                    if (cargo instanceof LiquidBulkCargoImpl) {
                        cargosCopy.put(cargo.getStorageLocation(), cargo);
                    }
                }
                break;
            case "UnitisedCargo":
                for (Cargo cargo : cargos.values()) {
                    if (cargo instanceof UnitisedCargoImpl) {
                        cargosCopy.put(cargo.getStorageLocation(), cargo);
                    }
                }
                break;
            case "LiquidBulkAndUnitisedCargo":
                for (Cargo cargo : cargos.values()) {
                    if (cargo instanceof LiquidBulkAndUnitisedCargoImpl) {
                        cargosCopy.put(cargo.getStorageLocation(), cargo);
                    }
                }
                break;
            default:
                for (Cargo cargo : cargos.values()) {
                    cargosCopy.put(cargo.getStorageLocation(), cargo);
                }
        }
        return cargosCopy;
    }

    public boolean updateCargo(int storageLocation) {
        synchronized (monitor) {
            CargoInternal cargo = cargos.get(storageLocation);
            if (cargo != null) {
                cargo.setLastInspectionDate(new Date());
                return true;
            }
            return false;
        }
    }

    public HashSet<Hazard> readHazards(boolean contained) {
        HashSet<Hazard> containedHazards = new HashSet<>();
        for (CargoInternal cargo : cargos.values()) {
            Collection<Hazard> hazardsOfCargos = cargo.getHazards();
            containedHazards.addAll(hazardsOfCargos);
        }
        if (contained) {
            return containedHazards;
        }
        HashSet<Hazard> notContainedHazards = new HashSet<>();
        HashSet<Hazard> allPossibleHazards = new HashSet<>();
        allPossibleHazards.add(Hazard.explosive);
        allPossibleHazards.add(Hazard.flammable);
        allPossibleHazards.add(Hazard.toxic);
        allPossibleHazards.add(Hazard.radioactive);
        for (Hazard hazard : allPossibleHazards) {
            if (!containedHazards.contains(hazard)) {
                notContainedHazards.add(hazard);
            }
        }
        return notContainedHazards;
    }

    public boolean swapStorageLocations(int storageLocation1, int storageLocation2) {
        synchronized (monitor) {
            CargoInternal cargo1 = cargos.get(storageLocation1);
            CargoInternal cargo2 = cargos.get(storageLocation2);
            cargo1.setStorageLocation(storageLocation2);
            cargo2.setStorageLocation(storageLocation1);
            cargos.put(storageLocation1, cargo2);
            cargos.put(storageLocation2, cargo1);
            return true;
        }
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
