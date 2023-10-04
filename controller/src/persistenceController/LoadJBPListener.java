package persistenceController;

import eventSystemInfrastructure.Handler;
import eventSystemInfrastructure.Listener;
import events.NetEvent;
import persistence.JBP;

import java.util.EventObject;

public class LoadJBPListener implements Listener<EventObject> {

    private final JBP jbp;
    private Handler<NetEvent> serverHandler;

    public LoadJBPListener(JBP jbp) {
        this.jbp = jbp;
    }

    public void setServerHandler(Handler<NetEvent> serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void onEvent(EventObject event) {
        jbp.decode();
        if (serverHandler != null) {
            NetEvent netEvent = new NetEvent(this, new EventObject(this), 'x');
            serverHandler.handle(netEvent);
        }
    }
}
