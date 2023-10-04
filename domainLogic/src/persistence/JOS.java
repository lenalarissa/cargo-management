package persistence;

import cargoManagement.CargoStorage;
import domainLogicManagement.ManagementFacade;

import java.io.*;

public class JOS {

    private final ManagementFacade managementFacade;

    public JOS(ManagementFacade managementFacade) {
        this.managementFacade = managementFacade;
    }

    // Save
    public void serialize() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("domainLogic.ser"))) {
            serialize(oos, managementFacade.getCargoStorage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize(ObjectOutput objectOutput, CargoStorage cargoStorage) throws IOException {
        objectOutput.writeObject(cargoStorage);
    }

    // Load
    public void deserialize() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("domainLogic.ser"))) {
            CargoStorage cargoStorage = deserialize(ois);
            managementFacade.setCargoStorage(cargoStorage);
            managementFacade.setCustomerManagement(cargoStorage.getCustomerManagement());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public CargoStorage deserialize(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return (CargoStorage) objectInput.readObject();
    }

}
