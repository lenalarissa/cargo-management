package client;

import eventSystemInfrastructure.Handler;
import events.CargoListEvent;
import events.CustomerListEvent;
import events.HazardListEvent;
import events.NetEvent;

import java.io.*;
import java.net.*;

public class UDPClient implements Client {

    private DatagramSocket socket;

    // Handler
    private Handler<CargoListEvent> returnCargosHandler;
    private Handler<CustomerListEvent> returnCustomersHandler;
    private Handler<HazardListEvent> returnHazardsHandler;

    @Override
    public void setReturnCargosHandler(Handler<CargoListEvent> returnCargosHandler) {
        this.returnCargosHandler = returnCargosHandler;
    }

    @Override
    public void setReturnCustomersHandler(Handler<CustomerListEvent> returnCustomersHandler) {
        this.returnCustomersHandler = returnCustomersHandler;
    }

    @Override
    public void setReturnHazardsHandler(Handler<HazardListEvent> returnHazardsHandler) {
        this.returnHazardsHandler = returnHazardsHandler;
    }

    @Override
    public void start() {
        try {
            this.socket = new DatagramSocket(5001);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(NetEvent event) {
        try {
            byte[] serializedEvent = serializeEvent(event);
            DatagramPacket packetOut = new DatagramPacket(serializedEvent, serializedEvent.length, InetAddress.getLocalHost(), 5000);
            socket.send(packetOut);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive() {
        try {
            byte[] buffer = new byte[100000];
            DatagramPacket packetIn = new DatagramPacket(buffer, buffer.length);
            socket.receive(packetIn);

            NetEvent netEvent = deserializeEvent(packetIn.getData());
            findEvent(netEvent);
        } catch (IOException | ClassNotFoundException e) {
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
        if (netEvent.getEventObject() instanceof CustomerListEvent) {
            CustomerListEvent customerListEvent = (CustomerListEvent) netEvent.getEventObject();
            if (returnCustomersHandler != null) {
                returnCustomersHandler.handle(customerListEvent);
            }
        } else if (netEvent.getEventObject() instanceof CargoListEvent) {
            CargoListEvent cargoListEvent = (CargoListEvent) netEvent.getEventObject();
            if (returnCargosHandler != null) {
                returnCargosHandler.handle(cargoListEvent);
            }
        } else if (netEvent.getEventObject() instanceof HazardListEvent) {
            HazardListEvent hazardListEvent = (HazardListEvent) netEvent.getEventObject();
            if (returnHazardsHandler != null) {
                returnHazardsHandler.handle(hazardListEvent);
            }
        }
    }
}
