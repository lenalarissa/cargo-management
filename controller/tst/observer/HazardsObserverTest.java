package observer;

import cargo.Hazard;
import cargoManagement.CargoStorage;
import customerManagement.CustomerManagement;
import domainLogicManagement.ManagementFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class HazardsObserverTest {

    private CustomerManagement customerManagement;
    private CargoStorage cargoStorage;
    private HazardsObserver hazardsObserver;
    private Number monitor = new Integer(0);
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        customerManagement = new CustomerManagement(monitor);
        cargoStorage = new CargoStorage(3, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);
        hazardsObserver = new HazardsObserver(managementFacade);
        cargoStorage.register(hazardsObserver);
        customerManagement.createCustomer("Emily");

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    // Testing update() directly
    @Test
    void update_when_no_cargo_is_added_ever_result_false() {
        hazardsObserver.update();
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertFalse(output.contains("Hazards changed to:"));
    }

    // Testing update() indirectly (to be able to provoke certain outputs, since update cannot have any arguments)
    @Test
    void update_when_first_cargo_was_added_with_new_hazards_result_true() {
        HashSet<Hazard> hazards = new HashSet<>();
        hazards.add(Hazard.toxic);
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertEquals("Hazards changed to: [toxic]", output);
    }

    @Test
    void update_when_cargo_was_added_without_new_hazards_result_false() {
         HashSet<Hazard> hazards = new HashSet<>();
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertFalse(output.contains("Hazards changed to:"));
    }

    @Test
    void update_when_cargo_was_deleted_that_had_new_hazards_result_true() {
       HashSet<Hazard> hazards = new HashSet<>();
        hazards.add(Hazard.toxic);
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.deleteCargo(0);
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertEquals("Hazards changed to: [toxic]" + System.getProperty("line.separator") + "Hazards changed to: []", output);
    }

    @Test
    void update_when_cargo_was_deleted_that_did_not_have_new_hazards_result_true() {
        HashSet<Hazard> hazards = new HashSet<>();
        hazards.add(Hazard.toxic);
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        cargoStorage.deleteCargo(1);
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertEquals("Hazards changed to: [toxic]", output);
    }

    @Test
    void update_when_all_hazards_are_in_list_result_true() {
        HashSet<Hazard> hazards = new HashSet<>();
        hazards.add(Hazard.toxic);
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.explosive);
        hazards.add(Hazard.radioactive);
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), hazards, true, false, 12);
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertTrue(output.contains("toxic") && output.contains("flammable") && output.contains("explosive") && output.contains("radioactive"));
    }
}