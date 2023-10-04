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

class CapacityObserverTest {

    private Number monitor;
    private CustomerManagement customerManagement;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;


    @BeforeEach
    void setUp(){
        monitor = new Integer(0);
        customerManagement = new CustomerManagement(monitor);
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    // Testing update() directly
    @Test
    void no_update_with_capacity_of_0_and_0_cargos_added_test_output() {
        CargoStorage cargoStorage = new CargoStorage(0, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CapacityObserver capacityObserver = new CapacityObserver(managementFacade);

        capacityObserver.update();
        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertNotEquals("90% of storage capacity exceeded", output);
    }

    // Testing update() indirectly (to be able to provoke the output, since update cannot have any arguments)
    @Test
    void update_when_90_percent_exceeded_with_capacity_of_one_test_output() {
        CargoStorage cargoStorage = new CargoStorage(1, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CapacityObserver capacityObserver = new CapacityObserver(managementFacade);
        cargoStorage.register(capacityObserver);
        customerManagement.createCustomer("Emily");

        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), new HashSet<>(), true, false, 12);

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertEquals("90% of storage capacity exceeded", output);
    }

    @Test
    void update_when_90_percent_exceeded_with_capacity_of_11_test_output() {
        CargoStorage cargoStorage = new CargoStorage(11, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CapacityObserver capacityObserver = new CapacityObserver(managementFacade);
        cargoStorage.register(capacityObserver);
        customerManagement.createCustomer("Emily");

        for (int i = 0; i < 11; i++) {
            cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), new HashSet<>(), true, false, 12);
        }

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertEquals("90% of storage capacity exceeded", output);
    }

    @Test
    void update_when_90_percent_exceeded_with_capacity_of_11_test_after_deleting_cargos_test_output() {
        CargoStorage cargoStorage = new CargoStorage(11, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CapacityObserver capacityObserver = new CapacityObserver(managementFacade);
        cargoStorage.register(capacityObserver);
        customerManagement.createCustomer("Emily");

        for (int i = 0; i < 11; i++) {
            cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), new HashSet<>(), true, false, 12);
        }
        cargoStorage.deleteCargo(9);
        cargoStorage.deleteCargo(10);
        cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), new HashSet<>(), true, false, 12);

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertEquals("90% of storage capacity exceeded" + System.getProperty("line.separator") + "90% of storage capacity exceeded", output);
    }

    @Test
    void no_update_with_capacity_of_100_and_90_cargos_added_test_output() {
        CargoStorage cargoStorage = new CargoStorage(100, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CapacityObserver capacityObserver = new CapacityObserver(managementFacade);
        cargoStorage.register(capacityObserver);
        customerManagement.createCustomer("Emily");

        for (int i = 0; i < 90; i++) {
            cargoStorage.createCargo("DryBulkAndUnitisedCargo", "Emily", BigDecimal.valueOf(3), new HashSet<>(), true, false, 12);
        }

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertNotEquals("90% of storage capacity exceeded", output);
    }
}