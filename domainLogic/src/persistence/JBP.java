package persistence;

import cargoManagement.CargoStorage;
import cargoManagement.CargoStorageDecoder;
import cargoManagement.CargoStorageEncoder;
import cargoManagement.CargoStoragePojo;
import domainLogicManagement.ManagementFacade;

import java.beans.*;
import java.io.*;

public class JBP {

    private final ManagementFacade managementFacade;
    private final CargoStorageEncoder cargoStorageEncoder;
    private final CargoStorageDecoder cargoStorageDecoder;

    public JBP(ManagementFacade managementFacade, CargoStorageEncoder cargoStorageEncoder, CargoStorageDecoder cargoStorageDecoder) {
        this.managementFacade = managementFacade;
        this.cargoStorageEncoder = cargoStorageEncoder;
        this.cargoStorageDecoder = cargoStorageDecoder;
    }

    public void encode() {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("domainLogic.xml")));) {
            encoder.writeObject(this.cargoStorageEncoder.encodeCargoStorage(managementFacade.getCargoStorage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decode() {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("domainLogic.xml")));) {
            CargoStorage cargoStorage = cargoStorageDecoder.decodeCargoStorage((CargoStoragePojo) decoder.readObject());
            managementFacade.setCargoStorage(cargoStorage);
            managementFacade.setCustomerManagement(cargoStorage.getCustomerManagement());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
