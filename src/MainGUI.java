import cargoManagement.CargoStorage;
import cargoManagement.CargoStorageDecoder;
import customerManagement.CustomerManagementDecoder;
import customerManagement.CustomerManagement;
import domainLogicController.*;
import eventSystemInfrastructure.Handler;
import events.*;
import gui.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.JBP;
import log.ActionDLEvent;
import log.LogEntries;
import log.LogWriter;
import logWriterController.WriteLogListener;
import domainLogicManagement.ManagementFacade;
import persistence.JOS;
import cargoManagement.CargoStorageEncoder;
import persistenceController.LoadJBPListener;
import persistenceController.LoadJOSListener;
import persistenceController.SaveJBPListener;
import persistenceController.SaveJOSListener;
import userInterfaceController.ReturnCargosListener;
import userInterfaceController.ReturnCustomersListener;
import userInterfaceController.ReturnHazardsListener;

import java.util.EventObject;
import java.util.List;

public class MainGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> args = getParameters().getRaw();
        int capacity = Integer.parseInt(args.get(0));
        Number monitor = new Integer(0);
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        CargoStorage cargoStorage = new CargoStorage(capacity, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);

        CargoStorageEncoder cargoStorageEncoder = new CargoStorageEncoder();
        CargoStorageDecoder cargoStorageDecoder = new CargoStorageDecoder(new CustomerManagementDecoder());
        JOS jos = new JOS(managementFacade);
        JBP jbp = new JBP(managementFacade, cargoStorageEncoder, cargoStorageDecoder);
        GUIController guiController = new GUIController();

        // Event-System
        Handler<CustomerEvent> createCustomerHandler = new Handler<>();
        CreateCustomerListener createCustomerListener = new CreateCustomerListener(managementFacade);
        createCustomerHandler.add(createCustomerListener);
        guiController.setCreateCustomerHandler(createCustomerHandler);

        Handler<CargoEvent> createCargoHandler = new Handler<>();
        CreateCargoListener createCargoListener = new CreateCargoListener(managementFacade);
        createCargoHandler.add(createCargoListener);
        guiController.setCreateCargoHandler(createCargoHandler);

        Handler<EventObject> readCustomersHandler = new Handler<>();
        ReadCustomersListener readCustomersListener = new ReadCustomersListener(managementFacade);
        readCustomersHandler.add(readCustomersListener);
        guiController.setReadCustomersHandler(readCustomersHandler);
        Handler<CustomerListEvent> returnCustomersHandler = new Handler<>();
        ReturnCustomersListener returnCustomersListener = new ReturnCustomersListener(guiController);
        returnCustomersHandler.add(returnCustomersListener);
        readCustomersListener.setReturnCustomersHandler(returnCustomersHandler);

        Handler<CargoClassEvent> readCargosHandler = new Handler<>();
        ReadCargosListener readCargosListener = new ReadCargosListener(managementFacade);
        readCargosHandler.add(readCargosListener);
        guiController.setReadCargosHandler(readCargosHandler);
        Handler<CargoListEvent> returnCargosHandler = new Handler<>();
        ReturnCargosListener returnCargosListener = new ReturnCargosListener(guiController);
        returnCargosHandler.add(returnCargosListener);
        readCargosListener.setReturnCargosHandler(returnCargosHandler);

        Handler<ContainedHazardsEvent> readHazardsHandler = new Handler<>();
        ReadHazardsListener readHazardsListener = new ReadHazardsListener(managementFacade);
        readHazardsHandler.add(readHazardsListener);
        guiController.setReadHazardsHandler(readHazardsHandler);
        Handler<HazardListEvent> returnHazardsHandler = new Handler<>();
        ReturnHazardsListener returnHazardsListener = new ReturnHazardsListener(guiController);
        returnHazardsHandler.add(returnHazardsListener);
        readHazardsListener.setReturnHazardsHandler(returnHazardsHandler);

        Handler<StorageLocationEvent> updateCargoHandler = new Handler<>();
        UpdateCargoListener updateCargoListener = new UpdateCargoListener(managementFacade);
        updateCargoHandler.add(updateCargoListener);
        guiController.setUpdateCargoHandler(updateCargoHandler);

        Handler<StorageLocationEvent> deleteCargoHandler = new Handler<>();
        DeleteCargoListener deleteCargoListener = new DeleteCargoListener(managementFacade);
        deleteCargoHandler.add(deleteCargoListener);
        guiController.setDeleteCargoHandler(deleteCargoHandler);

        Handler<CustomerEvent> deleteCustomerHandler = new Handler<>();
        DeleteCustomerListener deleteCustomerListener = new DeleteCustomerListener(managementFacade);
        deleteCustomerHandler.add(deleteCustomerListener);
        guiController.setDeleteCustomerHandler(deleteCustomerHandler);

        Handler<EventObject> saveJOSHandler = new Handler<>();
        SaveJOSListener saveJOSListener = new SaveJOSListener(jos);
        saveJOSHandler.add(saveJOSListener);
        guiController.setSaveJOSHandler(saveJOSHandler);

        Handler<EventObject> loadJOSHandler = new Handler<>();
        LoadJOSListener loadJOSListener = new LoadJOSListener(jos);
        loadJOSHandler.add(loadJOSListener);
        guiController.setLoadJOSHandler(loadJOSHandler);

        Handler<EventObject> saveJBPHandler = new Handler<>();
        SaveJBPListener saveJBPListener = new SaveJBPListener(jbp);
        saveJBPHandler.add(saveJBPListener);
        guiController.setSaveJBPHandler(saveJBPHandler);

        Handler<EventObject> loadJBPHandler = new Handler<>();
        LoadJBPListener loadJBPListener = new LoadJBPListener(jbp);
        loadJBPHandler.add(loadJBPListener);
        guiController.setLoadJBPHandler(loadJBPHandler);

        Handler<StorageLocationsEvent> swapStorageLocationsHandler = new Handler<>();
        SwapStorageLocationsListener swapStorageLocationsListener = new SwapStorageLocationsListener(managementFacade);
        swapStorageLocationsHandler.add(swapStorageLocationsListener);
        guiController.setSwapStorageLocationsHandler(swapStorageLocationsHandler);

        boolean log = false;
        String language = null;
        try {
            language = args.get(1);
            log = true;
        } catch (IndexOutOfBoundsException ignored) {
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
                swapStorageLocationsListener.setWriteLogHandler(writeLogHandler);
            }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/gui.fxml"));
        loader.setController(guiController);
        Parent root = loader.load();
        primaryStage.setTitle("cargo storage maintenance");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
