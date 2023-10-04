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

class LiquidBulkCargoImplTest {

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
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(customer, liquidBulkCargoImpl.getOwner());
    }
    @Test
    void get_owner_using_interface() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(liquidBulkCargoImpl.getStorageLocation(), liquidBulkCargoImpl);
        assertEquals(customer, cargos.get(0).getOwner());
    }

    // Testing getDurationOfStorage
    @Test
    void get_duration_of_storage_test_duration() {
        Date date = new Date();
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        long expectedDurationMillis = Duration.between(date.toInstant(), new Date().toInstant()).toMillis();
        long actualDurationMillis = liquidBulkCargoImpl.getDurationOfStorage().toMillis();
        long toleranceMillis = 1;
        assertTrue(Math.abs(expectedDurationMillis - actualDurationMillis) <= toleranceMillis);
    }

    // Testing getLastInspectionDate() & setLastInspectionDate()
    @Test
    void set_and_get_last_inspection_date_test_date() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        Date date = new Date();
        liquidBulkCargoImpl.setLastInspectionDate(date);
        assertEquals(date.toInstant(), liquidBulkCargoImpl.getLastInspectionDate().toInstant());
    }
    @Test
    void set_and_get_last_inspection_date_test_not_null() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        liquidBulkCargoImpl.setLastInspectionDate(new Date());
        assertNotNull(liquidBulkCargoImpl.getLastInspectionDate());
    }
    @Test
    void get_last_inspection_date_test_if_null_when_not_set() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertNull(liquidBulkCargoImpl.getLastInspectionDate());
    }

    // Testing getStorageLocation() & setStorageLocation()
    @Test
    void get_storage_location_test_storage_location_after_instancing() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        liquidBulkCargoImpl.setStorageLocation(4);
        assertEquals(4, liquidBulkCargoImpl.getStorageLocation());
    }
    @Test
    void get_storage_location_test_storage_location_after_setting() {
        LiquidBulkCargoImpl liquidBulkCargo = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        liquidBulkCargo.setStorageLocation(0);
        assertEquals(0, liquidBulkCargo.getStorageLocation());
    }

    // Testing getValue()
    @Test
    void get_value_after_instancing() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(BigDecimal.valueOf(44.67), liquidBulkCargoImpl.getValue());
    }

    // Testing Hazards()
    @Test
    void get_hazards_with_no_hazards() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(hazards, liquidBulkCargoImpl.getHazards());
    }
    @Test
    void get_hazards_with_one_hazard() {
        hazards.add(Hazard.radioactive);
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(hazards, liquidBulkCargoImpl.getHazards());
    }
    @Test
    void get_hazards_with_all_hazards() {
        hazards.add(Hazard.radioactive);
        hazards.add(Hazard.toxic);
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.explosive);
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(hazards, liquidBulkCargoImpl.getHazards());
    }

    // Testing getInsertDate()
    @Test
    void get_insert_date_after_instancing() {
        Date date = new Date();
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertEquals(date, liquidBulkCargoImpl.getInsertDate());
    }

    // Testing toString()
    @Test
    void test_if_to_string_contains_storage_location_0_is_true() {
        Date date = new Date();
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        String result = liquidBulkCargoImpl.toString();
        assertTrue(result.contains("0"));
    }

    // Testing isPressurized()
    @Test
    void is_pressurized_is_true_after_instancing() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true);
        assertTrue(liquidBulkCargoImpl.isPressurized());
    }
    @Test
    void is_pressurized_is_false_after_instancing() {
        LiquidBulkCargoImpl liquidBulkCargoImpl = new LiquidBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, false);
        assertFalse(liquidBulkCargoImpl.isPressurized());
    }
}