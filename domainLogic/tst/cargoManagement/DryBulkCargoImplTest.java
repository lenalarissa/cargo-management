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

class DryBulkCargoImplTest {

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
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        assertEquals(customer, dryBulkCargoImpl.getOwner());
    }

    @Test
    void get_owner_using_interface() {
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(dryBulkCargoImpl.getStorageLocation(), dryBulkCargoImpl);
        assertEquals(customer, cargos.get(0).getOwner());
    }

    // Testing getDurationOfStorage
    @Test
    void get_duration_of_storage_test_duration() {
        Date date = new Date();
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        long expectedDurationMillis = Duration.between(date.toInstant(), new Date().toInstant()).toMillis();
        long actualDurationMillis = dryBulkCargoImpl.getDurationOfStorage().toMillis();
        long toleranceMillis = 1;
        assertTrue(Math.abs(expectedDurationMillis - actualDurationMillis) <= toleranceMillis);
    }

    // Testing getLastInspectionDate() & setLastInspectionDate()
    @Test
    void set_and_get_last_inspection_date_test_date() {
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        Date date = new Date();
        dryBulkCargoImpl.setLastInspectionDate(date);
        assertEquals(date.toInstant(), dryBulkCargoImpl.getLastInspectionDate().toInstant());
    }

    @Test
    void set_and_get_last_inspection_date_test_not_null() {
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        dryBulkCargoImpl.setLastInspectionDate(new Date());
        assertNotNull(dryBulkCargoImpl.getLastInspectionDate());
    }

    @Test
    void get_last_inspection_date_test_if_null_when_not_set() {
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        assertNull(dryBulkCargoImpl.getLastInspectionDate());
    }

    // Testing getStorageLocation() & setStorageLocation()
    @Test
    void get_storage_location_test_storage_location_after_instancing() {
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        dryBulkCargoImpl.setStorageLocation(4);
        assertEquals(4, dryBulkCargoImpl.getStorageLocation());
    }

    @Test
    void get_storage_location_test_storage_location_after_setting() {
        DryBulkCargoImpl dryBulkCargo = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, 1);
        dryBulkCargo.setStorageLocation(0);
        assertEquals(0, dryBulkCargo.getStorageLocation());
    }

    // Testing getValue()
    @Test
    void get_value_after_instancing() {
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(44.67), hazards, 1);
        assertEquals(BigDecimal.valueOf(44.67), dryBulkCargoImpl.getValue());
    }

    // Testing Hazards()
    @Test
    void get_hazards_with_no_hazards() {
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        assertEquals(hazards, dryBulkCargoImpl.getHazards());
    }

    @Test
    void get_hazards_with_one_hazard() {
        Customer customer = mock(Customer.class);
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        assertEquals(hazards, dryBulkCargoImpl.getHazards());
    }

    @Test
    void get_hazards_with_all_hazards() {
        hazards.add(Hazard.radioactive);
        hazards.add(Hazard.toxic);
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.explosive);
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        assertEquals(hazards, dryBulkCargoImpl.getHazards());
    }

    // Testing getGrainSize()
    @Test
    void get_grain_size_after_instancing() {
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 3);
        assertEquals(3, dryBulkCargoImpl.getGrainSize());
    }

    // Testing getInsertDate()
    @Test
    void get_insert_date_after_instancing() {
        Date date = new Date();
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 1);
        ;
        assertEquals(date, dryBulkCargoImpl.getInsertDate());
    }

    // Testing toString()
    @Test
    void test_if_to_string_contains_storage_location_0_is_true() {
        Date date = new Date();
        DryBulkCargoImpl dryBulkCargoImpl = new DryBulkCargoImpl(customer, 0, new Date(), BigDecimal.valueOf(1), hazards, 3);
        String result = dryBulkCargoImpl.toString();
        assertTrue(result.contains("0"));
    }
}