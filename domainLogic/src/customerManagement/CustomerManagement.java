package customerManagement;

import administration.Customer;

import java.io.Serializable;
import java.util.HashSet;

public class CustomerManagement implements Serializable {
    private final static long serialVersionUID = 1L;
    private final HashSet<Customer> customers;
    private final Number monitor;

    public CustomerManagement(Number monitor) {
        this.monitor = monitor;
        this.customers = new HashSet<>();
    }

    CustomerManagement(Number monitor, HashSet<Customer> customers) {
        this.monitor = monitor;
        this.customers = customers;
    }

    public HashSet<Customer> readCustomers() {
        HashSet<Customer> customersCopy = new HashSet<>(customers);
        return customersCopy;
    }

    public boolean createCustomer(String name) {
        synchronized (monitor) {
            for (Customer customer : customers) {
                if (customer.getName().equals(name)) {
                    return false;
                }
            }
            CustomerImpl customer = new CustomerImpl(name);
            customers.add(customer);
            return true;
        }
    }

    public boolean deleteCustomer(String name) {
        synchronized (monitor) {
            for (Customer customer : customers) {
                if (customer.getName().equals(name)) {
                    customers.remove(customer);
                    return true;
                }
            }
            return false;
        }
    }

    public void setNumOfCargos(Customer customer, int num) {
        synchronized (monitor) {
            if (customer instanceof CustomerImpl) {
                CustomerImpl customerImpl = (CustomerImpl) customer;
                int numOfCargos = customer.getNumOfCargos();
                if (num == -1 && numOfCargos == 0) {
                } else {
                    customerImpl.setNumOfCargos(numOfCargos + num);
                }
            }
        }
    }
}
