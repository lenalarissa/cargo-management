import cargoManagement.CargoStorage;
import userInterfaceController.ReturnCargosListener;
import customerManagement.CustomerManagement;
import domainLogicController.CreateCargoListener;
import domainLogicController.DeleteCargoListener;
import domainLogicController.ReadCargosListener;
import eventSystemInfrastructure.Handler;
import events.CargoClassEvent;
import events.CargoEvent;
import events.CargoListEvent;
import events.StorageLocationEvent;
import domainLogicManagement.ManagementFacade;
import observer.CargosObserver;
import simulations.CreateCargos;
import simulations.DeleteCargos;

public class MainSimulation2 {
    public static void main(String[] args) {

        int capacity = Integer.parseInt(args[0]);
        int threads = Integer.parseInt(args[1]);

        Number monitor = new Integer(0);
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        String[] names = {"Abel", "Berbel", "Claudia", "Doris", "Eva", "Fabian", "Gustav", "Hans", "Inga", "Justus", "Kai", "Lena", "Maria", "Nina", "Olaf", "Pia", "Rabea", "Sebastian", "Tabea", "Ulf", "Viktor", "Wolfgang", "Xavier", "Yael", "Zac"};
        for (String name : names) {
            customerManagement.createCustomer(name);
        }
        CargoStorage cargoStorage = new CargoStorage(capacity, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CargosObserver cargosObserver = new CargosObserver(managementFacade);
        cargoStorage.register(cargosObserver);

        CreateCargos createCargos = new CreateCargos(monitor);
        DeleteCargos deleteCargos = new DeleteCargos(monitor);

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

        Handler<StorageLocationEvent> deleteCargoHandler = new Handler<>();
        DeleteCargoListener deleteCargoListener = new DeleteCargoListener(managementFacade);
        deleteCargoHandler.add(deleteCargoListener);
        deleteCargos.setDeleteCargoHandler(deleteCargoHandler);

        for (int i = 0; i < threads; i++) {
            Thread createCargoThread = new Thread(createCargos);
            Thread deleteCargoThread = new Thread(deleteCargos);
            createCargoThread.start();
            deleteCargoThread.start();
        }
    }
}
