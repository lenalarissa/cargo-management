package cargoManagement;

import administration.Customer;
import cargo.DryBulkAndUnitisedCargo;
import cargo.Hazard;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

class DryBulkAndUnitisedCargoImpl implements DryBulkAndUnitisedCargo, CargoInternal {

    private final static long serialVersionUID = 1L;
    private final Customer owner;
    private int storageLocation;
    private final Date insertDate;
    private final BigDecimal value;
    private final HashSet<Hazard> hazards;
    private final boolean fragile;
    private final int grainSize;
    private Date lastInspectionDate;

    public DryBulkAndUnitisedCargoImpl(Customer owner, int storageLocation, Date insertDate, BigDecimal value, HashSet<Hazard> hazards, boolean fragile, int grainSize) {
        this.owner = owner;
        this.insertDate = insertDate;
        this.storageLocation = storageLocation;
        this.value = value;
        this.hazards = hazards;
        this.grainSize = grainSize;
        this.fragile = fragile;
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

    @Override
    public boolean isFragile() {
        return fragile;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    @Override
    public String toString() {
        return "customer: " + owner.getName() + ", storage location: " + storageLocation + ", duration of storage: " + getDurationOfStorage() + ", last inspection date: " + lastInspectionDate;
    }
}
