package cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {

    // Testing Create Mode
    @Test
    void test_create_mode_successfully(){
        String input = ":c";
        Command command = new Command(input);
        assertEquals(Command.Operator.CREATE, command.getOperator());
    }
    @Test
    void test_create_mode_successfully_when_input_is_unnecessarily_long(){
        String input = ":c123  456";
        Command command = new Command(input);
        assertEquals(Command.Operator.CREATE, command.getOperator());
    }
    @Test
    void test_create_mode_when_input_is_only_c(){
        String input = "c";
        Command command = new Command(input);
        assertEquals(Command.Operator.ERROR, command.getOperator());
    }
    @Test
    void test_create_mode_when_input_is_wrong(){
        String input = ":";
        Command command = new Command(input);
        assertEquals(Command.Operator.ERROR, command.getOperator());
    }

    // Testing Read Mode
    @Test
    void test_read_mode_successfully(){
        String input = ":r";
        Command command = new Command(input);
        assertEquals(Command.Operator.READ, command.getOperator());
    }
    @Test
    void test_read_mode_successfully_when_input_is_unnecessarily_long(){
        String input = ":r123  456";
        Command command = new Command(input);
        assertEquals(Command.Operator.READ, command.getOperator());
    }
    @Test
    void test_read_mode_when_input_is_only_r(){
        String input = "r";
        Command command = new Command(input);
        assertEquals(Command.Operator.ERROR, command.getOperator());
    }
    @Test
    void test_read_mode_when_input_is_wrong(){
        String input = ":";
        Command command = new Command(input);
        assertEquals(Command.Operator.ERROR, command.getOperator());
    }
}