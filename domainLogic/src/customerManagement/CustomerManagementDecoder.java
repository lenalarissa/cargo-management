package customerManagement;

import administration.Customer;

import java.util.HashSet;

public class CustomerManagementDecoder {

    public Customer decodeCustomer(CustomerPojo customerPojo) {
        String name = customerPojo.name;
        int numOfCargos = customerPojo.numOfCargos;
        CustomerImpl customer = new CustomerImpl(name);
        customer.setNumOfCargos(numOfCargos);
        return customer;
    }

    public CustomerManagement createCustomerManagement(Number monitor, HashSet<Customer> customers) {
        return new CustomerManagement(monitor, customers);
    }
}
