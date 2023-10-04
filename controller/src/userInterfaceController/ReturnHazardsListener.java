package userInterfaceController;

import eventSystemInfrastructure.Listener;
import eventSystemInfrastructure.UserInterface;
import events.HazardListEvent;

public class ReturnHazardsListener implements Listener<HazardListEvent> {

    private final UserInterface userInterface;

    public ReturnHazardsListener(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void onEvent(HazardListEvent event) {
        userInterface.setHazards(event.getHazards());
    }
}
