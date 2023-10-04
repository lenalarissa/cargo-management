package userInterfaceController;

import eventSystemInfrastructure.Listener;
import eventSystemInfrastructure.UserInterface;
import events.CustomerListEvent;

public class ReturnCustomersListener implements Listener<CustomerListEvent> {
    private final UserInterface userInterface;

    public ReturnCustomersListener(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void onEvent(CustomerListEvent event) {
        userInterface.setCustomers(event.getCustomers());
    }
}
