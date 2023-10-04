package cargoManagement;

import cargo.Cargo;
import cargo.Hazard;
import customerManagement.CustomerManagement;
import observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CargoStorageTest {

    CustomerManagement customerManagement;
    CargoStorage cargoStorage;
    HashSet<Hazard> hazards;

    private Number monitor;


    @BeforeEach
    void setUp() {
        monitor = new Integer(0);
        customerManagement = new CustomerManagement(monitor);
        cargoStorage = new CargoStorage(5, this.customerManagement, monitor);
        hazards = new HashSet<>();
    }

    // Testing createCargo();
    @Test
    void add_dryBulkCargo_to_cargo_storage_with_capacity_of_zero_test_return() {
        customerManagement.createCustomer("Emil");
        CargoStorage cargoStorageAlt = new CargoStorage(0, customerManagement, monitor);
        boolean result = cargoStorageAlt.createCargo("DryBulkCargo", "Emil", BigDecimal.valueOf(10.9), hazards, false, false, 4);
        assertFalse(result);
    }

    @Test
    void add_dryBulkAndUnitisedCargo_to_empty_cargo_storage_with_capacity_of_five_successfully_test_return() {
        customerManagement.createCustomer("Emily");
        boolean result = cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        assertTrue(result);
    }

    @Test
    void add_dryBulkAndUnitisedCargo_to_empty_cargo_storage_with_capacity_of_five_successfully_test_storage_location() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        Cargo cargo = null;
        for (Cargo c : cargos.values()) {
            cargo = c;
        }
        assertEquals(0, cargo.getStorageLocation());
    }

    @Test
    void add_dryBulkAndUnitisedCargo_to_empty_cargo_storage_with_capacity_of_five_successfully_test_date() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        Cargo cargo = null;
        for (Cargo c : cargos.values()) {
            cargo = c;
        }
        assertNotNull(cargo.getDurationOfStorage());
    }

    @Test
    void add_dryBulkAndUnitisedCargo_to_empty_cargo_storage_with_capacity_of_five_successfully_test_liste_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        assertEquals(1, cargoStorage.readCargos("all").size());
    }

    @Test
    void add_liquidAndDryBulkCargo_to_cargo_storage_with_one_cargo_and_capacity_of_five_successfully_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        boolean result = cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        assertTrue(result);
    }

    @Test
    void add_liquidAndDryBulkCargo_to_cargo_storage_with_one_cargo_and_capacity_of_five_successfully_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        assertEquals(2, cargoStorage.readCargos("all").size());
    }

    @Test
    void add_dryBulkCargo_to_cargo_storage_with_one_storage_space_left_and_capacity_of_five_successfully_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        boolean result = cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertTrue(result);
    }

    @Test
    void add_dryBulkCargo_to_cargo_storage_with_one_storage_space_left_and_capacity_of_five_successfully_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertEquals(5, cargoStorage.readCargos("all").size());
    }

    @Test
    void add_unitisedCargo_to_cargo_storage_with_no_storage_space_and_capacity_of_five_unsuccessfully_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("UnitisedCargo", "Emily", BigDecimal.valueOf(4.123), hazards, true, false, 0);
        boolean result = cargoStorage.createCargo("UnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 0);
        assertFalse(result);
    }

    @Test
    void add_unitisedCargo_to_cargo_storage_with_no_storage_space_and_capacity_of_five_unsuccessfully_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("UnitisedCargo", "Emily", BigDecimal.valueOf(4.123), hazards, true, false, 0);
        cargoStorage.createCargo("UnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 0);
        assertEquals(5, cargoStorage.readCargos("all").size());
    }

    @Test
    void add_liquidBulkCargo_without_existing_customer_to_cargo_storage_with_capacity_of_five_unsuccessfully_test_return() {
        boolean result = cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        assertFalse(result);
    }

    @Test
    void add_liquidBulkAndUnitisedCargo_without_existing_customer_to_cargo_storage_with_capacity_of_five_unsuccessfully_test_list_size() {
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, true, 0);
        assertEquals(0, cargoStorage.readCargos("all").size());
    }

    @Test
    void add_falseCargo__to_cargo_storage_with_capacity_of_five_unsuccessfully_test_return() {
        customerManagement.createCustomer("Emily");
        boolean result = cargoStorage.createCargo("FalseCargo", "Emily", BigDecimal.valueOf(100), hazards, true, true, 100);
        assertFalse(result);
    }

    @Test
    void add_falseCargo__to_cargo_storage_with_capacity_of_five_unsuccessfully_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("FalseCargo", "Emily", BigDecimal.valueOf(100), hazards, true, true, 100);
        assertEquals(0, cargoStorage.readCargos("all").size());
    }


    @Test
    void add_dryBulkCargo_to_cargo_storage_with_one_space_left_successfully_test_storageLocation() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        Cargo cargo = cargos.get(4);
        assertEquals(4, cargo.getStorageLocation());
    }

    // Testing deleteCargo()
    @Test
    void delete_cargo_with_storage_location_with_capacity_of_zero_test_return() {
        CargoStorage cargoStorageAlt = new CargoStorage(0, customerManagement, monitor);
        boolean result = cargoStorageAlt.deleteCargo(0);
        assertFalse(result);
    }

    @Test
    void delete_cargo_with_empty_list_test_return() {
        boolean result = cargoStorage.deleteCargo(0);
        assertFalse(result);
    }

    @Test
    void delete_cargo_with_list_with_one_cargo_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        boolean result = cargoStorage.deleteCargo(0);
        assertTrue(result);
    }

    @Test
    void delete_cargo_with_list_with_one_cargo_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.deleteCargo(0);
        assertEquals(0, cargoStorage.readCargos("all").size());
    }

    @Test
    void delete_last_cargo_with_full_storage_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        boolean result = cargoStorage.deleteCargo(4);
        assertTrue(result);
    }


    @Test
    void delete_cargo_with_non_existing_storage_location_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        boolean result = cargoStorage.deleteCargo(1);
        assertFalse(result);
    }

    @Test
    void delete_cargo_with_non_existing_storage_location_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.deleteCargo(1);
        assertEquals(1, cargoStorage.readCargos("all").size());
    }

    @Test
    void delete_cargo_with_non_existing_negative_storage_location_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        boolean result = cargoStorage.deleteCargo(-1);
        assertFalse(result);
    }

    // Testing readCargo()
    @Test
    void read_all_cargos_with_empty_list_test_list_size() {
        assertEquals(0, cargoStorage.readCargos("all").size());
    }

    @Test
    void read_all_cargos_after_trying_to_delete_from_returned_list_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        cargos.remove(0);
        assertEquals(1, cargoStorage.readCargos("all").size());
    }

    @Test
    void read_all_cargos_with_full_list_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertEquals(5, cargoStorage.readCargos("all").size());
    }

    @Test
    void read_all_cargos_with_full_list_and_different_handover_parameter_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertEquals(5, cargoStorage.readCargos("i_can_write_whatever_here").size());
    }


    @Test
    void read_dryBulkAndUnitisedCargos_with_full_list_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertEquals(1, cargoStorage.readCargos("DryBulkAndUnitisedCargo").size());
    }

    @Test
    void read_liquidAndDryBulkCargos_with_full_list_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertEquals(1, cargoStorage.readCargos("LiquidAndDryBulkCargo").size());
    }

    @Test
    void read_dryBulkCargo_with_list_of_only_dryBulkCargos_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertEquals(5, cargoStorage.readCargos("DryBulkCargo").size());
    }

    @Test
    void read_liquidAndDryBulkCargo_with_list_without_liquidAndDryBulkCargo_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertEquals(0, cargoStorage.readCargos("LiquidAndDryBulkCargo").size());
    }

    @Test
    void read_liquidBulkAndUnitisedCargo_with_full_list_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        assertEquals(3, cargoStorage.readCargos("LiquidBulkAndUnitisedCargo").size());
    }

    @Test
    void read_liquidBulkCargo_with_list_of_size_one_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        assertEquals(1, cargoStorage.readCargos("LiquidBulkCargo").size());
    }

    @Test
    void read_unitisedCargo_with_list_of_size_three_test_list_size() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("UnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        assertEquals(1, cargoStorage.readCargos("UnitisedCargo").size());
    }
    @Test
    void read_unitisedCargo_with_empty_list_test_list_size() {
        assertEquals(0, cargoStorage.readCargos("UnitisedCargo").size());
    }

    @Test
    void read_when_cargos_are_set_in_constructor(){
        boolean[] usedStorageLocations = new boolean[1];
        CargoStorage cargoStorage1 = new CargoStorage(1, customerManagement, new Integer(0), usedStorageLocations, new HashMap<Integer, CargoInternal>());
        assertEquals(0, cargoStorage1.readCargos("all").size());
    }

    // Testing updateCargo()
    @Test
    void update_first_cargo_with_full_list_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        boolean result = cargoStorage.updateCargo(0);
        assertTrue(result);
    }
    @Test
    void update_first_cargo_with_full_list_test_date() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidAndDryBulkCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        cargoStorage.updateCargo(0);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        Cargo cargo = cargos.get(0);
        assertNotEquals(null, cargo.getLastInspectionDate());
    }
    @Test
    void update_last_cargo_with_full_list_test_date() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        cargoStorage.updateCargo(4);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        Cargo cargo = cargos.get(4);
        assertNotEquals(null, cargo.getLastInspectionDate());
    }
    @Test
    void update_cargo_with_empty_list_test_return() {
        boolean result = cargoStorage.updateCargo(0);
        assertFalse(result);
    }

    @Test
    void update_cargo_with_non_existing_storage_location_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        boolean result = cargoStorage.updateCargo(1);
        assertFalse(result);
    }

    @Test
    void update_cargo_with_negative_non_existing_storage_location_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        boolean result = cargoStorage.updateCargo(-1);
        assertFalse(result);
    }

    // Testing readHazards()
    @Test
    void read_contained_hazards_with_no_cargos_test_return() {
        HashSet<Hazard> hazards = cargoStorage.readHazards(true);
        assertEquals(0, hazards.size());
    }

    @Test
    void read_contained_hazards_with_one_cargo_in_list_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        this.hazards.add(Hazard.radioactive);
        HashSet<Hazard> hazards = cargoStorage.readHazards(true);
        Hazard hazard = null;
        for (Hazard h : hazards) {
            hazard = h;
        }
        assertEquals(Hazard.radioactive, hazard);
    }

    @Test
    void read_contained_hazards_with_no_hazards_in_list_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        HashSet<Hazard> hazards = cargoStorage.readHazards(true);
        assertEquals(0, hazards.size());
    }

    @Test
    void read_contained_hazards_with_all_hazards_in_list_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        this.hazards.add(Hazard.radioactive);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        this.hazards.add(Hazard.explosive);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        this.hazards.add(Hazard.flammable);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        this.hazards.add(Hazard.toxic);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        HashSet<Hazard> hazards = cargoStorage.readHazards(true);
        assertEquals(4, hazards.size());
    }

    @Test
    void read_contained_hazards_with_only_one_hazard_in_list_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        this.hazards.add(Hazard.flammable);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        HashSet<Hazard> hazards = cargoStorage.readHazards(true);
        Hazard hazard = null;
        for (Hazard h : hazards) {
            hazard = h;
        }
        assertEquals(Hazard.flammable, hazard);
    }

    @Test
    void read_contained_hazards_after_deleting_all_hazards_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        this.hazards.add(Hazard.radioactive);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        this.hazards.add(Hazard.explosive);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        this.hazards.add(Hazard.flammable);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        this.hazards.add(Hazard.toxic);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        cargoStorage.deleteCargo(0);
        cargoStorage.deleteCargo(1);
        cargoStorage.deleteCargo(2);
        cargoStorage.deleteCargo(3);
        cargoStorage.deleteCargo(4);
        HashSet<Hazard> hazards = cargoStorage.readHazards(true);
        assertEquals(0, hazards.size());
    }

    @Test
    void read_not_contained_hazards_with_all_hazards_in_list_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        this.hazards.add(Hazard.radioactive);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        this.hazards.add(Hazard.explosive);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        this.hazards.add(Hazard.flammable);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        this.hazards.add(Hazard.toxic);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        HashSet<Hazard> hazards = cargoStorage.readHazards(false);
        assertEquals(0, hazards.size());
    }

    @Test
    void read_not_contained_hazards_with_only_one_hazard_in_list_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        this.hazards.add(Hazard.flammable);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        HashSet<Hazard> hazards = cargoStorage.readHazards(false);
        assertEquals(3, hazards.size());
    }

    @Test
    void read_not_contained_hazards_with_no_cargos_test_return() {
        HashSet<Hazard> hazards = cargoStorage.readHazards(false);
        assertEquals(4, hazards.size());
    }

    @Test
    void read_not_contained_hazards_with_one_cargo_in_list_test_return() {
        customerManagement.createCustomer("Emily");
        this.hazards.add(Hazard.radioactive);
        this.hazards.add(Hazard.explosive);
        this.hazards.add(Hazard.flammable);
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        HashSet<Hazard> hazards = cargoStorage.readHazards(false);
        Hazard hazard = null;
        for (Hazard h : hazards) {
            hazard = h;
        }
        assertEquals(Hazard.toxic, hazard);
    }

    @Test
    void read_not_contained_hazards_with_no_hazards_in_list_test_return() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(7), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkCargo", "Emily", BigDecimal.valueOf(1), hazards, false, false, 0);
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(1.123), hazards, false, false, 2);
        HashSet<Hazard> hazards = cargoStorage.readHazards(false);
        assertEquals(4, hazards.size());
    }

    // Testing getCapacity()
    @Test
    void get_capacity_of_0_test_return() {
        customerManagement = new CustomerManagement(monitor);
        cargoStorage = new CargoStorage(0, this.customerManagement, monitor);
        int capacity = cargoStorage.getCapacity();
        assertEquals(0, capacity);
    }

    @Test
    void get_capacity_of_100_test_return() {
        customerManagement = new CustomerManagement(monitor);
        cargoStorage = new CargoStorage(100, this.customerManagement, monitor);
        int capacity = cargoStorage.getCapacity();
        assertEquals(100, capacity);
    }

    // Testing getUsedStorageLocations()
    @Test
    void get_used_storage_locations_when_capacity_is_0_test_array_length() {
        customerManagement = new CustomerManagement(monitor);
        cargoStorage = new CargoStorage(0, this.customerManagement, monitor);
        boolean[] usedStorageLocations = cargoStorage.getUsedStorageLocations();
        assertEquals(0, usedStorageLocations.length);
    }

    @Test
    void get_used_storage_locations_when_capacity_is_100_test_array_length() {
        customerManagement = new CustomerManagement(monitor);
        cargoStorage = new CargoStorage(100, this.customerManagement, monitor);
        boolean[] usedStorageLocations = cargoStorage.getUsedStorageLocations();
        assertEquals(100, usedStorageLocations.length);
    }

    @Test
    void get_used_storage_locations_when_no_cargo_was_added() {
        boolean[] usedStorageLocations = cargoStorage.getUsedStorageLocations();
        assertFalse(usedStorageLocations[0]);
    }

    @Test
    void get_used_storage_locations_when_one_cargo_was_added() {
        customerManagement.createCustomer("Emil");
        hazards.add(Hazard.toxic);
        cargoStorage.createCargo("DryBulkCargo", "Emil", BigDecimal.valueOf(10.9), hazards, false, false, 4);
        boolean[] usedStorageLocations = cargoStorage.getUsedStorageLocations();
        assertTrue(usedStorageLocations[0]);
    }

    @Test
    void get_used_storage_locations_after_deleting_cargo() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.deleteCargo(0);
        boolean[] usedStorageLocations = cargoStorage.getUsedStorageLocations();
        assertFalse(usedStorageLocations[0]);
    }
    @Test
    void get_used_storage_locations_after_trying_to_manipulate_it_from_outside() {
        boolean[] usedStorageLocations = cargoStorage.getUsedStorageLocations();
        usedStorageLocations[0] = true;
        assertEquals(false, cargoStorage.getUsedStorageLocations()[0]);
    }

        // Testing register()
    @Test
    void register_one_observer_test_return() {
        Observer observer = mock(Observer.class);
        cargoStorage.register(observer);
        assertEquals(1, cargoStorage.getObservers().size());
    }

    @Test
    void register_one_observer_test_observer_in_return() {
        Observer observer = mock(Observer.class);
        cargoStorage.register(observer);
        Observer testObserver = null;
        HashSet<Observer> observers = cargoStorage.getObservers();
        for (Observer o : observers) {
            testObserver = o;
            break;
        }
        assertEquals(observer, testObserver);
    }

    // Testing getObservers()
    @Test
    void get_observers_test_return_after_changing_list_from_outside() {
        Observer observer = mock(Observer.class);
        cargoStorage.register(observer);
        HashSet<Observer> observers = cargoStorage.getObservers();
        observers.add(mock(Observer.class));
        assertEquals(1, cargoStorage.getObservers().size());
    }

    @Test
    void get_observers_empty_list_test_return() {
        HashSet<Observer> observers = cargoStorage.getObservers();
        assertEquals(0, cargoStorage.getObservers().size());
    }

    // Testing notifyObservers()
    @Test
    void notify_observers_test_if_update_was_called() {
        Observer observer = mock(Observer.class);
        cargoStorage.register(observer);
        cargoStorage.notifyObservers();
        Mockito.verify(observer, Mockito.times(1)).update();
    }

    // Testing getCustomerManagement()
    @Test
    void getCustomerManagement_test_after_construction() {
        CustomerManagement customerManagementAlt = new CustomerManagement(monitor);
        cargoStorage = new CargoStorage(5, customerManagementAlt, monitor);
        assertEquals(customerManagementAlt, cargoStorage.getCustomerManagement());
    }

    // Testing swapStorageLocations()
    @Test
    void swapStorageLocations_with_two_different_cargos_test_first_cargo_name() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        customerManagement.createCustomer("Emil");
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emil", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.swapStorageLocations(0, 1);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        System.out.println(cargos);
        assertEquals("Emil", cargos.get(0).getOwner().getName());
    }

    @Test
    void swapStorageLocations_with_two_different_cargos_test_second_cargo_name() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        customerManagement.createCustomer("Emil");
        cargoStorage.createCargo("LiquidBulkAndUnitisedCargo", "Emil", BigDecimal.valueOf(2.81), hazards, false, true, 6);
        cargoStorage.swapStorageLocations(0, 1);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        System.out.println(cargos);
        assertEquals("Emily", cargos.get(1).getOwner().getName());
    }

    @Test
    void swapStorageLocations_with_two_times_the_same_cargo() {
        customerManagement.createCustomer("Emily");
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.swapStorageLocations(0, 0);
        HashMap<Integer, Cargo> cargos = cargoStorage.readCargos("all");
        System.out.println(cargos);
        assertEquals("Emily", cargos.get(0).getOwner().getName());
    }
}