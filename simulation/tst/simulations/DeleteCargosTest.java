package simulations;

import cargo.Cargo;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import events.StorageLocationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteCargosTest {

    // Testing Simulation 1 and 2
    private DeleteCargos deleteCargos;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;


    @BeforeEach
    void setUp() {
        deleteCargos = new DeleteCargos(new Object());
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void setReadCargosHandler_test_if_handler_can_handle() {
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        deleteCargos.setReadCargosHandler(readCargosHandler);
        deleteCargos.execute();
        verify(readCargosHandler).handle(any(CargoClassEvent.class));
    }

    @Test
    void not_setReadCargosHandler_test_if_handler_cannot_handle() {
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        deleteCargos.execute();
        verify(readCargosHandler, never()).handle(any(CargoClassEvent.class));
    }

    @Test
    void setDeleteCargoHandler_test_if_handler_can_handle() {
        Handler<StorageLocationEvent> deleteCargoHandler = mock(Handler.class);
        deleteCargos.setDeleteCargoHandler(deleteCargoHandler);
        deleteCargos.deleteCargo(0);
        verify(deleteCargoHandler).handle(any(StorageLocationEvent.class));
    }

    @Test
    void not_setDeleteCargoHandler_test_if_handler_cannot_handle() {
        Handler<StorageLocationEvent> deleteCargoHandler = mock(Handler.class);
        deleteCargos.deleteCargo(0);
        verify(deleteCargoHandler, never()).handle(any(StorageLocationEvent.class));
    }

    @Test
    void setCargos_test_if_cargo_size_is_called() {
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        deleteCargos.setReadCargosHandler(readCargosHandler);
        HashMap<Integer, Cargo> cargos = mock(HashMap.class);
        cargos.put(0, mock(Cargo.class));
        deleteCargos.setCargos(cargos);
        deleteCargos.execute();
        verify(cargos).size();
    }

    @Test
    void not_setCargos_test_if_cargo_size_is_not_called() {
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        deleteCargos.setReadCargosHandler(readCargosHandler);
        HashMap<Integer, Cargo> cargos = mock(HashMap.class);
        cargos.put(0, mock(Cargo.class));
        deleteCargos.execute();
        verify(cargos, never()).size();
    }

    @Test
    void randomStorageLocation_test_with_contained_key() {
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(0, mock(Cargo.class));
        deleteCargos.setCargos(cargos);
        int result = deleteCargos.randomStorageLocation(0);
        assertEquals(0, result);
    }

    @Test
    void randomStorageLocation_test_with_not_contained_key() {
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(0, mock(Cargo.class));
        deleteCargos.setCargos(cargos);
        int result = deleteCargos.randomStorageLocation(1);
        assertEquals(-1, result);
    }

    @Test
    void deleteCargo_with_handler_set_test_output() {
        Handler<StorageLocationEvent> deleteCargoHandler = new Handler<>();
        deleteCargos.setDeleteCargoHandler(deleteCargoHandler);
        deleteCargos.deleteCargo(0);
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertTrue(output.contains(" tries to delete a cargo"));
    }

    @Test
    void deleteCargo_with_handler_not_set_test_output() {
        deleteCargos.deleteCargo(0);
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertFalse(output.contains(" tries to delete a cargo"));
    }

    @Test
    void deleteCargo_test_if_handle_is_called() {
        Handler<StorageLocationEvent> deleteCargoHandler = mock(Handler.class);
        deleteCargos.setDeleteCargoHandler(deleteCargoHandler);
        deleteCargos.deleteCargo(0);
        verify(deleteCargoHandler).handle(any());
    }

    @Test
    void execute_with_cargo_list_size_0_test_output() {
        Handler<CargoClassEvent> readCargosHandler = new Handler<>();
        deleteCargos.setReadCargosHandler(readCargosHandler);
        Handler<StorageLocationEvent> deleteCargoHandler = new Handler<>();
        deleteCargos.setDeleteCargoHandler(deleteCargoHandler);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        deleteCargos.setCargos(cargos);
        deleteCargos.execute();

        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertFalse(output.contains(" tries to delete a cargo"));
    }

    @Test
    void execute_with_cargo_list_size_1() {
        Handler<CargoClassEvent> readCargosHandler = new Handler<>();
        deleteCargos.setReadCargosHandler(readCargosHandler);
        Handler<StorageLocationEvent> deleteCargoHandler = new Handler<>();
        deleteCargos.setDeleteCargoHandler(deleteCargoHandler);
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(0, mock(Cargo.class));
        deleteCargos.setCargos(cargos);
        deleteCargos.execute();

        String output = outputStream.toString().trim();
        System.setOut(originalOut);

        assertTrue(output.contains(" tries to delete a cargo"));
    }

    @Test
    void findOldestCargo_with_two_cargos_in_list() {
        Cargo cargo1 = mock(Cargo.class);
        Cargo cargo2 = mock(Cargo.class);
        when(cargo1.getDurationOfStorage()).thenReturn(Duration.ofDays(10));
        when(cargo2.getDurationOfStorage()).thenReturn(Duration.ofDays(20));
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(1, cargo1);
        cargos.put(2, cargo2);

        Cargo oldestCargo = deleteCargos.findOldestCargo(cargos);
        assertEquals(cargo2, oldestCargo);
    }

    @Test
    void findOldestCargo_with_one_cargo_in_list() {
        Cargo cargo1 = mock(Cargo.class);
        when(cargo1.getDurationOfStorage()).thenReturn(Duration.ofDays(10));
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(1, cargo1);

        Cargo oldestCargo = deleteCargos.findOldestCargo(cargos);
        assertEquals(cargo1, oldestCargo);
    }

    @Test
    void findOldestCargo_with_three_cargos_in_list() {
        Cargo cargo1 = mock(Cargo.class);
        Cargo cargo2 = mock(Cargo.class);
        Cargo cargo3 = mock(Cargo.class);
        when(cargo1.getDurationOfStorage()).thenReturn(Duration.ofDays(20));
        when(cargo2.getDurationOfStorage()).thenReturn(Duration.ofDays(30));
        when(cargo3.getDurationOfStorage()).thenReturn(Duration.ofDays(10));
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        cargos.put(1, cargo1);
        cargos.put(2, cargo2);
        cargos.put(3, cargo3);
        Cargo oldestCargo = deleteCargos.findOldestCargo(cargos);
        assertEquals(cargo2, oldestCargo);
    }

    // Testing Simulation 3
    @Test
    void execute_when_handler_not_set_test_if_wait_not_called() {
        DeleteCargos deleteCargosAlt = spy(new DeleteCargos(new Object(), true, new CargoStorageWatcher()));
        deleteCargosAlt.execute();
        verify(deleteCargosAlt, never()).wait(any());
    }

    @Test
    void execute_test_if_wait_is_called() {
        CargoStorageWatcher cargoStorageWatcher = new CargoStorageWatcher();
        DeleteCargos deleteCargosAlt = spy(new DeleteCargos(new Object(), true, cargoStorageWatcher));
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        deleteCargosAlt.setReadCargosHandler(readCargosHandler);

        HashMap<Integer, Cargo> cargos = new HashMap<>();
        Cargo cargo1 = mock(Cargo.class);
        when(cargo1.getStorageLocation()).thenReturn(0);
        when(cargo1.getDurationOfStorage()).thenReturn(Duration.ofDays(20));
        cargos.put(1, cargo1);
        deleteCargosAlt.setCargos(cargos);

        cargoStorageWatcher.setStorageIsFull(true);
        deleteCargosAlt.execute();
        verify(deleteCargosAlt).wait(any());
    }

    @Test
    void wait_test_return_with_storage_bigger_than_0() {
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        Cargo cargo1 = mock(Cargo.class);
        when(cargo1.getStorageLocation()).thenReturn(0);
        when(cargo1.getDurationOfStorage()).thenReturn(Duration.ofDays(20));
        cargos.put(1, cargo1);
        deleteCargos.setCargos(cargos);
        boolean result = deleteCargos.wait(cargos);
        assertFalse(result);
    }
}