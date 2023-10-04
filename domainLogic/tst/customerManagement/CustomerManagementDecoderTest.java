package customerManagement;

import administration.Customer;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CustomerManagementDecoderTest {

    @Test
    void decodeCustomer_test_customer_name() {
        CustomerManagementDecoder customerManagementDecoder = new CustomerManagementDecoder();
        CustomerPojo customerPojo = new CustomerPojo();
        customerPojo.name = "Emil";
        Customer customer = customerManagementDecoder.decodeCustomer(customerPojo);
        assertTrue(customer.getName().equals(customerPojo.name));
    }

    @Test
    void decodeCustomer_test_customer_num_of_cargos() {
        CustomerManagementDecoder customerManagementDecoder = new CustomerManagementDecoder();
        CustomerPojo customerPojo = new CustomerPojo();
        customerPojo.name = "Emil";
        customerPojo.numOfCargos = 3;
        Customer customer = customerManagementDecoder.decodeCustomer(customerPojo);
        assertEquals(customerPojo.numOfCargos, customer.getNumOfCargos());
    }

    @Test
    void createCustomerManagement_test_size_of_customer_list(){
        CustomerManagementDecoder customerManagementDecoder = new CustomerManagementDecoder();
        CustomerManagement customerManagement = customerManagementDecoder.createCustomerManagement(new Integer(0), new HashSet<Customer>());
        assertEquals(0, customerManagement.readCustomers().size());
    }
}