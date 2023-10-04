package observer;

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

class CargosObserverTest {

    private Number monitor;
    private CustomerManagement customerManagement;
    private CargoStorage cargoStorage;
    private ManagementFacade managementFacade;
    private CargosObserver cargosObserver;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp(){
        monitor = new Integer(0);
        customerManagement = new CustomerManagement(monitor);
        cargoStorage = new CargoStorage(1, customerManagement, monitor);
        managementFacade = new ManagementFacade(cargoStorage, customerManagement);
        cargosObserver = new CargosObserver(managementFacade);

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    // Testing update() directly
    @Test
    void update_without_any_cargos_added_ever_result_true() {
        cargosObserver.update();
        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertTrue(output.contains("cargos in warehouse: " + 0));
    }

    // Testing update() indirectly (to be able to provoke certain outputs, since update cannot have any arguments)
    @Test
    void update_test_output_with_added_cargo_result_true() {
        cargoStorage.register(cargosObserver);
        customerManagement.createCustomer("Emily");

        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), new HashSet<>(), true, false, 12);

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertTrue(output.contains("cargos in warehouse: " + 1));
    }

    @Test
    void update_test_output_with_one_deleted_cargo() {
        customerManagement.createCustomer("Emily");

        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), new HashSet<>(), true, false, 12);
        cargoStorage.register(cargosObserver);
        cargoStorage.deleteCargo(0);

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertTrue(output.contains("cargos in warehouse: " + 0));
    }
}