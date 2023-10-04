package cargoManagement;

import administration.Customer;
import cargo.*;
import customerManagement.CustomerManagementDecoder;
import customerManagement.CustomerManagement;
import customerManagement.CustomerPojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class CargoStorageDecoder {

    private final CustomerManagementDecoder customerManagementDecoder;

    public CargoStorageDecoder(CustomerManagementDecoder customerManagementDecoder) {
        this.customerManagementDecoder = customerManagementDecoder;
    }

    CargoInternal decodeCargo(CargoPojo c) {
        CargoInternal cargo = null;
        Customer owner = customerManagementDecoder.decodeCustomer(c.customerPojo);
        int storageLocation = c.storageLocation;
        Date insertDate = c.insertDate;
        BigDecimal value = new BigDecimal(c.value);
        HashSet<Hazard> hazards = c.hazards;
        Date lastInspectionDate = c.lastInspectionDate;
        if (c.cargoClass.equals("DryBulkAndUnitisedCargo")) {
            boolean fragile = c.fragile;
            int grainSize = c.grainSize;
            cargo = new DryBulkAndUnitisedCargoImpl(owner, storageLocation, insertDate, value, hazards, fragile, grainSize);
        } else if (c.cargoClass.equals("LiquidAndDryBulkCargo")) {
            boolean pressurized = c.pressurized;
            int grainSize = c.grainSize;
            cargo = new LiquidAndDryBulkCargoImpl(owner, storageLocation, insertDate, value, hazards, pressurized, grainSize);
        } else if (c.cargoClass.equals("LiquidBulkAndUnitisedCargo")) {
            boolean pressurized = c.pressurized;
            boolean fragile = c.fragile;
            cargo = new LiquidBulkAndUnitisedCargoImpl(owner, storageLocation, insertDate, value, hazards, fragile, pressurized);
        } else if (c.cargoClass.equals("DryBulkCargo")) {
            int grainSize = c.grainSize;
            cargo = new DryBulkCargoImpl(owner, storageLocation, insertDate, value, hazards, grainSize);
        } else if (c.cargoClass.equals("UnitisedCargo")) {
            boolean fragile = c.fragile;
            cargo = new UnitisedCargoImpl(owner, storageLocation, insertDate, value, hazards, fragile);
        } else if (c.cargoClass.equals("LiquidBulkCargo")) {
            boolean pressurized = c.pressurized;
            cargo = new LiquidBulkCargoImpl(owner, storageLocation, insertDate, value, hazards, pressurized);
        }
        cargo.setLastInspectionDate(lastInspectionDate);
        return cargo;
    }

    HashMap<Integer, CargoInternal> decodeCargos(HashMap<Integer, CargoPojo> cargoPojos) {
        HashMap<Integer, CargoInternal> cargos = new HashMap<>();
        for (CargoPojo cargoPojo : cargoPojos.values()) {
            CargoInternal cargo = decodeCargo(cargoPojo);
            cargos.put(cargo.getStorageLocation(), cargo);
        }
        return cargos;
    }

    HashSet<Customer> decodeCustomers(HashSet<CustomerPojo> customerPojos) {
        HashSet<Customer> customers = new HashSet<>();
        for (CustomerPojo customerPojo : customerPojos) {
            Customer customer = customerManagementDecoder.decodeCustomer(customerPojo);
            customers.add(customer);
        }
        return customers;
    }

    public CargoStorage decodeCargoStorage(CargoStoragePojo cargoStoragePojo) {
        Number monitor = new Integer(0);
        HashMap<Integer, CargoInternal> cargos = decodeCargos(cargoStoragePojo.cargos);
        boolean[] usedStorageLocations = cargoStoragePojo.usedStorageLocations;
        int capacity = cargoStoragePojo.capacity;
        HashSet<Customer> customers = decodeCustomers(cargoStoragePojo.customerManagementPojo.customers);
        CustomerManagement customerManagement = customerManagementDecoder.createCustomerManagement(monitor, customers);
        return new CargoStorage(capacity, customerManagement, monitor, usedStorageLocations, cargos);
    }
}
