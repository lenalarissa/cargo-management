package cargoManagement;

import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DryBulkAndUnitisedCargoImplTest {

    private HashSet<Hazard> hazards;
    private Customer customer;

    @BeforeEach
    void setUp() {
        hazards = new HashSet<>();
        customer = mock(Customer.class);
    }

    // Testing getOwner
    @Test
    void get_owner_using_impl() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, true, 1);
        assertEquals(customer, dryBulkAndUnitisedCargoImpl.getOwner());
    }

    @Test
    void get_owner_using_interface() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, true, 1);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(dryBulkAndUnitisedCargoImpl.getStorageLocation(), dryBulkAndUnitisedCargoImpl);
        assertEquals(customer, cargos.get(0).getOwner());
    }

    // Testing getDurationOfStorage
    @Test
    void get_duration_of_storage_test_duration() {
        Date date = new Date();
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, date, BigDecimal.valueOf(1), hazards, true, 1);
        long expectedDurationMillis = Duration.between(date.toInstant(), new Date().toInstant()).toMillis();
        long actualDurationMillis = dryBulkAndUnitisedCargoImpl.getDurationOfStorage().toMillis();
        long toleranceMillis = 1;
        assertTrue(Math.abs(expectedDurationMillis - actualDurationMillis) <= toleranceMillis);
    }

    // Testing getLastInspectionDate() & setLastInspectionDate()
    @Test
    void set_and_get_last_inspection_date_test_date() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, true, 1);
        Date date = new Date();
        dryBulkAndUnitisedCargoImpl.setLastInspectionDate(date);
        assertEquals(date.toInstant(), dryBulkAndUnitisedCargoImpl.getLastInspectionDate().toInstant());
    }

    @Test
    void set_and_get_last_inspection_date_test_not_null() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, true, 1);
        dryBulkAndUnitisedCargoImpl.setLastInspectionDate(new Date());
        assertNotNull(dryBulkAndUnitisedCargoImpl.getLastInspectionDate());
    }

    @Test
    void get_last_inspection_date_test_if_null_when_not_set() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, true, 1);
        assertNull(dryBulkAndUnitisedCargoImpl.getLastInspectionDate());
    }

    // Testing getStorageLocation() & setStorageLocation()
    @Test
    void get_storage_location_test_storage_location_after_instancing() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, true, 1);
        dryBulkAndUnitisedCargoImpl.setStorageLocation(4);
        assertEquals(4, dryBulkAndUnitisedCargoImpl.getStorageLocation());
    }

    @Test
    void get_storage_location_test_storage_location_after_setting() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargo = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 1);
        dryBulkAndUnitisedCargo.setStorageLocation(0);
        assertEquals(0, dryBulkAndUnitisedCargo.getStorageLocation());
    }

    // Testing getValue()
    @Test
    void get_value_after_instancing() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 1);
        assertEquals(BigDecimal.valueOf(44.67), dryBulkAndUnitisedCargoImpl.getValue());
    }

    // Testing Hazards()
    @Test
    void get_hazards_with_no_hazards() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 1);
        assertEquals(hazards, dryBulkAndUnitisedCargoImpl.getHazards());
    }

    @Test
    void get_hazards_with_one_hazard() {
        hazards.add(Hazard.radioactive);
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 1);
        assertEquals(hazards, dryBulkAndUnitisedCargoImpl.getHazards());
    }

    @Test
    void get_hazards_with_all_hazards() {
        hazards.add(Hazard.radioactive);
        hazards.add(Hazard.toxic);
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.explosive);
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 1);
        assertEquals(hazards, dryBulkAndUnitisedCargoImpl.getHazards());
    }

    // Testing getGrainSize()
    @Test
    void get_grain_size_after_instancing() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertEquals(3, dryBulkAndUnitisedCargoImpl.getGrainSize());
    }

    // Testing isFragile()
    @Test
    void is_fragile_is_true_after_instancing() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertTrue(dryBulkAndUnitisedCargoImpl.isFragile());
    }

    @Test
    void is_fragile_is_false_after_instancing() {
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, false, 3);
        assertFalse(dryBulkAndUnitisedCargoImpl.isFragile());
    }

    // Testing getInsertDate()
    @Test
    void get_insert_date_after_instancing() {
        Date date = new Date();
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, date, BigDecimal.valueOf(44.67), hazards, false, 3);
        assertEquals(date, dryBulkAndUnitisedCargoImpl.getInsertDate());
    }

    // Testing toString()
    @Test
    void test_if_to_string_contains_storage_location_0_is_true() {
        Date date = new Date();
        DryBulkAndUnitisedCargoImpl dryBulkAndUnitisedCargoImpl = new DryBulkAndUnitisedCargoImpl(customer, 0, date, BigDecimal.valueOf(44.67), hazards, false, 3);
        String result = dryBulkAndUnitisedCargoImpl.toString();
        assertTrue(result.contains("0"));
    }
}