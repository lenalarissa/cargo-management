package domainLogicManagement;

import cargoManagement.CargoStorage;
import customerManagement.CustomerManagement;

import java.io.Serializable;


public class ManagementFacade implements Serializable {
    private CargoStorage cargoStorage;
    private CustomerManagement customerManagement;

    public ManagementFacade(CargoStorage cargoStorage, CustomerManagement customerManagement) {
        this.cargoStorage = cargoStorage;
        this.customerManagement = customerManagement;
    }

    public CargoStorage getCargoStorage() {
        return cargoStorage;
    }

    public void setCargoStorage(CargoStorage cargoStorage) {
        this.cargoStorage = cargoStorage;
    }

    public CustomerManagement getCustomerManagement() {
        return customerManagement;
    }

    public void setCustomerManagement(CustomerManagement customerManagement) {
        this.customerManagement = customerManagement;
    }
}
