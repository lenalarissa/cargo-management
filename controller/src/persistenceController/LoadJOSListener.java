package persistenceController;

import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import events.NetEvent;
import persistence.JOS;

import java.util.EventObject;

public class LoadJOSListener implements Listener<EventObject> {

    private final JOS jos;
    private Handler<NetEvent> serverHandler;

    public LoadJOSListener(JOS jos) {
        this.jos = jos;
    }

    public void setServerHandler(Handler<NetEvent> serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        jos.deserialize();
        if (serverHandler != null) {
            NetEvent netEvent = new NetEvent(this, new EventObject(this), 'x');
            serverHandler.handle(netEvent);
        }
    }
}
