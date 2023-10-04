import cargoManagement.CargoStorage;
import cargoManagement.CargoStorageDecoder;
import customerManagement.CustomerManagementDecoder;
import customerManagement.CustomerManagement;
import domainLogicController.*;
import eventSystemInfrastructure.Handler;
import events.*;
import persistence.JBP;
import domainLogicManagement.ManagementFacade;
import persistence.JOS;
import cargoManagement.CargoStorageEncoder;
import persistenceController.LoadJBPListener;
import persistenceController.LoadJOSListener;
import persistenceController.SaveJBPListener;
import persistenceController.SaveJOSListener;
import server.Server;
import server.TCPServer;
import server.UDPServer;
import serverController.ServerListener;

import java.util.EventObject;

public class MainServer {
    public static void main(String[] args) {
        int capacity = Integer.parseInt(args[0]);
        Server server = null;
        if (args[1].equals("TCP") || args[1].equals("UDP")) {
            switch (args[1]) {
                case "TCP":
                    server = new TCPServer();
                    break;
                case "UDP":
                    server = new UDPServer();
                    break;
            }
        }
        Number monitor = new Integer(0);
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        CargoStorage cargoStorage = new CargoStorage(capacity, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CargoStorageEncoder cargoStorageEncoder = new CargoStorageEncoder();
        CargoStorageDecoder cargoStorageDecoder = new CargoStorageDecoder(new CustomerManagementDecoder());
        JOS jos = new JOS(managementFacade);
        JBP jbp = new JBP(managementFacade, cargoStorageEncoder, cargoStorageDecoder);

        // Event-System
        Handler<CustomerEvent> createCustomerHandler = new Handler<>();
        CreateCustomerListener createCustomerListener = new CreateCustomerListener(managementFacade);
        createCustomerHandler.add(createCustomerListener);
        server.setCreateCustomerHandler(createCustomerHandler);

        Handler<CargoEvent> createCargoHandler = new Handler<>();
        CreateCargoListener createCargoListener = new CreateCargoListener(managementFacade);
        createCargoHandler.add(createCargoListener);
        server.setCreateCargoHandler(createCargoHandler);

        Handler<EventObject> readCustomersHandler = new Handler<>();
        ReadCustomersListener readCustomersListener = new ReadCustomersListener(managementFacade);
        readCustomersHandler.add(readCustomersListener);
        server.setReadCustomersHandler(readCustomersHandler);

        Handler<CargoClassEvent> readCargosHandler = new Handler<>();
        ReadCargosListener readCargosListener = new ReadCargosListener(managementFacade);
        readCargosHandler.add(readCargosListener);
        server.setReadCargosHandler(readCargosHandler);

        Handler<ContainedHazardsEvent> readHazardsHandler = new Handler<>();
        ReadHazardsListener readHazardsListener = new ReadHazardsListener(managementFacade);
        readHazardsHandler.add(readHazardsListener);
        server.setReadHazardsHandler(readHazardsHandler);

        Handler<StorageLocationEvent> updateCargoHandler = new Handler<>();
        UpdateCargoListener updateCargoListener = new UpdateCargoListener(managementFacade);
        updateCargoHandler.add(updateCargoListener);
        server.setUpdateCargoHandler(updateCargoHandler);

        Handler<StorageLocationEvent> deleteCargoHandler = new Handler<>();
        DeleteCargoListener deleteCargoListener = new DeleteCargoListener(managementFacade);
        deleteCargoHandler.add(deleteCargoListener);
        server.setDeleteCargoHandler(deleteCargoHandler);

        Handler<CustomerEvent> deleteCustomerHandler = new Handler<>();
        DeleteCustomerListener deleteCustomerListener = new DeleteCustomerListener(managementFacade);
        deleteCustomerHandler.add(deleteCustomerListener);
        server.setDeleteCustomerHandler(deleteCustomerHandler);

        Handler<EventObject> saveJOSHandler = new Handler<>();
        SaveJOSListener saveJOSListener = new SaveJOSListener(jos);
        saveJOSHandler.add(saveJOSListener);
        server.setSaveJOSHandler(saveJOSHandler);

        Handler<EventObject> loadJOSHandler = new Handler<>();
        LoadJOSListener loadJOSListener = new LoadJOSListener(jos);
        loadJOSHandler.add(loadJOSListener);
        server.setLoadJOSHandler(loadJOSHandler);

        Handler<EventObject> saveJBPHandler = new Handler<>();
        SaveJBPListener saveJBPListener = new SaveJBPListener(jbp);
        saveJBPHandler.add(saveJBPListener);
        server.setSaveJBPHandler(saveJBPHandler);

        Handler<EventObject> loadJBPHandler = new Handler<>();
        LoadJBPListener loadJBPListener = new LoadJBPListener(jbp);
        loadJBPHandler.add(loadJBPListener);
        server.setLoadJBPHandler(loadJBPHandler);

        Handler<NetEvent> serverHandler = new Handler<>();
        ServerListener serverListener = new ServerListener(server);
        serverHandler.add(serverListener);

        createCargoListener.setServerHandler(serverHandler);
        createCustomerListener.setServerHandler(serverHandler);
        deleteCargoListener.setServerHandler(serverHandler);
        deleteCustomerListener.setServerHandler(serverHandler);
        updateCargoListener.setServerHandler(serverHandler);
        loadJBPListener.setServerHandler(serverHandler);
        loadJOSListener.setServerHandler(serverHandler);
        saveJBPListener.setServerHandler(serverHandler);
        saveJOSListener.setServerHandler(serverHandler);
        readCargosListener.setServerHandler(serverHandler);
        readCustomersListener.setServerHandler(serverHandler);
        readHazardsListener.setServerHandler(serverHandler);

        server.start();
    }
}
