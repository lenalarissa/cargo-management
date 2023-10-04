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

class UnitisedCargoImplTest {

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
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(customer, unitisedCargoImpl.getOwner());
    }
    @Test
    void get_owner_using_interface() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(unitisedCargoImpl.getStorageLocation(), unitisedCargoImpl);
        assertEquals(customer, cargos.get(0).getOwner());
    }

    // Testing getDurationOfStorage
    @Test
    void get_duration_of_storage_test_duration() {
        Date date = new Date();
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        long expectedDurationMillis = Duration.between(date.toInstant(), new Date().toInstant()).toMillis();
        long actualDurationMillis = unitisedCargoImpl.getDurationOfStorage().toMillis();
        long toleranceMillis = 10;
        assertTrue(Math.abs(expectedDurationMillis - actualDurationMillis) <= toleranceMillis);
    }

    // Testing getLastInspectionDate() & setLastInspectionDate()
    @Test
    void set_and_get_last_inspection_date_test_date() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        Date date = new Date();
        unitisedCargoImpl.setLastInspectionDate(date);
        assertEquals(date.toInstant(), unitisedCargoImpl.getLastInspectionDate().toInstant());
    }
    @Test
    void set_and_get_last_inspection_date_test_not_null() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        unitisedCargoImpl.setLastInspectionDate(new Date());
        assertNotNull(unitisedCargoImpl.getLastInspectionDate());
    }
    @Test
    void get_last_inspection_date_test_if_null_when_not_set() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertNull(unitisedCargoImpl.getLastInspectionDate());
    }

    // Testing getStorageLocation() & setStorageLocation()
    @Test
    void get_storage_location_test_storage_location_after_instancing() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        unitisedCargoImpl.setStorageLocation(4);
        assertEquals(4, unitisedCargoImpl.getStorageLocation());
    }
    @Test
    void get_storage_location_test_storage_location_after_setting() {
        UnitisedCargoImpl unitisedCargo = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        unitisedCargo.setStorageLocation(0);
        assertEquals(0, unitisedCargo.getStorageLocation());
    }

    // Testing getValue()
    @Test
    void get_value_after_instancing() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(BigDecimal.valueOf(44.67), unitisedCargoImpl.getValue());
    }

    // Testing Hazards()
    @Test
    void get_hazards_with_no_hazards() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(hazards, unitisedCargoImpl.getHazards());
    }
    @Test
    void get_hazards_with_one_hazard() {
        hazards.add(Hazard.radioactive);
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(hazards, unitisedCargoImpl.getHazards());
    }
    @Test
    void get_hazards_with_all_hazards() {
        hazards.add(Hazard.radioactive);
        hazards.add(Hazard.toxic);
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.explosive);
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(hazards, unitisedCargoImpl.getHazards());
    }

    // Testing getInsertDate()
    @Test
    void get_insert_date_after_instancing() {
        Date date = new Date();
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(date, unitisedCargoImpl.getInsertDate());
    }

    // Testing toString()
    @Test
    void test_if_to_string_contains_storage_location_0_is_true() {
        Date date = new Date();
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        String result = unitisedCargoImpl.toString();
        assertTrue(result.contains("0"));
    }

    // Testing isFragile()
    @Test
    void is_fragile_is_true_after_instancing() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertTrue(unitisedCargoImpl.isFragile());
    }
    @Test
    void is_fragile_is_false_after_instancing() {
        UnitisedCargoImpl unitisedCargoImpl = new UnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, false);
        assertFalse(unitisedCargoImpl.isFragile());
    }
}