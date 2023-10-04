package simulations;

import cargo.Hazard;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import events.CargoEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateCargosTest {

    // Testing CreateCargos for Simulation 1 and 2
    private CreateCargos createCargos;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        createCargos = new CreateCargos(new Object());
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void test_if_handler_is_set() {
        Handler<CargoEvent> createCargoHandler = mock(Handler.class);
        createCargos.setCreateCargoHandler(createCargoHandler);
        createCargos.createCargo("DryBulkCargo", "Emil", BigDecimal.valueOf(1), new HashSet<Hazard>(), true, false, 1);
        verify(createCargoHandler).handle(any(CargoEvent.class));
    }

    @Test
    void test_randomCargoClass_with_index_0() {
        String result = createCargos.randomCargoClass(0);
        assertEquals("DryBulkAndUnitisedCargo", result);
    }

    @Test
    void test_randomCargoClass_with_index_5() {
        String result = createCargos.randomCargoClass(5);
        assertEquals("UnitisedCargo", result);
    }

    @Test
    void test_randomName_with_index_0() {
        String result = createCargos.randomName(0);
        assertEquals("Abel", result);
    }

    @Test
    void test_randomName_with_index_24() {
        String result = createCargos.randomName(24);
        assertEquals("Zac", result);
    }

    @Test
    void test_randomHazards_with_index_0() {
        HashSet<Hazard> result = createCargos.randomHazards(0);
        assertEquals(0, result.size());
    }

    @Test
    void test_randomHazards_with_index_15() {
        HashSet<Hazard> result = createCargos.randomHazards(15);
        assertTrue(result.contains(Hazard.explosive) && result.contains(Hazard.radioactive) && result.contains(Hazard.flammable) && result.contains(Hazard.toxic));
    }

    @Test
    void test_createCargo_correctly() {
        Handler<CargoEvent> createCargoHandler = new Handler<>();
        createCargos.setCreateCargoHandler(createCargoHandler);
        createCargos.createCargo("DryBulkCargo", "Emil", BigDecimal.valueOf(1), new HashSet<Hazard>(), true, false, 1);

        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertTrue(output.contains(" tries to create a cargo"));
    }

    @Test
    void test_createCargo_with_handler_not_set() {
        createCargos.createCargo("DryBulkCargo", "Emil", BigDecimal.valueOf(1), new HashSet<Hazard>(), true, false, 1);

        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertFalse(output.contains(" tries to create a cargo"));
    }

    // Testing CreateCargos for Simulation 3
    @Test
    void createCargo_for_sim3_with_no_read_handler_set_test_output() {
        CreateCargos createCargosSim3 = new CreateCargos(new Object(), true, 0, new CargoStorageWatcher());
        Handler<CargoEvent> createCargoHandler = new Handler<>();
        createCargosSim3.setCreateCargoHandler(createCargoHandler);
        createCargosSim3.createCargo("DryBulkCargo", "Emil", BigDecimal.valueOf(1), new HashSet<Hazard>(), true, false, 1);

        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertTrue(output.contains(" tries to create a cargo"));
    }

    @Test
    void createCargo_for_sim3_test_if_read_handler_is_set_correctly() {
        CreateCargos createCargosSim3 = new CreateCargos(new Object(), true, 0, new CargoStorageWatcher());
        Handler<CargoEvent> createCargoHandler = new Handler<>();
        createCargosSim3.setCreateCargoHandler(createCargoHandler);
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        createCargosSim3.setReadCargosHandler(readCargosHandler);
        createCargosSim3.createCargo("DryBulkCargo", "Emil", BigDecimal.valueOf(1), new HashSet<Hazard>(), true, false, 1);
        verify(readCargosHandler).handle(any());
    }

    @Test
    void createCargo_for_sim3_test_if_wait_is_called() {
        CreateCargos createCargosSim3 = spy(new CreateCargos(new Object(), true, 1, new CargoStorageWatcher()));
        Handler<CargoEvent> createCargoHandler = new Handler<>();
        createCargosSim3.setCreateCargoHandler(createCargoHandler);
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        createCargosSim3.setReadCargosHandler(readCargosHandler);
        createCargosSim3.setCargos(new HashMap<>());
        createCargosSim3.createCargo("DryBulkCargo", "Emil", BigDecimal.valueOf(1), new HashSet<Hazard>(), true, false, 1);
        verify(createCargosSim3).wait(any());
    }

    @Test
    void test_wait_method_when_storage_is_not_full_result_false() {
        CreateCargos createCargosSim3 = new CreateCargos(new Object(), true, 1, new CargoStorageWatcher());
        boolean result = createCargosSim3.wait(new HashMap<>());
        assertFalse(result);
    }
}