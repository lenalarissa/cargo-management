package client;

import eventSystemInfrastructure.Handler;
import events.CargoListEvent;
import events.CustomerListEvent;
import events.HazardListEvent;
import events.NetEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class TCPClient implements Client {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

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
            socket = new Socket("localhost", 7070);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(NetEvent event) {
        try {
            if (socket == null) {
                throw new IllegalStateException();
            } else {
                out.reset();
                out.writeObject(event);
                out.flush();
                receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive() throws IOException {
        NetEvent netEvent = null;
        while (true) {
            try {
                netEvent = (NetEvent) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (netEvent != null) {
                findEvent(netEvent);
                break;
            }
        }
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
