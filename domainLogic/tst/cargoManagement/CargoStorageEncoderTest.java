package cargoManagement;

import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import customerManagement.CustomerManagement;
import customerManagement.CustomerPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CargoStorageEncoderTest {

    private CargoStorageEncoder cargoStorageEncoder;
    private Customer customer;

    @BeforeEach
    void setUp() {
        cargoStorageEncoder = new CargoStorageEncoder();
        customer = mock(Customer.class);
        when(customer.getNumOfCargos()).thenReturn(1);
        when(customer.getName()).thenReturn("Emil");
    }

    @Test
    void encodeCargoStorage_test_capacity() {
        CustomerManagement customerManagement = new CustomerManagement(new Integer(0));
        CargoStorage cargoStorage = new CargoStorage(3, customerManagement, new Integer(0));
        CargoStoragePojo cargoStoragePojo = cargoStorageEncoder.encodeCargoStorage(cargoStorage);
        assertEquals(3, cargoStoragePojo.capacity);
    }

    @Test
    void encodeCustomers_test_list_size() {
        HashSet<Customer> customers = new HashSet<>();
        HashSet<CustomerPojo> customerPojos = cargoStorageEncoder.encodeCustomers(customers);
        assertEquals(0, customerPojos.size());
    }

    @Test
    void encodeCargos_test_list_size() {
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        HashMap<Integer, CargoPojo> cargoPojos = cargoStorageEncoder.encodeCargos(cargos);
        assertEquals(0, cargoPojos.size());
    }

    @Test
    void encodeCargo_test_if_cargo_class_is_DryBulkAndUnitisedCargo() {
        DryBulkAndUnitisedCargoImpl cargo = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), new HashSet<Hazard>(), true, 1);
        CargoPojo cargoPojo = cargoStorageEncoder.encodeCargo(cargo);
        assertTrue(cargoPojo.cargoClass.equals("DryBulkAndUnitisedCargo"));
    }

    @Test
    void encodeCargo_test_if_cargo_class_is_DryBulkCargo() {
        DryBulkCargoImpl cargo = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), new HashSet<Hazard>(), 1);
        CargoPojo cargoPojo = cargoStorageEncoder.encodeCargo(cargo);
        assertTrue(cargoPojo.cargoClass.equals("DryBulkCargo"));
    }

    @Test
    void encodeCargo_test_if_cargo_class_is_LiquidAndDryBulkCargo() {
        LiquidAndDryBulkCargoImpl cargo = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), new HashSet<Hazard>(), true, 3);
        CargoPojo cargoPojo = cargoStorageEncoder.encodeCargo(cargo);
        assertTrue(cargoPojo.cargoClass.equals("LiquidAndDryBulkCargo"));
    }

    @Test
    void encodeCargo_test_if_cargo_class_is_LiquidBulkAndUnitisedCargo() {
        LiquidBulkAndUnitisedCargoImpl cargo = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), new HashSet<Hazard>(), true, true);
        CargoPojo cargoPojo = cargoStorageEncoder.encodeCargo(cargo);
        assertTrue(cargoPojo.cargoClass.equals("LiquidBulkAndUnitisedCargo"));
    }

    @Test
    void encodeCargo_test_if_cargo_class_is_LiquidBulkCargo() {
        LiquidBulkCargoImpl cargo = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), new HashSet<Hazard>(), true);
        CargoPojo cargoPojo = cargoStorageEncoder.encodeCargo(cargo);
        assertTrue(cargoPojo.cargoClass.equals("LiquidBulkCargo"));
    }

    @Test
    void encodeCargo_test_if_cargo_class_is_UnitisedCargo() {
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), new HashSet<Hazard>(), true);
        CargoPojo cargoPojo = cargoStorageEncoder.encodeCargo(cargo);
        assertTrue(cargoPojo.cargoClass.equals("UnitisedCargo"));
    }

    @Test
    void encodeCustomer_test_numOfCargos() {
        CustomerPojo customerPojo = cargoStorageEncoder.encodeCustomer(customer);
        assertEquals(1, customerPojo.numOfCargos);
    }

    @Test
    void encodeCustomer_test_name() {
        CustomerPojo customerPojo = cargoStorageEncoder.encodeCustomer(customer);
        assertTrue(customerPojo.name.equals(customer.getName()));
    }
}