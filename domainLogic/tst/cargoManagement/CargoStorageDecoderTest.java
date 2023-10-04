package cargoManagement;

import administration.Customer;
import cargo.*;
import customerManagement.CustomerManagementDecoder;
import customerManagement.CustomerManagementPojo;
import customerManagement.CustomerPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CargoStorageDecoderTest {

    private CargoStorageDecoder cargoStorageDecoder;
    private CargoPojo cargoPojo;
    private CustomerPojo customerPojo;

    @BeforeEach
    void setUp() {
        CustomerManagementDecoder customerManagementDecoder = new CustomerManagementDecoder();
        cargoStorageDecoder = new CargoStorageDecoder(customerManagementDecoder);

        cargoPojo = new CargoPojo();
        customerPojo = new CustomerPojo();
        customerPojo.name = "Emil";
        customerPojo.numOfCargos = 1;
        cargoPojo.customerPojo = customerPojo;
        cargoPojo.value = "10";
        cargoPojo.hazards = new HashSet<Hazard>();
    }

    @Test
    void decodeCargo_getDryBulkAndUnitisedCargo() {
        cargoPojo.cargoClass = "DryBulkAndUnitisedCargo";
        CargoInternal cargo = cargoStorageDecoder.decodeCargo(cargoPojo);
        assertTrue(cargo instanceof DryBulkAndUnitisedCargoImpl);
    }

    @Test
    void decodeCargo_getLiquidAndDryBulkCargo() {
        cargoPojo.cargoClass = "LiquidAndDryBulkCargo";
        CargoInternal cargo = cargoStorageDecoder.decodeCargo(cargoPojo);
        assertTrue(cargo instanceof LiquidAndDryBulkCargoImpl);
    }

    @Test
    void decodeCargo_getLiquidBulkAndUnitisedCargo() {
        cargoPojo.cargoClass = "LiquidBulkAndUnitisedCargo";
        CargoInternal cargo = cargoStorageDecoder.decodeCargo(cargoPojo);
        assertTrue(cargo instanceof LiquidBulkAndUnitisedCargoImpl);
    }

    @Test
    void decodeCargo_getDryBulkCargo() {
        cargoPojo.cargoClass = "DryBulkCargo";
        CargoInternal cargo = cargoStorageDecoder.decodeCargo(cargoPojo);
        assertTrue(cargo instanceof DryBulkCargoImpl);
    }

    @Test
    void decodeCargo_getUnitisedCargo() {
        cargoPojo.cargoClass = "UnitisedCargo";
        CargoInternal cargo = cargoStorageDecoder.decodeCargo(cargoPojo);
        assertTrue(cargo instanceof UnitisedCargoImpl);
    }

    @Test
    void decodeCargo_getLiquidBulkCargo() {
        cargoPojo.cargoClass = "LiquidBulkCargo";
        CargoInternal cargo = cargoStorageDecoder.decodeCargo(cargoPojo);
        assertTrue(cargo instanceof LiquidBulkCargoImpl);
    }

    @Test
    void decodeCargos_test_if_same_list_size() {
        cargoPojo.cargoClass = "LiquidBulkCargo";
        HashMap<Integer, CargoPojo> cargoPojos = new HashMap<>();
        cargoPojos.put(0, cargoPojo);
        HashMap<Integer, CargoInternal> cargos = cargoStorageDecoder.decodeCargos(cargoPojos);
        assertEquals(1, cargos.size());
    }

    @Test
    void decodeCustomers_test_if_same_list_size() {
        HashSet<CustomerPojo> customerPojos = new HashSet<>();
        customerPojos.add(customerPojo);
        HashSet<Customer> customers = cargoStorageDecoder.decodeCustomers(customerPojos);
        assertEquals(1, customers.size());
    }

    @Test
    void decodeCargoStorage_test_if_same_capacity() {
        cargoPojo.cargoClass = "LiquidBulkCargo";
        HashMap<Integer, CargoPojo> cargoPojos = new HashMap<>();
        HashSet<CustomerPojo> customerPojos = new HashSet<>();
        CargoStoragePojo cargoStoragePojo = new CargoStoragePojo();
        cargoStoragePojo.capacity = 100;
        cargoStoragePojo.cargos = cargoPojos;
        CustomerManagementPojo customerManagementPojo = new CustomerManagementPojo();
        customerManagementPojo.customers = customerPojos;
        cargoStoragePojo.customerManagementPojo = customerManagementPojo;
        CargoStorage cargoStorage = cargoStorageDecoder.decodeCargoStorage(cargoStoragePojo);
        assertEquals(100, cargoStorage.getCapacity());
    }

    @Test
    void decodeCargoStorage_test_if_same_cargo_list_size() {
        cargoPojo.cargoClass = "LiquidBulkCargo";
        HashMap<Integer, CargoPojo> cargoPojos = new HashMap<>();
        HashSet<CustomerPojo> customerPojos = new HashSet<>();
        CargoStoragePojo cargoStoragePojo = new CargoStoragePojo();
        cargoStoragePojo.capacity = 100;
        cargoStoragePojo.cargos = cargoPojos;
        CustomerManagementPojo customerManagementPojo = new CustomerManagementPojo();
        customerManagementPojo.customers = customerPojos;
        cargoStoragePojo.customerManagementPojo = customerManagementPojo;
        CargoStorage cargoStorage = cargoStorageDecoder.decodeCargoStorage(cargoStoragePojo);
        assertEquals(0, cargoStorage.readCargos("all").size());
    }

    @Test
    void decodeCargoStorage_test_if_same_customer_list_size() {
        cargoPojo.cargoClass = "LiquidBulkCargo";
        HashMap<Integer, CargoPojo> cargoPojos = new HashMap<>();
        HashSet<CustomerPojo> customerPojos = new HashSet<>();
        CargoStoragePojo cargoStoragePojo = new CargoStoragePojo();
        cargoStoragePojo.capacity = 100;
        cargoStoragePojo.cargos = cargoPojos;
        CustomerManagementPojo customerManagementPojo = new CustomerManagementPojo();
        customerManagementPojo.customers = customerPojos;
        cargoStoragePojo.customerManagementPojo = customerManagementPojo;
        CargoStorage cargoStorage = cargoStorageDecoder.decodeCargoStorage(cargoStoragePojo);
        assertEquals(0, cargoStorage.getCustomerManagement().readCustomers().size());
    }
}