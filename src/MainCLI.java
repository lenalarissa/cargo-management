import cargoManagement.CargoStorage;
import cargoManagement.CargoStorageDecoder;
import client.Client;
import client.TCPClient;
import client.UDPClient;
import clientController.ClientListener;
import customerManagement.CustomerManagementDecoder;
import persistence.JBP;
import log.ActionDLEvent;
import cargoManagement.CargoStorageEncoder;
import userInterfaceController.ReturnCargosListener;
import userInterfaceController.ReturnCustomersListener;
import userInterfaceController.ReturnHazardsListener;
import customerManagement.CustomerManagement;
import domainLogicController.*;
import eventSystemInfrastructure.Handler;
import events.*;
import log.LogEntries;
import log.LogWriter;
import logWriterController.WriteLogListener;
import domainLogicManagement.ManagementFacade;

import cli.Console;
import observer.*;
import persistenceController.LoadJBPListener;
import persistenceController.LoadJOSListener;
import persistenceController.SaveJBPListener;
import persistenceController.SaveJOSListener;
import persistence.JOS;

import java.util.EventObject;
import java.util.Scanner;

public class MainCLI {

    public static void main(String[] args) {
        Console console = new Console(new Scanner(System.in));
        boolean net = true;
        int capacity = 0;
        try {
            capacity = Integer.parseInt(args[0]);
            net = false;
        } catch (NumberFormatException ignored) {
        }
        if (!net) {
            Number monitor = new Integer(0);
            CustomerManagement customerManagement = new CustomerManagement(monitor);
            CargoStorage cargoStorage = new CargoStorage(capacity, customerManagement, monitor);
            ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

            CargoStorageEncoder cargoStorageEncoder = new CargoStorageEncoder();
            CargoStorageDecoder cargoStorageDecoder = new CargoStorageDecoder(new CustomerManagementDecoder());
            JOS jos = new JOS(managementFacade);
            JBP jbp = new JBP(managementFacade, cargoStorageEncoder, cargoStorageDecoder);

            // Observers
            Observer capacityObserver = new CapacityObserver(managementFacade);
            cargoStorage.register(capacityObserver);
            Observer hazardsObserver = new HazardsObserver(managementFacade);
            cargoStorage.register(hazardsObserver);

            // Event-System
            Handler<CustomerEvent> createCustomerHandler = new Handler<>();
            CreateCustomerListener createCustomerListener = new CreateCustomerListener(managementFacade);
            createCustomerHandler.add(createCustomerListener);
            console.setCreateCustomerHandler(createCustomerHandler);

            Handler<CargoEvent> createCargoHandler = new Handler<>();
            CreateCargoListener createCargoListener = new CreateCargoListener(managementFacade);
            createCargoHandler.add(createCargoListener);
            console.setCreateCargoHandler(createCargoHandler);

            Handler<EventObject> readCustomersHandler = new Handler<>();
            ReadCustomersListener readCustomersListener = new ReadCustomersListener(managementFacade);
            readCustomersHandler.add(readCustomersListener);
            console.setReadCustomersHandler(readCustomersHandler);
            Handler<CustomerListEvent> returnCustomersHandler = new Handler<>();
            ReturnCustomersListener returnCustomersListener = new ReturnCustomersListener(console);
            returnCustomersHandler.add(returnCustomersListener);
            readCustomersListener.setReturnCustomersHandler(returnCustomersHandler);

            Handler<CargoClassEvent> readCargosHandler = new Handler<>();
            ReadCargosListener readCargosListener = new ReadCargosListener(managementFacade);
            readCargosHandler.add(readCargosListener);
            console.setReadCargosHandler(readCargosHandler);
            Handler<CargoListEvent> returnCargosHandler = new Handler<>();
            ReturnCargosListener returnCargosListener = new ReturnCargosListener(console);
            returnCargosHandler.add(returnCargosListener);
            readCargosListener.setReturnCargosHandler(returnCargosHandler);

            Handler<ContainedHazardsEvent> readHazardsHandler = new Handler<>();
            ReadHazardsListener readHazardsListener = new ReadHazardsListener(managementFacade);
            readHazardsHandler.add(readHazardsListener);
            console.setReadHazardsHandler(readHazardsHandler);
            Handler<HazardListEvent> returnHazardsHandler = new Handler<>();
            ReturnHazardsListener returnHazardsListener = new ReturnHazardsListener(console);
            returnHazardsHandler.add(returnHazardsListener);
            readHazardsListener.setReturnHazardsHandler(returnHazardsHandler);

            Handler<StorageLocationEvent> updateCargoHandler = new Handler<>();
            UpdateCargoListener updateCargoListener = new UpdateCargoListener(managementFacade);
            updateCargoHandler.add(updateCargoListener);
            console.setUpdateCargoHandler(updateCargoHandler);

            Handler<StorageLocationEvent> deleteCargoHandler = new Handler<>();
            DeleteCargoListener deleteCargoListener = new DeleteCargoListener(managementFacade);
            deleteCargoHandler.add(deleteCargoListener);
            console.setDeleteCargoHandler(deleteCargoHandler);

            Handler<CustomerEvent> deleteCustomerHandler = new Handler<>();
            DeleteCustomerListener deleteCustomerListener = new DeleteCustomerListener(managementFacade);
            deleteCustomerHandler.add(deleteCustomerListener);
            console.setDeleteCustomerHandler(deleteCustomerHandler);

            Handler<EventObject> saveJOSHandler = new Handler<>();
            SaveJOSListener saveJOSListener = new SaveJOSListener(jos);
            saveJOSHandler.add(saveJOSListener);
            console.setSaveJOSHandler(saveJOSHandler);

            Handler<EventObject> loadJOSHandler = new Handler<>();
            LoadJOSListener loadJOSListener = new LoadJOSListener(jos);
            loadJOSHandler.add(loadJOSListener);
            console.setLoadJOSHandler(loadJOSHandler);

            Handler<EventObject> saveJBPHandler = new Handler<>();
            SaveJBPListener saveJBPListener = new SaveJBPListener(jbp);
            saveJBPHandler.add(saveJBPListener);
            console.setSaveJBPHandler(saveJBPHandler);

            Handler<EventObject> loadJBPHandler = new Handler<>();
            LoadJBPListener loadJBPListener = new LoadJBPListener(jbp);
            loadJBPHandler.add(loadJBPListener);
            console.setLoadJBPHandler(loadJBPHandler);

            boolean log = false;
            String language = null;
            try {
                language = args[1];
                log = true;
            } catch (IndexOutOfBoundsException ignore) {
            }
            if (log) {
                if (language.equals("EN") || language.equals("DE")) {
                    LogEntries logEntries = new LogEntries(language);
                    LogWriter logWriter = new LogWriter(logEntries);

                    Handler<ActionDLEvent> writeLogHandler = new Handler<>();
                    WriteLogListener writeLogListener = new WriteLogListener(logWriter);
                    writeLogHandler.add(writeLogListener);

                    createCargoListener.setWriteLogHandler(writeLogHandler);
                    createCustomerListener.setWriteLogHandler(writeLogHandler);
                    deleteCargoListener.setWriteLogHandler(writeLogHandler);
                    deleteCustomerListener.setWriteLogHandler(writeLogHandler);
                    readCargosListener.setWriteLogHandler(writeLogHandler);
                    readCustomersListener.setWriteLogHandler(writeLogHandler);
                    readHazardsListener.setWriteLogHandler(writeLogHandler);
                    updateCargoListener.setWriteLogHandler(writeLogHandler);
                }
            }
        } else {
            Client client = null;
            if (args[0].equals("TCP") || args[0].equals("UDP")) {
                switch (args[0]) {
                    case "TCP":
                        client = new TCPClient();
                        break;
                    case "UDP":
                        client = new UDPClient();
                        break;
                    default:
                        break;
                }
            }
            Handler<NetEvent> clientHandler = new Handler<>();
            ClientListener clientListener = new ClientListener(client);
            clientHandler.add(clientListener);
            console.setClientHandler(clientHandler);

            Handler<CustomerListEvent> returnCustomersHandler = new Handler<>();
            ReturnCustomersListener returnCustomersListener = new ReturnCustomersListener(console);
            returnCustomersHandler.add(returnCustomersListener);
            client.setReturnCustomersHandler(returnCustomersHandler);

            Handler<CargoListEvent> returnCargosHandler = new Handler<>();
            ReturnCargosListener returnCargosListener = new ReturnCargosListener(console);
            returnCargosHandler.add(returnCargosListener);
            client.setReturnCargosHandler(returnCargosHandler);

            Handler<HazardListEvent> returnHazardsHandler = new Handler<>();
            ReturnHazardsListener returnHazardsListener = new ReturnHazardsListener(console);
            returnHazardsHandler.add(returnHazardsListener);
            client.setReturnHazardsHandler(returnHazardsHandler);

            client.start();
        }
        console.execute();
    }
}
