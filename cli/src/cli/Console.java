package cli;

import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.UserInterface;
import events.*;

import java.math.BigDecimal;
import java.util.*;

public class Console implements UserInterface {

    private final Scanner scanner;
    private HashMap<Integer, Cargo> cargos;
    private HashSet<Customer> customers;
    private HashSet<Hazard> hazards;

    // Handler
    private Handler<CustomerEvent> createCustomerHandler, deleteCustomerHandler;
    private Handler<CargoEvent> createCargoHandler;
    private Handler<EventObject> readCustomersHandler, saveJOSHandler, loadJOSHandler, saveJBPHandler, loadJBPHandler;
    private Handler<CargoClassEvent> readCargosHandler;
    private Handler<ContainedHazardsEvent> readHazardsHandler;
    private Handler<StorageLocationEvent> updateCargoHandler, deleteCargoHandler;
    private Handler<NetEvent> clientHandler;

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

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

    public void setClientHandler(Handler<NetEvent> clientHandler) {
        this.clientHandler = clientHandler;
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

    public void execute() {
        try {
            boolean exit = false;
            Command command;
            String input = scanner.nextLine();
            command = new Command(input);
            do {
                switch (command.getOperator()) {
                    case CREATE:
                        do {
                            input = scanner.nextLine();
                            command = new Command(input);
                            if (command.getOperator() != Command.Operator.CREATE && command.getOperator() != Command.Operator.ERROR) {
                                break;
                            }
                            create(input);
                        } while (true);
                        break;
                    case READ:
                        do {
                            input = scanner.nextLine();
                            command = new Command(input);
                            if (command.getOperator() != Command.Operator.READ && command.getOperator() != Command.Operator.ERROR) {
                                break;
                            }
                            read(input);
                        } while (true);
                        break;
                    case UPDATE:
                        do {
                            input = scanner.nextLine();
                            command = new Command(input);
                            if (command.getOperator() != Command.Operator.UPDATE && command.getOperator() != Command.Operator.ERROR) {
                                break;
                            }
                            update(input);
                        } while (true);
                        break;
                    case DELETE:
                        do {
                            input = scanner.nextLine();
                            command = new Command(input);
                            if (command.getOperator() != Command.Operator.DELETE && command.getOperator() != Command.Operator.ERROR) {
                                break;
                            }
                            delete(input);
                        } while (true);
                        break;
                    case PERSISTENCE:
                        do {
                            input = scanner.nextLine();
                            command = new Command(input);
                            if (command.getOperator() != Command.Operator.PERSISTENCE && command.getOperator() != Command.Operator.ERROR) {
                                break;
                            }
                            persistence(input);
                        } while (true);
                        break;
                    case EXIT:
                        exit = true;
                        System.out.println("program terminated");
                        break;
                    case ERROR:
                        input = scanner.nextLine();
                        command = new Command(input);
                        break;
                    default:
                        throw new IllegalArgumentException("error");
                }
                if(exit){
                    break;
                }
            } while (true);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    boolean create(String input) {
        String[] properties = input.split(" ");
        if (properties.length == 1 && properties[0].matches(".*[^0-9].*") && !properties[0].matches(":c")) {
            CustomerEvent customerEvent = new CustomerEvent(this, properties[0]);
            if (createCustomerHandler != null) {
                createCustomerHandler.handle(customerEvent);
                return true;
            } else if (clientHandler != null) {
                NetEvent netEvent = new NetEvent(this, customerEvent, 'c');
                clientHandler.handle(netEvent);
            }
        } else {
            try {
                String cargoClass = properties[0];
                String customerName = properties[1];
                BigDecimal value = new BigDecimal(properties[2].replace(',', '.'));
                HashSet<Hazard> hazards = new HashSet<>();
                if (!properties[3].equals(",")) { // checks if there are hazards
                    String[] hazardArray = properties[3].split(",");
                    try {
                        for (String hazard : hazardArray) {
                            hazards.add(Hazard.valueOf(hazard));
                        }
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                }
                boolean fragile = false;
                boolean pressurized = false;
                int grainSize = 0;
                switch (cargoClass) {
                    case "DryBulkAndUnitisedCargo":
                        fragile = Boolean.parseBoolean(properties[4]);
                        grainSize = Integer.parseInt(properties[5]);
                        break;
                    case "DryBulkCargo":
                        grainSize = Integer.parseInt(properties[4]);
                        break;
                    case "LiquidAndDryBulkCargo":
                        pressurized = Boolean.parseBoolean(properties[4]);
                        grainSize = Integer.parseInt(properties[5]);
                        break;
                    case "LiquidBulkAndUnitisedCargo":
                        fragile = Boolean.parseBoolean(properties[4]);
                        pressurized = Boolean.parseBoolean(properties[5]);
                        break;
                    case "LiquidBulkCargo":
                        pressurized = Boolean.parseBoolean(properties[4]);
                        break;
                    case "UnitisedCargo":
                        fragile = Boolean.parseBoolean(properties[4]);
                        break;
                    default:
                        break;
                }
                CargoEvent cargoEvent = new CargoEvent(this, cargoClass, customerName, value, hazards, fragile, pressurized, grainSize);
                if (createCargoHandler != null) {
                    createCargoHandler.handle(cargoEvent);
                    return true;
                } else if (clientHandler != null) {
                    NetEvent netEvent = new NetEvent(this, cargoEvent, 'c');
                    clientHandler.handle(netEvent);
                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException ignored) {
            }
        }
        return false;
    }

    void read(String input) {
        String[] mode = input.split(" ");
        switch (mode[0]) {
            case "customers":
                if (readCustomersHandler != null) {
                    readCustomersHandler.handle(new EventObject(this));
                } else if (clientHandler != null) {
                    NetEvent netEvent = new NetEvent(this, new EventObject(this), 'r');
                    clientHandler.handle(netEvent);
                }
                if (customers != null) {
                    for (Customer customer : customers) {
                        System.out.println(customer.toString());
                    }
                }
                break;
            case "cargos":
                String cargoClass;
                try {
                    cargoClass = mode[1];
                } catch (IndexOutOfBoundsException e) {
                    cargoClass = "allCargos";
                }
                CargoClassEvent cargoClassEvent = new CargoClassEvent(this, cargoClass);
                if (readCargosHandler != null) {
                    readCargosHandler.handle(cargoClassEvent);
                } else if (clientHandler != null) {
                    NetEvent netEvent = new NetEvent(this, cargoClassEvent, 'r');
                    clientHandler.handle(netEvent);
                }
                if (cargos != null) {
                    for (Cargo cargo : cargos.values()) {
                        System.out.println(cargo.toString());
                    }
                }
                break;
            case "hazards":
                String hazardMode;
                try {
                    hazardMode = mode[1].substring(0, 1);
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
                ContainedHazardsEvent containedHazardsEvent;
                if (hazardMode.equals("i")) {
                    containedHazardsEvent = new ContainedHazardsEvent(this, true);
                } else if (hazardMode.equals("e")) {
                    containedHazardsEvent = new ContainedHazardsEvent(this, false);
                } else {
                    break;
                }
                if (readHazardsHandler != null) {
                    readHazardsHandler.handle(containedHazardsEvent);
                } else if (clientHandler != null) {
                    NetEvent netEvent = new NetEvent(this, containedHazardsEvent, 'r');
                    clientHandler.handle(netEvent);
                }
                if (hazards != null) {
                    System.out.println(hazards);
                }
                break;
            default:
                break;
        }
    }

    private void update(String input) {
        try {
            int storageLocation = Integer.parseInt(input);
            StorageLocationEvent storageLocationEvent = new StorageLocationEvent(this, storageLocation);
            if (updateCargoHandler != null) {
                updateCargoHandler.handle(storageLocationEvent);
            } else if (clientHandler != null) {
                NetEvent netEvent = new NetEvent(this, storageLocationEvent, 'u');
                clientHandler.handle(netEvent);
            }
        } catch (NumberFormatException ignored) {
        }
    }

    private void delete(String input) {
        try {
            int storageLocation = Integer.parseInt(input);
            StorageLocationEvent storageLocationEvent = new StorageLocationEvent(this, storageLocation);
            if (deleteCargoHandler != null) {
                deleteCargoHandler.handle(storageLocationEvent);
            } else if (clientHandler != null) {
                NetEvent netEvent = new NetEvent(this, storageLocationEvent, 'd');
                clientHandler.handle(netEvent);
            }
        } catch (NumberFormatException ignored) {
            CustomerEvent customerEvent = new CustomerEvent(this, input);
            if (deleteCustomerHandler != null) {
                deleteCustomerHandler.handle(customerEvent);
            } else if (clientHandler != null) {
                NetEvent netEvent = new NetEvent(this, customerEvent, 'd');
                clientHandler.handle(netEvent);
            }
        }
    }

    private void persistence(String input) {
        switch (input) {
            case "saveJOS":
                if (saveJOSHandler != null) {
                    saveJOSHandler.handle(new EventObject(this));
                } else if (clientHandler != null) {
                    NetEvent netEvent = new NetEvent(this, new EventObject(this), 'a');
                    clientHandler.handle(netEvent);
                }
                break;
            case "loadJOS":
                if (loadJOSHandler != null) {
                    loadJOSHandler.handle(new EventObject(this));
                } else if (clientHandler != null) {
                    NetEvent netEvent = new NetEvent(this, new EventObject(this), 'b');
                    clientHandler.handle(netEvent);
                }
                break;
            case "saveJBP":
                if (saveJBPHandler != null) {
                    saveJBPHandler.handle(new EventObject(this));
                } else if (clientHandler != null) {
                    NetEvent netEvent = new NetEvent(this, new EventObject(this), 'c');
                    clientHandler.handle(netEvent);
                }
                break;
            case "loadJBP":
                if (loadJBPHandler != null) {
                    loadJBPHandler.handle(new EventObject(this));
                } else if (clientHandler != null) {
                    NetEvent netEvent = new NetEvent(this, new EventObject(this), 'd');
                    clientHandler.handle(netEvent);
                }
                break;
            default:
                break;
        }
    }
}
