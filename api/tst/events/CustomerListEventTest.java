package events;

import administration.Customer;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class CustomerListEventTest {

    @Test
    void get_empty_customer_list_correctly() {
        HashSet<Customer> customers = new HashSet<>();
        CustomerListEvent customerListEvent = new CustomerListEvent(this, customers);
        assertEquals(customers, customerListEvent.getCustomers());
    }
    @Test
    void get_filled_customer_list_correctly() {
        HashSet<Customer> customers = new HashSet<>();
        customers.add(mock(Customer.class));
        CustomerListEvent customerListEvent = new CustomerListEvent(this, customers);
        assertEquals(1, customerListEvent.getCustomers().size());
    }
}