package logWriterController;

import eventSystemInfrastructure.Listener;
import log.ActionDLEvent;
import log.LogWriter;

public class WriteLogListener implements Listener<ActionDLEvent> {
    private final LogWriter logWriter;

    public WriteLogListener(LogWriter logWriter) {
        this.logWriter = logWriter;
    }

    @Override
    public void onEvent(ActionDLEvent event) {
        logWriter.write(event.getActionDL());
    }
}
