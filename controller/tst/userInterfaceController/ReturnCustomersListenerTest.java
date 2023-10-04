package userInterfaceController;

import cli.Console;
import events.CustomerListEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Scanner;

import static org.mockito.Mockito.mock;

class ReturnCustomersListenerTest {

    @Test
    void on_event_tests_if_get_customers_was_called_verify_true() {
        Console console = new Console(new Scanner(System.in));
        ReturnCustomersListener returnCustomersListener = new ReturnCustomersListener(console);
        CustomerListEvent customerListEvent = mock(CustomerListEvent.class);

        returnCustomersListener.onEvent(customerListEvent);
        Mockito.verify(customerListEvent, Mockito.times(1)).getCustomers();
    }

    @Test
    void on_event_tests_if_set_customers_was_called_verify_true() {
        Console console = mock(Console.class);
        ReturnCustomersListener returnCustomersListener = new ReturnCustomersListener(console);
        CustomerListEvent customerListEvent = new CustomerListEvent(this, new HashSet<>());

        returnCustomersListener.onEvent(customerListEvent);
        Mockito.verify(console, Mockito.times(1)).setCustomers(customerListEvent.getCustomers());
    }

}