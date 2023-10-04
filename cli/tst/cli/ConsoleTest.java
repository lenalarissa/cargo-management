package cli;

import administration.Customer;
import eventSystemInfrastructure.Handler;
import events.CustomerEvent;
import events.NetEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsoleTest {

    Console console;

    @BeforeEach
    void setUp() {
        console = new Console(new Scanner(System.in));
    }

    // Testing execute() for creating customer and reading customers
    @Test
    void create_customer_test_input_stream_verify_true() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String input = ":c" + System.getProperty("line.separator") + "Emil" + System.getProperty("line.separator") + ":x" + System.getProperty("line.separator");
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);
        console = spy(new Console(scanner));
        console.execute();
        verify(console).create("Emil");
    }

    @Test
    void read_customers_test_input_stream_verify_true() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String input = ":r" + System.getProperty("line.separator") + "customers" + System.getProperty("line.separator") + ":x" + System.getProperty("line.separator");
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);
        console = spy(new Console(scanner));
        console.execute();
        verify(console).read("customers");
    }

    // Testing create() for creating customers only
    @Test
    void create_customer_correctly_result_true() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String customerName = "Emil";
        boolean result = console.create(customerName);
        assertTrue(result);
    }

    @Test
    void create_customer_with_only_numbers_in_name_result_false() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String customerName = "123";
        boolean result = console.create(customerName);
        assertFalse(result);
    }

    @Test
    void create_customer_with_numbers_in_name_result_true() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String customerName = "Emil123";
        boolean result = console.create(customerName);
        assertTrue(result);
    }

    @Test
    void create_customer_without_handler_result_false() {
        String customerName = "Emil";
        boolean result = console.create(customerName);
        assertFalse(result);
    }

    @Test
    void create_customer_with_two_words_result_false() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String customerName = "Emil Bauer";
        boolean result = console.create(customerName);
        assertFalse(result);
    }

    @Test
    void create_customer_without_letters_result_false() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String customerName = " ";
        boolean result = console.create(customerName);
        assertFalse(result);
    }

    @Test
    void create_customer_with_only_spaces_result_false() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String customerName = "       ";
        boolean result = console.create(customerName);
        assertFalse(result);
    }

    @Test
    void create_customer_with_special_characters_result_true() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String customerName = "&%,,,!°";
        boolean result = console.create(customerName);
        assertTrue(result);
    }

    @Test
    void create_customer_with_only_one_character_result_true() {
        console.setCreateCustomerHandler(new Handler<CustomerEvent>());
        String customerName = "Q";
        boolean result = console.create(customerName);
        assertTrue(result);
    }
    @Test
    void create_customer_test_if_create_customer_handler_is_called() {
        Handler<CustomerEvent> customerHandler = mock(Handler.class);
        console.setCreateCustomerHandler(customerHandler);
        String customerName = "Emil";
        boolean result = console.create(customerName);
        verify(customerHandler).handle(any());
    }
    @Test
    void create_customer_test_if_client_handler_is_called() {
        Handler<NetEvent> clientHandler = mock(Handler.class);
        console.setClientHandler(clientHandler);
        String customerName = "Emil";
        boolean result = console.create(customerName);
        verify(clientHandler).handle(any());
    }

    // Testing read() for reading customers only
    @Test
    void read_customers_correctly_test_if_it_prints_the_created_mock_customer_result_true() {
        HashSet<Customer> customers = new HashSet<>();
        customers.add(mock(Customer.class));
        console.setCustomers(customers);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        console.read("customers");
        String output = outputStream.toString().trim();
        System.setOut(originalOut);
        assertTrue(output.contains("Mock for Customer"));
    }

    @Test
    void read_customers_test_if_read_handler_is_called_when_set_verify_true() {
        Handler<EventObject> readCustomerHandler = mock(Handler.class);
        console.setReadCustomersHandler(readCustomerHandler);
        console.read("customers");
        verify(readCustomerHandler).handle(any());
    }
    @Test
    void read_customers_test_if_client_handler_is_called_when_set_verify_true() {
        Handler<NetEvent> clientHandler = mock(Handler.class);
        console.setClientHandler(clientHandler);
        console.read("customers");
        verify(clientHandler).handle(any());
    }
}