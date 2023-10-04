package serverController;

import eventSystemInfrastructure.Listener;
import events.NetEvent;
import server.Server;

public class ServerListener implements Listener<NetEvent> {

    private final Server server;

    public ServerListener(Server server) {
        this.server = server;
    }

    @Override
    public void onEvent(NetEvent event) {
        server.send(event);
    }
}
