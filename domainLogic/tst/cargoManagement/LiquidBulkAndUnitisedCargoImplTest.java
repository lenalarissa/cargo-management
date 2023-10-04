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

class LiquidBulkAndUnitisedCargoImplTest {

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
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertEquals(customer, liquidBulkAndUnitisedCargoImpl.getOwner());
    }
    @Test
    void get_owner_using_interface() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(liquidBulkAndUnitisedCargoImpl.getStorageLocation(), liquidBulkAndUnitisedCargoImpl);
        assertEquals(customer, cargos.get(0).getOwner());
    }

    // Testing getDurationOfStorage
    @Test
    void get_duration_of_storage_test_duration() {
        Date date = new Date();
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        long expectedDurationMillis = Duration.between(date.toInstant(), new Date().toInstant()).toMillis();
        long actualDurationMillis = liquidBulkAndUnitisedCargoImpl.getDurationOfStorage().toMillis();
        long toleranceMillis = 1;
        assertTrue(Math.abs(expectedDurationMillis - actualDurationMillis) <= toleranceMillis);
    }

    // Testing getLastInspectionDate() & setLastInspectionDate()
    @Test
    void set_and_get_last_inspection_date_test_date() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        Date date = new Date();
        liquidBulkAndUnitisedCargoImpl.setLastInspectionDate(date);
        assertEquals(date.toInstant(), liquidBulkAndUnitisedCargoImpl.getLastInspectionDate().toInstant());
    }
    @Test
    void set_and_get_last_inspection_date_test_not_null() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        liquidBulkAndUnitisedCargoImpl.setLastInspectionDate(new Date());
        assertNotNull(liquidBulkAndUnitisedCargoImpl.getLastInspectionDate());
    }
    @Test
    void get_last_inspection_date_test_if_null_when_not_set() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertNull(liquidBulkAndUnitisedCargoImpl.getLastInspectionDate());
    }

    // Testing getStorageLocation() & setStorageLocation()
    @Test
    void get_storage_location_test_storage_location_after_instancing() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        liquidBulkAndUnitisedCargoImpl.setStorageLocation(4);
        assertEquals(4, liquidBulkAndUnitisedCargoImpl.getStorageLocation());
    }
    @Test
    void get_storage_location_test_storage_location_after_setting() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        liquidBulkAndUnitisedCargoImpl.setStorageLocation(0);
        assertEquals(0, liquidBulkAndUnitisedCargoImpl.getStorageLocation());
    }

    // Testing getValue()
    @Test
    void get_value_after_instancing() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertEquals(BigDecimal.valueOf(44.67), liquidBulkAndUnitisedCargoImpl.getValue());
    }

    // Testing Hazards()
    @Test
    void get_hazards_with_no_hazards() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertEquals(hazards, liquidBulkAndUnitisedCargoImpl.getHazards());
    }
    @Test
    void get_hazards_with_one_hazard() {
        hazards.add(Hazard.radioactive);
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertEquals(hazards, liquidBulkAndUnitisedCargoImpl.getHazards());
    }
    @Test
    void get_hazards_with_all_hazards() {
        hazards.add(Hazard.radioactive);
        hazards.add(Hazard.toxic);
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.explosive);
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertEquals(hazards, liquidBulkAndUnitisedCargoImpl.getHazards());
    }

    // Testing getInsertDate()
    @Test
    void get_insert_date_after_instancing() {
        Date date = new Date();
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertEquals(date, liquidBulkAndUnitisedCargoImpl.getInsertDate());
    }

    // Testing toString()
    @Test
    void test_if_to_string_contains_storage_location_0_is_true() {
        Date date = new Date();
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        String result = liquidBulkAndUnitisedCargoImpl.toString();
        assertTrue(result.contains("0"));
    }

    // Testing isPressurized()
    @Test
    void is_pressurized_is_true_after_instancing() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertTrue(liquidBulkAndUnitisedCargoImpl.isPressurized());
    }
    @Test
    void is_pressurized_is_false_after_instancing() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,false);
        assertFalse(liquidBulkAndUnitisedCargoImpl.isPressurized());
    }

    // Testing isFragile()
    @Test
    void is_fragile_is_true_after_instancing() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true,true);
        assertTrue(liquidBulkAndUnitisedCargoImpl.isFragile());
    }
    @Test
    void is_fragile_is_false_after_instancing() {
        LiquidBulkAndUnitisedCargoImpl liquidBulkAndUnitisedCargoImpl = new LiquidBulkAndUnitisedCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, false,true);
        assertFalse(liquidBulkAndUnitisedCargoImpl.isFragile());
    }
}