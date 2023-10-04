package persistence;

import cargoManagement.CargoStorageDecoder;
import cargoManagement.CargoStorageEncoder;
import customerManagement.CustomerManagementDecoder;
import domainLogicManagement.ManagementFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class JBPTest {

    // Nicht testbar aufgrund des Zugriffs auf das Dateisystem
    JBP jbp;

    @Test
    void testConstructor_after_constructing_not_null(){
        ManagementFacade managementFacade = mock(ManagementFacade.class);
        jbp = new JBP(managementFacade, new CargoStorageEncoder(), new CargoStorageDecoder(new CustomerManagementDecoder()));
        assertNotNull(jbp);
    }



}