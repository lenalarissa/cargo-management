package events;

import administration.Customer;

import java.util.EventObject;
import java.util.HashSet;

public class CustomerListEvent extends EventObject {

    private final HashSet<Customer> customers;

    public CustomerListEvent(Object source, HashSet<Customer> customers) {
        super(source);
        this.customers = customers;
    }

    public HashSet<Customer> getCustomers() {
        return customers;
    }
}
