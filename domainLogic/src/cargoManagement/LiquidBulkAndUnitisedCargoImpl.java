package cargoManagement;

import administration.Customer;
import cargo.Hazard;
import cargo.LiquidBulkAndUnitisedCargo;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

class LiquidBulkAndUnitisedCargoImpl implements LiquidBulkAndUnitisedCargo, CargoInternal {

    private final static long serialVersionUID = 1L;
    private final Customer owner;
    private final Date insertDate;
    private int storageLocation;
    private final BigDecimal value;
    private final HashSet<Hazard> hazards;
    private final boolean pressurized;
    private final boolean fragile;
    private Date lastInspectionDate;

    public LiquidBulkAndUnitisedCargoImpl(Customer owner, int storageLocation, Date insertDate, BigDecimal value, HashSet<Hazard> hazards, boolean fragile, boolean pressurized) {
        this.owner = owner;
        this.storageLocation = storageLocation;
        this.insertDate = insertDate;
        this.value = value;
        this.hazards = hazards;
        this.pressurized = pressurized;
        this.fragile = fragile;
    }

    @Override
    public boolean isFragile() {
        return fragile;
    }

    @Override
    public boolean isPressurized() {
        return pressurized;
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

    public Date getInsertDate() {
        return insertDate;
    }

    @Override
    public String toString() {
        return "customer: " + owner.getName() + ", storage location: " + storageLocation + ", duration of storage: " + getDurationOfStorage() + ", last inspection date: " + lastInspectionDate;
    }
}
