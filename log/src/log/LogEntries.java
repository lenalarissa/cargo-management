package log;

import java.io.Serializable;

public class LogEntries implements Serializable {

    private String cargoCreated;
    private String cargoDeleted;
    private String cargoChanged;
    private String customerCreated;
    private String customerDeleted;
    private String numOfCargosSet;
    private String createCargo;
    private String deleteCargo;
    private String updateCargo;
    private String readCargos;
    private String readHazards;
    private String createCustomer;
    private String deleteCustomer;
    private String readCustomers;
    private String swapStorageLocations;

    public LogEntries(String language) {
        switch (language) {
            case "EN":
                createCargo = "user tries to create cargo";
                deleteCargo = "user tries to delete cargo";
                readCargos = "user tries to read cargos";
                updateCargo = "user tries to update cargo";
                readHazards = "user tries to read hazards";
                createCustomer = "user tries to create customer";
                deleteCustomer = "user tries to delete customer";
                readCustomers = "user tries to read customers";
                cargoCreated = "cargo created";
                cargoDeleted = "cargo deleted";
                cargoChanged = "cargo property changed";
                customerCreated = "customer created";
                customerDeleted = "customer deleted";
                numOfCargosSet = "num of cargos of customer set";
                swapStorageLocations = "user tries to swap storage locations";
                break;
            case "DE":
                createCargo = "User versucht Frachtstück zu erstellen";
                deleteCargo = "User versucht Frachtstück zu löschen";
                readCargos = "User versucht Frachtstücke auszulesen";
                updateCargo = "User versucht Frachtstück zu inspizieren";
                readHazards = "User versucht Gefahrenstoffe auszulesen";
                createCustomer = "User versucht Kund*in zu erstellen";
                deleteCustomer = "User versucht Kund*in zu löschen";
                readCustomers = "User versucht Kund*innen auszulesen";
                cargoCreated = "Frachtstück erstellt";
                cargoDeleted = "Frachtstück gelöscht";
                cargoChanged = "Frachtstück Eigenschaft verändert";
                customerCreated = "Kund*in erstellt";
                customerDeleted = "Kund*in gelöscht";
                numOfCargosSet = "Anzahl der Frachtstücke von Kund*in gesetzt";
                swapStorageLocations = "Kund*in versucht Lagerplätze zu vertauschen";
                break;
            default:
                break;
        }
    }

    String writeActionDL(ActionDL actionDL) {
        String toWrite;
        switch (actionDL) {
            case CREATECARGO:
                toWrite = createCargo;
                break;
            case DELETECARGO:
                toWrite = deleteCargo;
                break;
            case READCARGOS:
                toWrite = readCargos;
                break;
            case UPDATECARGO:
                toWrite = updateCargo;
                break;
            case READHAZARDS:
                toWrite = readHazards;
                break;
            case CREATECUSTOMER:
                toWrite = createCustomer;
                break;
            case DELETECUSTOMER:
                toWrite = deleteCustomer;
                break;
            case READCUSTOMERS:
                toWrite = readCustomers;
                break;
            case CARGOCREATED:
                toWrite = cargoCreated;
                break;
            case CARGODELETED:
                toWrite = cargoDeleted;
                break;
            case CARGOCHANGED:
                toWrite = cargoChanged;
                break;
            case CUSTOMERCREATED:
                toWrite = customerCreated;
                break;
            case CUSTOMERDELETED:
                toWrite = customerDeleted;
                break;
            case NUMOFCARGOSSET:
                toWrite = numOfCargosSet;
                break;
            case SWAPSTORAGELOCATIONS:
                toWrite = swapStorageLocations;
                break;
            default:
                toWrite = "";
                break;
        }
        return toWrite;
    }
}
