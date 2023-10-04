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

class LiquidAndDryBulkCargoImplTest {

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
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertEquals(customer, liquidAndDryBulkCargoImpl.getOwner());
    }

    @Test
    void get_owner_using_interface() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(liquidAndDryBulkCargoImpl.getStorageLocation(), liquidAndDryBulkCargoImpl);
        assertEquals(customer, cargos.get(0).getOwner());
    }

    // Testing getDurationOfStorage
    @Test
    void get_duration_of_storage_test_duration() {
        Date date = new Date();
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        long expectedDurationMillis = Duration.between(date.toInstant(), new Date().toInstant()).toMillis();
        long actualDurationMillis = liquidAndDryBulkCargoImpl.getDurationOfStorage().toMillis();
        long toleranceMillis = 1;
        assertTrue(Math.abs(expectedDurationMillis - actualDurationMillis) <= toleranceMillis);
    }

    // Testing getLastInspectionDate() & setLastInspectionDate()
    @Test
    void set_and_get_last_inspection_date_test_date() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        Date date = new Date();
        liquidAndDryBulkCargoImpl.setLastInspectionDate(date);
        assertEquals(date.toInstant(), liquidAndDryBulkCargoImpl.getLastInspectionDate().toInstant());
    }

    @Test
    void set_and_get_last_inspection_date_test_not_null() {
        HashSet<Hazard> hazards = new HashSet<>();
        Customer customer = mock(Customer.class);
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        liquidAndDryBulkCargoImpl.setLastInspectionDate(new Date());
        assertNotNull(liquidAndDryBulkCargoImpl.getLastInspectionDate());
    }

    @Test
    void get_last_inspection_date_test_if_null_when_not_set() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertNull(liquidAndDryBulkCargoImpl.getLastInspectionDate());
    }

    // Testing getStorageLocation() & setStorageLocation()
    @Test
    void get_storage_location_test_storage_location_after_instancing() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        liquidAndDryBulkCargoImpl.setStorageLocation(4);
        assertEquals(4, liquidAndDryBulkCargoImpl.getStorageLocation());
    }

    @Test
    void get_storage_location_test_storage_location_after_setting() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargo = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        liquidAndDryBulkCargo.setStorageLocation(0);
        assertEquals(0, liquidAndDryBulkCargo.getStorageLocation());
    }

    // Testing getValue()
    @Test
    void get_value_after_instancing() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertEquals(BigDecimal.valueOf(44.67), liquidAndDryBulkCargoImpl.getValue());
    }

    // Testing Hazards()
    @Test
    void get_hazards_with_no_hazards() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertEquals(hazards, liquidAndDryBulkCargoImpl.getHazards());
    }

    @Test
    void get_hazards_with_one_hazard() {
        hazards.add(Hazard.radioactive);
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertEquals(hazards, liquidAndDryBulkCargoImpl.getHazards());
    }

    @Test
    void get_hazards_with_all_hazards() {
        hazards.add(Hazard.radioactive);
        hazards.add(Hazard.toxic);
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.explosive);
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertEquals(hazards, liquidAndDryBulkCargoImpl.getHazards());
    }

    // Testing getGrainSize()
    @Test
    void get_grain_size_after_instancing() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertEquals(3, liquidAndDryBulkCargoImpl.getGrainSize());
    }

    // Testing getInsertDate()
    @Test
    void get_insert_date_after_instancing() {
        Date date = new Date();
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertEquals(date, liquidAndDryBulkCargoImpl.getInsertDate());
    }

    // Testing toString()
    @Test
    void test_if_to_string_contains_storage_location_0_is_true() {
        Date date = new Date();
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        String result = liquidAndDryBulkCargoImpl.toString();
        assertTrue(result.contains("0"));
    }

    // Testing isPressurized()
    @Test
    void is_pressurized_is_true_after_instancing() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, true, 3);
        assertTrue(liquidAndDryBulkCargoImpl.isPressurized());
    }

    @Test
    void is_pressurized_is_false_after_instancing() {
        LiquidAndDryBulkCargoImpl liquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, false, 3);
        assertFalse(liquidAndDryBulkCargoImpl.isPressurized());
    }
}