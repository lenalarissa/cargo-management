package cli;

class Command {
    enum Operator {CREATE, READ, UPDATE, DELETE, PERSISTENCE, EXIT, ERROR}

    private Operator operator;

    Operator getOperator() {
        return operator;
    }

    Command(String text) {
        try {
            String op = text.substring(0, 2);
            switch (op) {
                case ":c":
                    this.operator = Operator.CREATE;
                    break;
                case ":r":
                    this.operator = Operator.READ;
                    break;
                case ":u":
                    this.operator = Operator.UPDATE;
                    break;
                case ":d":
                    this.operator = Operator.DELETE;
                    break;
                case ":p":
                    this.operator = Operator.PERSISTENCE;
                    break;
                case ":x":
                    this.operator = Operator.EXIT;
                    break;
                default:
                    this.operator = Operator.ERROR;
                    break;
            }
        } catch (StringIndexOutOfBoundsException e) {
            this.operator = Operator.ERROR;
        }
    }
}