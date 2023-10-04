package client;

import eventSystemInfrastructure.Handler;
import events.CargoListEvent;
import events.CustomerListEvent;
import events.HazardListEvent;
import events.NetEvent;

public interface Client {
    void start();

    void send(NetEvent event);

    void setReturnCargosHandler(Handler<CargoListEvent> returnCargosHandler);

    void setReturnCustomersHandler(Handler<CustomerListEvent> returnCustomersHandler);

    void setReturnHazardsHandler(Handler<HazardListEvent> returnHazardsHandler);
}
