package cargoManagement;

import administration.Customer;
import cargo.*;
import customerManagement.CustomerManagementPojo;
import customerManagement.CustomerPojo;

import java.util.HashMap;
import java.util.HashSet;

public class CargoStorageEncoder {

    public CargoStoragePojo encodeCargoStorage(CargoStorage cargoStorage) {
        CargoStoragePojo cargoStoragePojo = new CargoStoragePojo();

        HashMap<Integer, CargoPojo> cargoPojos = encodeCargos(cargoStorage.readCargos("all"));
        cargoStoragePojo.cargos = cargoPojos;
        cargoStoragePojo.usedStorageLocations = cargoStorage.getUsedStorageLocations();
        cargoStoragePojo.capacity = cargoStorage.getCapacity();

        CustomerManagementPojo customerManagementPojo = new CustomerManagementPojo();
        customerManagementPojo.customers = encodeCustomers(cargoStorage.getCustomerManagement().readCustomers());
        cargoStoragePojo.customerManagementPojo = customerManagementPojo;

        return cargoStoragePojo;
    }

    HashSet<CustomerPojo> encodeCustomers(HashSet<Customer> customers) {
        HashSet<CustomerPojo> customerPojos = new HashSet<>();
        for (Customer customer : customers) {
            CustomerPojo customerPojo = encodeCustomer(customer);
            customerPojos.add(customerPojo);
        }
        return customerPojos;
    }

    HashMap<Integer, CargoPojo> encodeCargos(HashMap<Integer, Cargo> cargos) {
        HashMap<Integer, CargoPojo> cargoPojos = new HashMap<>();
        for (Cargo c : cargos.values()) {
            CargoPojo cargoPojo = encodeCargo(c);
            cargoPojos.put(cargoPojo.storageLocation, cargoPojo);
        }
        return cargoPojos;
    }

    CargoPojo encodeCargo(Cargo c) {
        CargoPojo cargoPojo = new CargoPojo();
        if (c instanceof DryBulkAndUnitisedCargo) {
            DryBulkAndUnitisedCargo cargo = (DryBulkAndUnitisedCargo) c;
            cargoPojo.cargoClass = "DryBulkAndUnitisedCargo";
            cargoPojo.customerPojo = encodeCustomer(cargo.getOwner());
            cargoPojo.storageLocation = cargo.getStorageLocation();
            cargoPojo.insertDate = cargo.getInsertDate();
            cargoPojo.value = cargo.getValue().toString();
            cargoPojo.hazards = (HashSet<Hazard>) cargo.getHazards();
            cargoPojo.fragile = cargo.isFragile();
            cargoPojo.grainSize = cargo.getGrainSize();
            cargoPojo.lastInspectionDate = cargo.getLastInspectionDate();
        } else if (c instanceof LiquidAndDryBulkCargo) {
            LiquidAndDryBulkCargo cargo = (LiquidAndDryBulkCargo) c;
            cargoPojo.cargoClass = "LiquidAndDryBulkCargo";
            cargoPojo.customerPojo = encodeCustomer(cargo.getOwner());
            cargoPojo.storageLocation = cargo.getStorageLocation();
            cargoPojo.insertDate = cargo.getInsertDate();
            cargoPojo.value = cargo.getValue().toString();
            cargoPojo.hazards = (HashSet<Hazard>) cargo.getHazards();
            cargoPojo.pressurized = cargo.isPressurized();
            cargoPojo.grainSize = cargo.getGrainSize();
            cargoPojo.lastInspectionDate = cargo.getLastInspectionDate();
        } else if (c instanceof LiquidBulkAndUnitisedCargo) {
            LiquidBulkAndUnitisedCargo cargo = (LiquidBulkAndUnitisedCargo) c;
            cargoPojo.cargoClass = "LiquidBulkAndUnitisedCargo";
            cargoPojo.customerPojo = encodeCustomer(cargo.getOwner());
            cargoPojo.storageLocation = cargo.getStorageLocation();
            cargoPojo.insertDate = cargo.getInsertDate();
            cargoPojo.value = cargo.getValue().toString();
            cargoPojo.hazards = (HashSet<Hazard>) cargo.getHazards();
            cargoPojo.fragile = cargo.isFragile();
            cargoPojo.pressurized = cargo.isPressurized();
            cargoPojo.lastInspectionDate = cargo.getLastInspectionDate();
        } else if (c instanceof DryBulkCargo) {
            DryBulkCargo cargo = (DryBulkCargo) c;
            cargoPojo.cargoClass = "DryBulkCargo";
            cargoPojo.customerPojo = encodeCustomer(cargo.getOwner());
            cargoPojo.storageLocation = cargo.getStorageLocation();
            cargoPojo.insertDate = cargo.getInsertDate();
            cargoPojo.value = cargo.getValue().toString();
            cargoPojo.hazards = (HashSet<Hazard>) cargo.getHazards();
            cargoPojo.grainSize = cargo.getGrainSize();
            cargoPojo.lastInspectionDate = cargo.getLastInspectionDate();
        } else if (c instanceof UnitisedCargo) {
            UnitisedCargo cargo = (UnitisedCargo) c;
            cargoPojo.cargoClass = "UnitisedCargo";
            cargoPojo.customerPojo = encodeCustomer(cargo.getOwner());
            cargoPojo.storageLocation = cargo.getStorageLocation();
            cargoPojo.insertDate = cargo.getInsertDate();
            cargoPojo.value = cargo.getValue().toString();
            cargoPojo.hazards = (HashSet<Hazard>) cargo.getHazards();
            cargoPojo.fragile = cargo.isFragile();
            cargoPojo.lastInspectionDate = cargo.getLastInspectionDate();
        } else if (c instanceof LiquidBulkCargo) {
            LiquidBulkCargo cargo = (LiquidBulkCargo) c;
            cargoPojo.cargoClass = "LiquidBulkCargo";
            cargoPojo.customerPojo = encodeCustomer(cargo.getOwner());
            cargoPojo.storageLocation = cargo.getStorageLocation();
            cargoPojo.insertDate = cargo.getInsertDate();
            cargoPojo.value = cargo.getValue().toString();
            cargoPojo.hazards = (HashSet<Hazard>) cargo.getHazards();
            cargoPojo.pressurized = cargo.isPressurized();
            cargoPojo.lastInspectionDate = cargo.getLastInspectionDate();
        }
        return cargoPojo;
    }

    CustomerPojo encodeCustomer(Customer customer) {
        CustomerPojo customerPojo = new CustomerPojo();
        customerPojo.numOfCargos = customer.getNumOfCargos();
        customerPojo.name = customer.getName();
        return customerPojo;
    }

}
