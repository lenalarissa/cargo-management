package simulations;

import cargo.Cargo;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class WatchCargoStorageTest {


    @Test
    void test_if_handler_is_set() {
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        WatchCargoStorage watchCargoStorage = new WatchCargoStorage(new Object());
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        watchCargoStorage.setCargos(cargos);
        watchCargoStorage.setReadCargosHandler(readCargosHandler);
        watchCargoStorage.run();
        verify(readCargosHandler).handle(any(CargoClassEvent.class));
    }

    @Test
    void run_test_output() {
        Handler<CargoClassEvent> readCargosHandler = mock(Handler.class);
        WatchCargoStorage watchCargoStorage = new WatchCargoStorage(new Object());
        HashMap<Integer, Cargo> cargos = new HashMap<>();
        watchCargoStorage.setCargos(cargos);
        watchCargoStorage.setReadCargosHandler(readCargosHandler);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        watchCargoStorage.run();
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertTrue(output.contains("cargos in warehouse: "));
    }
}