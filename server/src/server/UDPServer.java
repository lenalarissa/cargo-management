package server;

import eventSystemInfrastructure.Handler;
import events.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.EventObject;

public class UDPServer implements Server {

    private DatagramSocket socket;

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

    @Override
    public void start() {
        try {
            socket = new DatagramSocket(5000);
            while (true) {
                byte[] receiveBuffer = new byte[100000];
                DatagramPacket packetIn = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(packetIn);

                NetEvent netEvent = deserializeEvent(packetIn.getData());
                findEvent(netEvent);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(NetEvent netEvent) {
        try {
            if (socket == null) {
                throw new IllegalStateException();
            } else {
                byte[] serializedEvent = serializeEvent(netEvent);
                DatagramPacket packetOut = new DatagramPacket(serializedEvent, serializedEvent.length, InetAddress.getLocalHost(), 5001);
                socket.send(packetOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Source for serializing and deserializing: https://stackoverflow.com/questions/4252294/sending-objects-across-network-using-udp-in-java
    private byte[] serializeEvent(NetEvent event) throws IOException {
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOut);
        out.writeObject(event);
        out.flush();
        return byteArrayOut.toByteArray();
    }

    private NetEvent deserializeEvent(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream in = new ObjectInputStream(bis);
        return (NetEvent) in.readObject();
    }

    private void findEvent(NetEvent netEvent) {
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
