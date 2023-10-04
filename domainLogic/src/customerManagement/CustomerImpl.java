package customerManagement;

import administration.Customer;

class CustomerImpl implements Customer {

    private final static long serialVersionUID = 1L;
    private final String name;
    private int numOfCargos;

    public CustomerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumOfCargos() {
        return numOfCargos;
    }

    void setNumOfCargos(int numOfCargos) {
        this.numOfCargos = numOfCargos;
    }

    @Override
    public String toString() {
        return "name: " + name + ", num of cargos: " + numOfCargos;
    }
}
