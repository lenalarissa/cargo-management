package domainLogicManagement;

import cargoManagement.CargoStorage;
import customerManagement.CustomerManagement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagementFacadeTest {

    Number monitor = new Integer(0);

    @Test
    void getCargoStorage_test_return() {
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        CargoStorage cargoStorage = new CargoStorage(5, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);
        assertEquals(cargoStorage, managementFacade.getCargoStorage());
    }

    @Test
    void setCargoStorage_test_return_of_getCargoStorage() {
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        CargoStorage cargoStorage = new CargoStorage(5, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);
        CargoStorage newCargoStorage = new CargoStorage(10, customerManagement, monitor);
        managementFacade.setCargoStorage(newCargoStorage);
        assertEquals(newCargoStorage, managementFacade.getCargoStorage());
    }

    @Test
    void getCustomerManagement_test_return() {
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        CargoStorage cargoStorage = new CargoStorage(5, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);
        assertEquals(customerManagement, managementFacade.getCustomerManagement());
    }

    @Test
    void setCustomerManagement_test_return_of_getCargoStorage() {
        CustomerManagement customerManagement = new CustomerManagement(monitor);
        CargoStorage cargoStorage = new CargoStorage(5, customerManagement, monitor);
        ManagementFacade managementFacade = new ManagementFacade(cargoStorage, customerManagement);
        CustomerManagement newCustomerManagement = new CustomerManagement(monitor);
        managementFacade.setCustomerManagement(newCustomerManagement);
        assertEquals(newCustomerManagement, managementFacade.getCustomerManagement());
    }
}