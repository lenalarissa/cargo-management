package eventSystemInfrastructure;

import administration.Customer;
import cargo.Hazard;

import java.util.HashSet;

public interface UserInterface extends CargosSettable {
    void setHazards(HashSet<Hazard> hazards);

    void setCustomers(HashSet<Customer> customers);
}
