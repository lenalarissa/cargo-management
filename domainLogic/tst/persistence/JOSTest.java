package persistence;

import cargoManagement.CargoStorage;
import domainLogicManagement.ManagementFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JOSTest {

    private ManagementFacade managementFacade;
    private JOS jos;

    @BeforeEach
    void setUp() {
        managementFacade = mock(ManagementFacade.class);
        jos = new JOS(managementFacade);
    }

    @Test
    void serialize_with_cargo_storage() {
        ObjectOutput oos = mock(ObjectOutput.class);
        CargoStorage cargoStorage = managementFacade.getCargoStorage();
        try {
            jos.serialize(oos, cargoStorage);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        try {
            verify(oos).writeObject(any());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deserialize() {
        ObjectInput objectInput = mock(ObjectInput.class);
        try {
            CargoStorage cargoStorage = jos.deserialize(objectInput);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        try {
            verify(objectInput).readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}