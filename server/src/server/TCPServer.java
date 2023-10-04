package server;

import eventSystemInfrastructure.Handler;
import events.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventObject;
import java.util.HashMap;

public class TCPServer implements Server {

    private ServerSocket serverSocket;
    private final HashMap<Long, ObjectOutputStream> threads = new HashMap<>();

    // Handler
    private Handler<CustomerEvent> createCustomerHandler, deleteCustomerHandler;
    private Handler<CargoEvent> createCargoHandler;
    private Handler<EventObject> readCustomersHandler, saveJOSHandler, loadJOSHandler, saveJBPHandler, loadJBPHandler;
    private Handler<CargoClassEvent> readCargosHandler;
    private Handler<ContainedHazardsEvent> readHazardsHandler;
    private Handler<StorageLocationEvent> updateCargoHandler, deleteCargoHandler;

    public void setCreateCustomerHandler(Handler<CustomerEvent> createCustomerHandler) {
        this.createCustomerHandler = createCustomerHandler;
    }

    public void setDeleteCustomerHandler(Handler<CustomerEvent> deleteCustomerHandler) {
        this.deleteCustomerHandler = deleteCustomerHandler;
    }

    public void setCreateCargoHandler(Handler<CargoEvent> createCargoHandler) {
        this.createCargoHandler = createCargoHandler;
    }

    public void setReadCustomersHandler(Handler<EventObject> readCustomersHandler) {
        this.readCustomersHandler = readCustomersHandler;
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


    public void start() {
        try {
            serverSocket = new ServerSocket(7070);
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                ServerThread serverThread = new ServerThread(out, in, this);
                Thread thread = new Thread(serverThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addThread(Long id, ObjectOutputStream out) {
        threads.put(id, out);
    }

    public synchronized void send(NetEvent netEvent) {
        ObjectOutputStream out = threads.get(Thread.currentThread().getId());
        try {
            if (serverSocket == null) {
                throw new IllegalStateException();
            } else {
                out.reset();
                out.writeObject(netEvent);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void findEvent(NetEvent netEvent) {
        if (netEvent.getEventObject() instanceof CustomerEvent) {
            CustomerEvent customerEvent = (CustomerEvent) netEvent.getEventObject();
            if (netEvent.getControlChar() == 'c') {
                if (createCustomerHandler != null) {
                    createCustomerHandler.handle(customerEvent);
                }
            }
            if (netEvent.getControlChar() == 'd') {
                if (deleteCustomerHandler != null) {
                    deleteCustomerHandler.handle(customerEvent);
                }
            }
        } else if (netEvent.getEventObject() instanceof CargoEvent) {
            CargoEvent cargoEvent = (CargoEvent) netEvent.getEventObject();
            if (createCargoHandler != null) {
                createCargoHandler.handle(cargoEvent);
            }
        } else if (netEvent.getEventObject() instanceof CargoClassEvent) {
            CargoClassEvent cargoClassEvent = (CargoClassEvent) netEvent.getEventObject();
            if (readCargosHandler != null) {
                readCargosHandler.handle(cargoClassEvent);
            }
        } else if (netEvent.getEventObject() instanceof ContainedHazardsEvent) {
            ContainedHazardsEvent containedHazardsEvent = (ContainedHazardsEvent) netEvent.getEventObject();
            if (readHazardsHandler != null) {
                readHazardsHandler.handle(containedHazardsEvent);
            }
        } else if (netEvent.getEventObject() instanceof StorageLocationEvent) {
            StorageLocationEvent storageLocationEvent = (StorageLocationEvent) netEvent.getEventObject();
            if (netEvent.getControlChar() == 'u') {
                if (updateCargoHandler != null) {
                    updateCargoHandler.handle(storageLocationEvent);
                }
            } else if (netEvent.getControlChar() == 'd') {
                if (deleteCargoHandler != null) {
                    deleteCargoHandler.handle(storageLocationEvent);
                }
            }
        } else {
            if (netEvent.getControlChar() == 'r') {
                if (readCustomersHandler != null) {
                    readCustomersHandler.handle(netEvent.getEventObject());
                }
            } else if (netEvent.getControlChar() == 'a') {
                if (saveJOSHandler != null) {
                    saveJOSHandler.handle(netEvent.getEventObject());
                }
            } else if (netEvent.getControlChar() == 'b') {
                if (loadJOSHandler != null) {
                    loadJOSHandler.handle(netEvent.getEventObject());
                }
            } else if (netEvent.getControlChar() == 'c') {
                if (saveJBPHandler != null) {
                    saveJBPHandler.handle(netEvent.getEventObject());
                }
            } else if (netEvent.getControlChar() == 'd') {
                if (loadJBPHandler != null) {
                    loadJBPHandler.handle(netEvent.getEventObject());
                }
            }
        }
    }
}


