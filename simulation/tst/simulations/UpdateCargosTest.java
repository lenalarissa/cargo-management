package simulations;

import cargo.Cargo;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import events.StorageLocationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateCargosTest {

    private UpdateCargos updateCargos;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        updateCargos = new UpdateCargos(new Object());
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void randomStorageLocation_test_with_contained_key() {
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(0, mock(Cargo.class));
        updateCargos.setCargos(cargos);
        int result = updateCargos.randomStorageLocation(0);
        assertEquals(0, result);
    }

    @Test
    void randomStorageLocation_test_with_not_contained_key() {
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(0, mock(Cargo.class));
        updateCargos.setCargos(cargos);
        int result = updateCargos.randomStorageLocation(1);
        assertEquals(-1, result);
    }

    @Test
    void updateCargo_with_handler_set_test_output() {
        Handler<StorageLocationEvent> updateCargoHandler = new Handler<>();
        updateCargos.setUpdateCargoHandler(updateCargoHandler);
        updateCargos.updateCargo(0);
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertTrue(output.contains(" tries to update a cargo"));
    }

    @Test
    void updateCargo_with_handler_not_set_test_output() {
        updateCargos.updateCargo(0);
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertFalse(output.contains(" tries to update a cargo"));
    }

    @Test
    void deleteCargo_test_if_handle_is_called() {
        Handler<StorageLocationEvent> updateCargoHandler = mock(Handler.class);
        updateCargos.setUpdateCargoHandler(updateCargoHandler);
        updateCargos.updateCargo(0);
        verify(updateCargoHandler).handle(any());
    }

    @Test
    void execute_with_cargo_list_size_0_result_false() {
        Handler<CargoClassEvent> readCargosHandler = new Handler<>();
        updateCargos.setReadCargosHandler(readCargosHandler);
        Handler<StorageLocationEvent> updateCargoHandler = new Handler<>();
        updateCargos.setUpdateCargoHandler(updateCargoHandler);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        updateCargos.setCargos(cargos);
        updateCargos.execute();

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertFalse(output.contains(" tries to update a cargo"));
    }

    @Test
    void execute_with_cargo_list_size_1_result_true() {
        Handler<CargoClassEvent> readCargosHandler = new Handler<>();
        updateCargos.setReadCargosHandler(readCargosHandler);
        Handler<StorageLocationEvent> updateCargoHandler = new Handler<>();
        updateCargos.setUpdateCargoHandler(updateCargoHandler);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(0, mock(Cargo.class));
        updateCargos.setCargos(cargos);
        updateCargos.execute();

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertTrue(output.contains(" tries to update a cargo"));
    }
}