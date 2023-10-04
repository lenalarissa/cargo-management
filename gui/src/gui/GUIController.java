package gui;

import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.UserInterface;
import events.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.text.Text;

import java.time.Duration;
import java.util.*;

public class GUIController implements UserInterface {

    private final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    private enum OperatorCLI {CREATECARGO, CREATECUSTOMER, UPDATECARGO, DELETECARGO, DELETECUSTOMER}

    private OperatorCLI operatorCLI;
    private HashMap<Integer, Cargo> cargos;
    private HashSet<Customer> customers;
    private HashSet<Hazard> hazards;
    private final ListProperty<Customer> customersListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<Cargo> cargosListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());

    // Handler
    private Handler<CustomerEvent> createCustomerHandler, deleteCustomerHandler;
    private Handler<CargoEvent> createCargoHandler;
    private Handler<EventObject> readCustomersHandler, saveJOSHandler, loadJOSHandler, saveJBPHandler, loadJBPHandler;
    private Handler<CargoClassEvent> readCargosHandler;
    private Handler<ContainedHazardsEvent> readHazardsHandler;
    private Handler<StorageLocationEvent> updateCargoHandler, deleteCargoHandler;
    private Handler<StorageLocationsEvent> swapStorageLocationsHandler;

    public void setCargos(HashMap<Integer, Cargo> cargos) {
        this.cargos = cargos;
    }

    public void setCustomers(HashSet<Customer> customers) {
        this.customers = customers;
    }

    public void setHazards(HashSet<Hazard> hazards) {
        this.hazards = hazards;
    }

    // Setter for handler
    public void setCreateCustomerHandler(Handler<CustomerEvent> createCustomerHandler) {
        this.createCustomerHandler = createCustomerHandler;
    }

    public void setCreateCargoHandler(Handler<CargoEvent> createCargoHandler) {
        this.createCargoHandler = createCargoHandler;
    }

    public void setReadCustomersHandler(Handler<EventObject> readCustomersHandler) {
        this.readCustomersHandler = readCustomersHandler;
    }

    public void setReadCargosHandler(Handler<CargoClassEvent> readCargosHandler) {
        this.readCargosHandler = readCargosHandler;
    }

    public void setReadHazardsHandler(Handler<ContainedHazardsEvent> readHazardsHandler) {
        this.readHazardsHandler = readHazardsHandler;
    }

    public void setUpdateCargoHandler(Handler<StorageLocationEvent> updateCargoHandler) {
        this.updateCargoHandler = updateCargoHandler;
    }

    public void setDeleteCargoHandler(Handler<StorageLocationEvent> deleteCargoHandler) {
        this.deleteCargoHandler = deleteCargoHandler;
    }

    public void setDeleteCustomerHandler(Handler<CustomerEvent> deleteCustomerHandler) {
        this.deleteCustomerHandler = deleteCustomerHandler;
    }

    public void setSaveJOSHandler(Handler<EventObject> saveJOSHandler) {
        this.saveJOSHandler = saveJOSHandler;
    }

    public void setLoadJOSHandler(Handler<EventObject> loadJOSHandler) {
        this.loadJOSHandler = loadJOSHandler;
    }

    public void setSaveJBPHandler(Handler<EventObject> saveJBPHandler) {
        this.saveJBPHandler = saveJBPHandler;
    }

    public void setLoadJBPHandler(Handler<EventObject> loadJBPHandler) {
        this.loadJBPHandler = loadJBPHandler;
    }

    public void setSwapStorageLocationsHandler(Handler<StorageLocationsEvent> swapStorageLocationsHandler) {
        this.swapStorageLocationsHandler = swapStorageLocationsHandler;
    }

    @FXML
    private TableView<Cargo> cargoTable;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Cargo, Duration> durationOfStorage;

    @FXML
    private Text hazardsContainedText;

    @FXML
    private Text hazardsNotContainedText;

    @FXML
    private TextField input;

    @FXML
    private TableColumn<Cargo, String> customer;

    @FXML
    private TableColumn<Cargo, Date> inspectionDate;

    @FXML
    private TableColumn<Customer, Integer> numberOfCargos;

    @FXML
    private Button saveBtn;

    @FXML
    private TableColumn<Cargo, Integer> storageLocation;

    @FXML
    private Text text;

    @FXML
    void readAllCargos(ActionEvent event) {
        filterCargos("all");
    }

    @FXML
    void readDryBulkAndUnitisedCargo(ActionEvent event) {
        filterCargos("DryBulkAndUnitisedCargo");
    }

    @FXML
    void readDryBulkCargo(ActionEvent event) {
        filterCargos("DryBulkCargo");
    }

    @FXML
    void readLiquidAndDryBulkCargo(ActionEvent event) {
        filterCargos("LiquidAndDryBulkCargo");
    }

    @FXML
    void readLiquidBulkAndUnitisedCargo(ActionEvent event) {
        filterCargos("LiquidBulkAndUnitisedCargo");
    }

    @FXML
    void readLiquidBulkCargo(ActionEvent event) {
        filterCargos("LiquidBulkCargo");
    }

    @FXML
    void readUnitisedCargo(ActionEvent event) {
        filterCargos("UnitisedCargo");
    }

    @FXML
    void changeToCreateCargo(ActionEvent event) {
        saveBtn.setText("save");
        text.setText("create cargo:");
        operatorCLI = OperatorCLI.CREATECARGO;
    }

    @FXML
    void changeToDeleteCargo(ActionEvent event) {
        saveBtn.setText("save");
        text.setText("delete cargo:");
        operatorCLI = OperatorCLI.DELETECARGO;
    }

    @FXML
    void changeToDeleteCustomer(ActionEvent event) {
        saveBtn.setText("save");
        text.setText("delete customer:");
        operatorCLI = OperatorCLI.DELETECUSTOMER;
    }

    @FXML
    void changeToICreateCustomer(ActionEvent event) {
        saveBtn.setText("save");
        text.setText("create customer:");
        operatorCLI = OperatorCLI.CREATECUSTOMER;
    }

    @FXML
    void changeToUpdateCargo(ActionEvent event) {
        saveBtn.setText("save");
        text.setText("update cargo:");
        operatorCLI = OperatorCLI.UPDATECARGO;
    }

    @FXML
    void loadJBP(ActionEvent event) {
        if (loadJBPHandler != null) {
            loadJBPHandler.handle(new EventObject(this));
            update();
        }
    }

    @FXML
    void loadJOS(ActionEvent event) {
        if (loadJOSHandler != null) {
            loadJOSHandler.handle(new EventObject(this));
            update();
        }
    }

    @FXML
    void saveJBP(ActionEvent event) {
        if (saveJBPHandler != null) {
            saveJBPHandler.handle(new EventObject(this));
        }
    }

    @FXML
    void saveJOS(ActionEvent event) {
        if (saveJOSHandler != null) {
            saveJOSHandler.handle(new EventObject(this));
        }
    }

    @FXML
    void save(ActionEvent event) {
        switch (operatorCLI) {
            case CREATECARGO:
                createCargo();
                break;
            case DELETECARGO:
                deleteCargo();
                break;
            case UPDATECARGO:
                updateCargo();
                break;
            case CREATECUSTOMER:
                createCustomer();
                break;
            case DELETECUSTOMER:
                deleteCustomer();
                break;
            default:
                break;
        }
    }


    private void update() {
        EventObject event = new EventObject(this);
        if (readCustomersHandler != null) {
            readCustomersHandler.handle(event);
            customersListProperty.set(FXCollections.observableArrayList(customers));
        }
        CargoClassEvent cargoClassEvent = new CargoClassEvent(this, "all");
        if (readCargosHandler != null) {
            readCargosHandler.handle(cargoClassEvent);
            cargosListProperty.set(FXCollections.observableArrayList(cargos.values()));
        }
        ContainedHazardsEvent containedHazardsEvent = new ContainedHazardsEvent(this, true);
        if (readHazardsHandler != null) {
            readHazardsHandler.handle(containedHazardsEvent);
            hazardsContainedText.setText("hazards contained: " + hazards);
        }
        containedHazardsEvent = new ContainedHazardsEvent(this, false);
        if (readHazardsHandler != null) {
            readHazardsHandler.handle(containedHazardsEvent);
            hazardsNotContainedText.setText("hazards not contained: " + hazards);
        }
    }

    private void filterCargos(String cargoClass) {
        CargoClassEvent cargoClassEvent = new CargoClassEvent(this, cargoClass);
        if (readCargosHandler != null) {
            readCargosHandler.handle(cargoClassEvent);
            cargosListProperty.set(FXCollections.observableArrayList(cargos.values()));
        }
    }

    private void deleteCustomer() {
        CustomerEvent customerEvent = new CustomerEvent(this, input.getText());
        if (deleteCustomerHandler != null) {
            deleteCustomerHandler.handle(customerEvent);
            update();
        }
    }

    private void createCustomer() {
        try {
            String name = input.getText().split(" ")[0];
            if (name.matches(".*[^0-9].*")) {
                CustomerEvent customerEvent = new CustomerEvent(this, name);
                if (createCustomerHandler != null) {
                    createCustomerHandler.handle(customerEvent);
                    update();
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    private void updateCargo() {
        try {
            int storageLocation = Integer.parseInt(input.getText());
            StorageLocationEvent storageLocationEvent = new StorageLocationEvent(this, storageLocation);
            if (updateCargoHandler != null) {
                updateCargoHandler.handle(storageLocationEvent);
                update();
            }
        } catch (NumberFormatException ignored) {
        }
    }

    private void deleteCargo() {
        try {
            int storageLocation = Integer.parseInt(input.getText());
            StorageLocationEvent storageLocationEvent = new StorageLocationEvent(this, storageLocation);
            if (deleteCargoHandler != null) {
                deleteCargoHandler.handle(storageLocationEvent);
                update();
            }
        } catch (NumberFormatException ignored) {
        }
    }

    private void createCargo() {
        String[] properties = input.getText().split(" ");
        CreateCargoTask createCargoTask = new CreateCargoTask(properties, createCargoHandler);

        // Source for lambda expression and task-threads: "JavaFX Background Tasks | How to make your GUI smoother, faster and snappier" https://www.youtube.com/watch?v=pdRX6CLP0tM
        createCargoTask.valueProperty().addListener((observable, oldValue, newValue) -> update());
        Thread thread = new Thread(createCargoTask);
        thread.setDaemon(true);
        thread.start();

    }

    public void initialize() {
        customer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner().getName()));
        storageLocation.setCellValueFactory(cellData -> {
            Cargo cargo = cellData.getValue();
            int storageLocationValue = cargo.getStorageLocation();
            return new SimpleIntegerProperty(storageLocationValue).asObject();
        });
        inspectionDate.setCellValueFactory(cellData -> {
            Cargo cargo = cellData.getValue();
            Date lastInspectionDate = cargo.getLastInspectionDate();
            return new SimpleObjectProperty<>(lastInspectionDate);
        });
        durationOfStorage.setCellValueFactory(cellData -> {
            Cargo cargo = cellData.getValue();
            Duration durationOfStorage = cargo.getDurationOfStorage();
            return new SimpleObjectProperty<>(durationOfStorage);
        });
        cargoTable.setItems(cargosListProperty);

        customerName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        numberOfCargos.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue();
            int numOfCargos = customer.getNumOfCargos();
            return new SimpleIntegerProperty(numOfCargos).asObject();
        });
        customerTable.setItems(customersListProperty);

        update();

        // Source for drag and drop: https://stackoverflow.com/questions/28603224/sort-tableview-with-drag-and-drop-rows

        cargoTable.setRowFactory(tv -> {
            TableRow<Cargo> row = new TableRow<>();
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    int dropIndex = row.isEmpty() ? cargoTable.getItems().size() : row.getIndex();

                    Cargo draggedCargo = cargoTable.getItems().get(draggedIndex);
                    Cargo dropCargo = cargoTable.getItems().get(dropIndex);

                    StorageLocationsEvent storageLocationsEvent = new StorageLocationsEvent(this, draggedCargo.getStorageLocation(), dropCargo.getStorageLocation());
                    if (swapStorageLocationsHandler != null) {
                        swapStorageLocationsHandler.handle(storageLocationsEvent);
                        update();
                    }

                    event.setDropCompleted(true);
                    event.consume();
                }
            });
            return row;
        });
    }
}
