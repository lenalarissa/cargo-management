package customerManagement;

import administration.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CustomerManagementTest {

    CustomerManagement customerManagement;

    @BeforeEach
    void setUp() {
        Number monitor = new Integer(0);
        customerManagement = new CustomerManagement(monitor);
    }

    // Testing readCustomers()
    @Test
    void read_customers_with_empty_list_test_list_size() {
        assertEquals(0, customerManagement.readCustomers().size());
    }

    @Test
    void read_customers_with_list_size_of_1_test_list_size() {
        customerManagement.createCustomer("Test-Name-One");
        assertEquals(1, customerManagement.readCustomers().size());
    }

    @Test
    void read_customers_with_list_size_of_5_test_list_size() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-Two");
        customerManagement.createCustomer("Test-Name-Three");
        customerManagement.createCustomer("Test-Name-Four");
        customerManagement.createCustomer("Test-Name-Five");
        assertEquals(5, customerManagement.readCustomers().size());
    }

    @Test
    void read_customers_after_trying_to_add_to_returned_list_test_list_size() {
        HashSet<Customer> customers = customerManagement.readCustomers();
        Customer customer = mock(CustomerImpl.class);
        customers.add(customer);
        assertEquals(0, customerManagement.readCustomers().size());
    }

    @Test
    void read_customers_after_trying_to_remove_from_returned_list_test_list_size() {
        customerManagement.createCustomer("Test-Name-One");
        HashSet<Customer> customers = customerManagement.readCustomers();
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
            break;
        }
        customers.remove(customer);
        assertEquals(1, customerManagement.readCustomers().size());
    }

    @Test
    void read_customers_with_list_size_of_1_test_customer_in_list() {
        customerManagement.createCustomer("Test-Name-One");
        HashSet<Customer> customers = customerManagement.readCustomers();
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
            break;
        }
        assertEquals("Test-Name-One", customer.getName());
    }

    // Testing createCustomer()
    @Test
    void add_new_customer_to_empty_list_successfully_test_return() {
        boolean result = customerManagement.createCustomer("Test-Name");
        assertTrue(result);
    }

    @Test
    void add_new_customer_to_filled_list_successfully_test_return() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-Two");
        customerManagement.createCustomer("Test-Name-Three");
        boolean result = customerManagement.createCustomer("Test-Four");
        assertTrue(result);
    }

    @Test
    void add_already_existing_customer_to_list_unsuccessfully_test_return() {
        customerManagement.createCustomer("Test-Name");
        boolean result = customerManagement.createCustomer("Test-Name");
        assertFalse(result);
    }

    @Test
    void add_already_existing_customer_to_more_filled_list_unsuccessfully_test_return() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-Two");
        customerManagement.createCustomer("Test-Name-Three");
        customerManagement.createCustomer("Test-Name-Four");
        boolean result = customerManagement.createCustomer("Test-Name-Four");
        assertFalse(result);
    }

    @Test
    void add_already_existing_customer_several_time_unsuccessfully_test_list_size() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-One");
        assertEquals(1, customerManagement.readCustomers().size());
    }

    @Test
    void add_new_customer_to_empty_list_successfully_test_list_size() {
        customerManagement.createCustomer("Test-Name");
        assertEquals(1, customerManagement.readCustomers().size());
    }

    @Test
    void add_new_customer_to_filled_list_successfully_test_list_size() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-Two");
        customerManagement.createCustomer("Test-Name-Three");
        customerManagement.createCustomer("Test-Name-Four");
        assertEquals(4, customerManagement.readCustomers().size());
    }

    @Test
    void add_already_existing_customer_to_list_unsuccessfully_test_list_size() {
        customerManagement.createCustomer("Test-Name");
        customerManagement.createCustomer("Test-Name");
        assertEquals(1, customerManagement.readCustomers().size());
    }

    // Testing delete()
    @Test
    void delete_customer_on_empty_list_test_return() {
        boolean result = customerManagement.deleteCustomer("Test-Name-One");
        assertFalse(result);
    }

    @Test
    void delete_customer_on_empty_list_test_list_size() {
        customerManagement.deleteCustomer("Test-Name-One");
        assertEquals(0, customerManagement.readCustomers().size());
    }

    @Test
    void delete_one_customer_with_list_of_one_test_return() {
        customerManagement.createCustomer("Test-Name-One");
        boolean result = customerManagement.deleteCustomer("Test-Name-One");
        assertTrue(result);
    }

    @Test
    void delete_one_customer_with_list_of_one_test_list_size() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.deleteCustomer("Test-Name-One");
        assertEquals(0, customerManagement.readCustomers().size());
    }

    @Test
    void delete_one_customer_full_list_test_return() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-Two");
        customerManagement.createCustomer("Test-Name-Three");
        customerManagement.createCustomer("Test-Name-Four");
        boolean result = customerManagement.deleteCustomer("Test-Name-Three");
        assertTrue(result);
    }

    @Test
    void delete_one_customer_with_full_list_test_list_size() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-Two");
        customerManagement.createCustomer("Test-Name-Three");
        customerManagement.createCustomer("Test-Name-Four");
        customerManagement.deleteCustomer("Test-Name-Three");
        assertEquals(3, customerManagement.readCustomers().size());
    }

    @Test
    void delete_non_existing_customer_full_list_test_return() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-Two");
        customerManagement.createCustomer("Test-Name-Three");
        customerManagement.createCustomer("Test-Name-Four");
        boolean result = customerManagement.deleteCustomer("Test-Name-Five");
        assertFalse(result);
    }

    @Test
    void delete_non_existing_customer_with_full_list_test_list_size() {
        customerManagement.createCustomer("Test-Name-One");
        customerManagement.createCustomer("Test-Name-Two");
        customerManagement.createCustomer("Test-Name-Three");
        customerManagement.createCustomer("Test-Name-Four");
        customerManagement.deleteCustomer("Test-Name-Five");
        assertEquals(4, customerManagement.readCustomers().size());
    }

    //Testing setNumOfCargos();
    @Test
    void set_num_of_cargos_add_one_cargo_test_num_of_cargos() {
        customerManagement.createCustomer("Test-Name-One");
        HashSet<Customer> customers = customerManagement.readCustomers();
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
            break;
        }
        customerManagement.setNumOfCargos(customer, 1);
        assertEquals(1, customer.getNumOfCargos());
    }

    @Test
    void set_num_of_cargos_delete_one_cargo_test_num_of_cargos() {
        customerManagement.createCustomer("Test-Name-One");
        HashSet<Customer> customers = customerManagement.readCustomers();
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
            break;
        }
        customerManagement.setNumOfCargos(customer, -1);
        assertEquals(0, customer.getNumOfCargos());
    }

    @Test
    void set_num_of_cargos_add_three_cargos_test_num_of_cargos() {
        customerManagement.createCustomer("Test-Name-One");
        HashSet<Customer> customers = customerManagement.readCustomers();
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
            break; // es soll bloß an irgendeinem Customer getestet werden
        }
        customerManagement.setNumOfCargos(customer, 1);
        customerManagement.setNumOfCargos(customer, 1);
        customerManagement.setNumOfCargos(customer, 1);
        assertEquals(3, customer.getNumOfCargos());
    }

    @Test
    void set_num_of_cargos_add_three_cargos_and_delete_two_cargos_test_num_of_cargos() {
        customerManagement.createCustomer("Test-Name-One");

        HashSet<Customer> customers = customerManagement.readCustomers();
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
            break; // es soll bloß an irgendeinem Customer getestet werden
        }
        customerManagement.setNumOfCargos(customer, 1);
        customerManagement.setNumOfCargos(customer, 1);
        customerManagement.setNumOfCargos(customer, 1);
        customerManagement.setNumOfCargos(customer, -1);
        customerManagement.setNumOfCargos(customer, -1);
        assertEquals(1, customer.getNumOfCargos());
    }
}