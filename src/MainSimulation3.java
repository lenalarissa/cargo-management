import cargoManagement.CargoStorage;
import customerManagement.CustomerManagement;
import domainLogicController.CreateCargoListener;
import domainLogicController.DeleteCargoListener;
import domainLogicController.ReadCargosListener;
import domainLogicController.UpdateCargoListener;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import events.CargoEvent;
import events.CargoListEvent;
import events.StorageLocationEvent;
import domainLogicManagement.ManagementFacade;
import simulations.*;
import userInterfaceController.ReturnCargosListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainSimulation3 {
    public static void main(String[] args) {

        int capacity = Integer.parseInt(args[0]);
        int threads = Integer.parseInt(args[1]);
        int interval = Integer.parseInt(args[2]);

        Number monitor = new Integer(0);
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        String[] names = {"Abel", "Berbel", "Claudia", "Doris", "Eva", "Fabian", "Gustav", "Hans", "Inga", "Justus", "Kai", "Lena", "Maria", "Nina", "Olaf", "Pia", "Rabea", "Sebastian", "Tabea", "Ulf", "Viktor", "Wolfgang", "Xavier", "Yael", "Zac"};
        for (String name : names) {
            customerManagement.createCustomer(name);
        }
        CargoStorage cargoStorage = new CargoStorage(capacity, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CargoStorageWatcher cargoStorageWatcher = new CargoStorageWatcher();
        CreateCargos createCargos = new CreateCargos(monitor, true, capacity, cargoStorageWatcher);
        DeleteCargos deleteCargos = new DeleteCargos(monitor, true, cargoStorageWatcher);
        UpdateCargos updateCargos = new UpdateCargos(monitor);
        WatchCargoStorage watchCargoStorage = new WatchCargoStorage(monitor);

        Handler<CargoEvent> createCargoHandler = new Handler<>();
        CreateCargoListener createCargoListener = new CreateCargoListener(managementFacade);
        createCargoHandler.add(createCargoListener);
        createCargos.setCreateCargoHandler(createCargoHandler);

        Handler<CargoClassEvent> readCargosHandler = new Handler<>();
        ReadCargosListener readCargosListener = new ReadCargosListener(managementFacade);
        readCargosHandler.add(readCargosListener);
        deleteCargos.setReadCargosHandler(readCargosHandler);
        Handler<CargoListEvent> returnCargosHandler = new Handler<>();
        ReturnCargosListener returnCargosListener = new ReturnCargosListener(deleteCargos);
        returnCargosHandler.add(returnCargosListener);
        readCargosListener.setReturnCargosHandler(returnCargosHandler);

        Handler<CargoClassEvent> readCargosHandler2 = new Handler<>();
        ReadCargosListener readCargosListener2 = new ReadCargosListener(managementFacade);
        readCargosHandler2.add(readCargosListener2);
        createCargos.setReadCargosHandler(readCargosHandler2);
        Handler<CargoListEvent> returnCargosHandler2 = new Handler<>();
        ReturnCargosListener returnCargosListener2 = new ReturnCargosListener(createCargos);
        returnCargosHandler2.add(returnCargosListener2);
        readCargosListener2.setReturnCargosHandler(returnCargosHandler2);

        Handler<CargoClassEvent> readCargosHandler3 = new Handler<>();
        ReadCargosListener readCargosListener3 = new ReadCargosListener(managementFacade);
        readCargosHandler3.add(readCargosListener3);
        updateCargos.setReadCargosHandler(readCargosHandler3);
        Handler<CargoListEvent> returnCargosHandler3 = new Handler<>();
        ReturnCargosListener returnCargosListener3 = new ReturnCargosListener(updateCargos);
        returnCargosHandler3.add(returnCargosListener3);
        readCargosListener3.setReturnCargosHandler(returnCargosHandler3);

        Handler<CargoClassEvent> readCargosHandler4 = new Handler<>();
        ReadCargosListener readCargosListener4 = new ReadCargosListener(managementFacade);
        readCargosHandler4.add(readCargosListener4);
        watchCargoStorage.setReadCargosHandler(readCargosHandler4);
        Handler<CargoListEvent> returnCargosHandler4 = new Handler<>();
        ReturnCargosListener returnCargosListener4 = new ReturnCargosListener(watchCargoStorage);
        returnCargosHandler4.add(returnCargosListener4);
        readCargosListener4.setReturnCargosHandler(returnCargosHandler4);

        Handler<StorageLocationEvent> deleteCargoHandler = new Handler<>();
        DeleteCargoListener deleteCargoListener = new DeleteCargoListener(managementFacade);
        deleteCargoHandler.add(deleteCargoListener);
        deleteCargos.setDeleteCargoHandler(deleteCargoHandler);

        Handler<StorageLocationEvent> updateCargoHandler = new Handler<>();
        UpdateCargoListener updateCargoListener = new UpdateCargoListener(managementFacade);
        updateCargoHandler.add(updateCargoListener);
        updateCargos.setUpdateCargoHandler(updateCargoHandler);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(watchCargoStorage, 0, interval, TimeUnit.MILLISECONDS);
        for (int i = 0; i < threads; i++) {
            Thread createCargoThread = new Thread(createCargos);
            Thread deleteCargoThread = new Thread(deleteCargos);
            Thread updateCargoThread = new Thread(updateCargos);
            updateCargoThread.start();
            createCargoThread.start();
            deleteCargoThread.start();
        }
    }
}
