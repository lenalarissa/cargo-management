package clientController;

import client.Client;
import eventSystemInfrastructure.Listener;
import events.NetEvent;

public class ClientListener implements Listener<NetEvent> {

    private final Client client;

    public ClientListener(Client client) {
        this.client = client;
    }

    @Override
    public void onEvent(NetEvent event) {
        client.send(event);
    }
}
