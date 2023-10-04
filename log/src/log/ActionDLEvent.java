package log;

import java.util.EventObject;

public class ActionDLEvent extends EventObject {

    private final ActionDL actionDL;

    public ActionDLEvent(Object source, ActionDL actionDL) {
        super(source);
        this.actionDL = actionDL;
    }

    public ActionDL getActionDL() {
        return actionDL;
    }
}
