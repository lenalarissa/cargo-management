package server;

import eventSystemInfrastructure.Handler;
import events.*;

import java.util.EventObject;

public interface Server {
    void start();

    void send(NetEvent netEvent);

    void setCreateCustomerHandler(Handler<CustomerEvent> createCustomerHandler);

    void setDeleteCustomerHandler(Handler<CustomerEvent> deleteCustomerHandler);

    void setCreateCargoHandler(Handler<CargoEvent> createCargoHandler);

    void setReadCustomersHandler(Handler<EventObject> readCustomersHandler);

    void setSaveJOSHandler(Handler<EventObject> saveJOSHandler);

    void setLoadJOSHandler(Handler<EventObject> loadJOSHandler);

    void setSaveJBPHandler(Handler<EventObject> saveJBPHandler);

    void setLoadJBPHandler(Handler<EventObject> loadJBPHandler);

    void setReadCargosHandler(Handler<CargoClassEvent> readCargosHandler);

    void setReadHazardsHandler(Handler<ContainedHazardsEvent> readHazardsHandler);

    void setUpdateCargoHandler(Handler<StorageLocationEvent> updateCargoHandler);

    void setDeleteCargoHandler(Handler<StorageLocationEvent> deleteCargoHandler);
}
