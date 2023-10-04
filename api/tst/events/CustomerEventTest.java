package events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEventTest {

    @Test
    void get_name_correctly() {
        CustomerEvent customerEvent = new CustomerEvent(this, "Emil");
        assertEquals("Emil", customerEvent.getName());
    }
}