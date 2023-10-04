package simulations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CargoStorageWatcherTest {

    private CargoStorageWatcher cargoStorageWatcher;

    @BeforeEach
    void setUp() {
        cargoStorageWatcher = new CargoStorageWatcher();
    }

    @Test
    void test_isStorageIsFull_if_true() {
        cargoStorageWatcher.setStorageIsFull(true);
        boolean result = cargoStorageWatcher.isStorageIsFull();
        assertTrue(result);
    }

    @Test
    void test_isStorageIsFull_if_false() {
        cargoStorageWatcher.setStorageIsFull(false);
        boolean result = cargoStorageWatcher.isStorageIsFull();
        assertFalse(result);
    }

    @Test
    void test_isStorageIsFull_if_never_set_result_false() {
        boolean result = cargoStorageWatcher.isStorageIsFull();
        assertFalse(result);
    }

}