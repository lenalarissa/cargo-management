package cargoManagement;

import cargo.Hazard;
import customerManagement.CustomerPojo;

import java.util.Date;
import java.util.HashSet;

public class CargoPojo {
    public String cargoClass;
    public CustomerPojo customerPojo;
    public int storageLocation;
    public Date insertDate;
    public String value;

    public HashSet<Hazard> hazards;
    public boolean fragile;
    public boolean pressurized;
    public int grainSize;
    public Date lastInspectionDate;

}
