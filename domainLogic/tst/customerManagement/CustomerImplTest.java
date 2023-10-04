package customerManagement;

import administration.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CustomerImplTest {

    private CustomerImpl customerImpl;

    @BeforeEach
    void setUp() {
        customerImpl = new CustomerImpl("Emil");
    }

    // Testing getName()
    @Test
    void get_name_test_customerImpl() {
        assertEquals("Emil", customerImpl.getName());
    }

    @Test
    void get_name_test_customerImpl_with_name_Emily() {
        CustomerImpl customerImpl = new CustomerImpl("Emily");
        assertEquals("Emily", customerImpl.getName());
    }

    @Test
    void get_name_test_customer_interface() {
        HashSet<Customer> customers = new HashSet<>();
        customers.add(customerImpl);
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
        }
        assertEquals("Emil", customer.getName());
    }

    // Testing getNumOfCargos & setNumOfCargos()
    @Test
    void get_num_of_cargos_with_no_cargos_test_customerImpl() {
        assertEquals("Emil", customerImpl.getName());
    }

    @Test
    void get_num_of_cargos_with_one_cargo_test_customerImpl() {
        customerImpl.setNumOfCargos(1);
        assertEquals(1, customerImpl.getNumOfCargos());
    }

    @Test
    void get_num_of_cargos_with_four_cargos_test_customerImpl() {
        customerImpl.setNumOfCargos(4);
        assertEquals(4, customerImpl.getNumOfCargos());
    }

    @Test
    void get_num_of_cargos_with_one_cargo_test_customer_interface() {
        customerImpl.setNumOfCargos(1);
        HashSet<Customer> customers = new HashSet<>();
        customers.add(customerImpl);
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
        }
        assertEquals(1, customer.getNumOfCargos());
    }

    // Testing toString()
    @Test
    void toString_test_if_it_contains_num_of_cargos_is_0() {
        String result = customerImpl.toString();
        assertTrue(result.contains("num of cargos: 0"));
    }

    @Test
    void toString_test_if_it_contains_num_of_cargos_is_2() {
        customerImpl.setNumOfCargos(2);
        String result = customerImpl.toString();
        assertTrue(result.contains("2"));
    }

    @Test
    void toString_test_if_it_contains_name_is_emil() {
        String result = customerImpl.toString();
        assertTrue(result.contains("name: Emil"));
    }

    @Test
    void toString_test_if_it_contains_name_is_emil_using_customer_interface() {
        HashSet<Customer> customers = new HashSet<>();
        customers.add(customerImpl);
        Customer customer = null;
        for (Customer c : customers) {
            customer = c;
        }
        String result = customer.toString();
        assertTrue(result.contains("name: Emil"));
    }
}