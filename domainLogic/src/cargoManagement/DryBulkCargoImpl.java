package cargoManagement;

import administration.Customer;
import cargo.DryBulkCargo;
import cargo.Hazard;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

class DryBulkCargoImpl implements DryBulkCargo, CargoInternal {

    private final static long serialVersionUID = 1L;
    private final Customer owner;
    private final Date insertDate;
    private int storageLocation;
    private final BigDecimal value;
    private final HashSet<Hazard> hazards;
    private final int grainSize;
    private Date lastInspectionDate;

    public DryBulkCargoImpl(Customer owner, int storageLocation, Date insertDate, BigDecimal value, HashSet<Hazard> hazards, int grainSize) {
        this.owner = owner;
        this.storageLocation = storageLocation;
        this.insertDate = insertDate;
        this.value = value;
        this.hazards = hazards;
        this.grainSize = grainSize;
    }

    @Override
    public Customer getOwner() {
        return owner;
    }

    @Override
    public Duration getDurationOfStorage() {
        return Duration.between(insertDate.toInstant(), new Date().toInstant());
    }

    @Override
    public Date getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    @Override
    public int getStorageLocation() {
        return storageLocation;
    }

    @Override
    public void setStorageLocation(int storageLocation) {
        this.storageLocation = storageLocation;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Collection<Hazard> getHazards() {
        return hazards;
    }

    @Override
    public int getGrainSize() {
        return grainSize;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    @Override
    public String toString() {
        return "customer: " + owner.getName() + ", storage location: " + storageLocation + ", duration of storage: " + getDurationOfStorage() + ", last inspection date: " + lastInspectionDate;
    }
}
