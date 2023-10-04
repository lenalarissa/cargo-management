package gui;

import cargo.Hazard;
import eventSystemInfrastructure.Handler;
import events.CargoEvent;
import javafx.concurrent.Task;

import java.math.BigDecimal;
import java.util.HashSet;

public class CreateCargoTask extends Task<Object> {

    private final String[] properties;
    private final Handler<CargoEvent> createCargoHandler;

    public CreateCargoTask(String[] properties, Handler<CargoEvent> createCargoHandler) {
        this.properties = properties;
        this.createCargoHandler = createCargoHandler;
    }


    @Override
    protected Long call() throws Exception {
        try {
            String cargoClass = properties[0];
            String customerName = properties[1];
            BigDecimal value = new BigDecimal(properties[2].replace(',', '.'));
            HashSet<Hazard> hazards = new HashSet<>();
            if (!properties[3].equals(",")) { // checks if there are hazards
                String[] hazardArray = properties[3].split(",");
                try {
                    for (String hazard : hazardArray) {
                        hazards.add(Hazard.valueOf(hazard));
                    }
                } catch (IllegalArgumentException e) {
                }
            }
            boolean fragile = false;
            boolean pressurized = false;
            int grainSize = 0;
            switch (cargoClass) {
                case "DryBulkAndUnitisedCargo":
                    fragile = Boolean.parseBoolean(properties[4]);
                    grainSize = Integer.parseInt(properties[5]);
                    break;
                case "DryBulkCargo":
                    grainSize = Integer.parseInt(properties[4]);
                    break;
                case "LiquidAndDryBulkCargo":
                    pressurized = Boolean.parseBoolean(properties[4]);
                    grainSize = Integer.parseInt(properties[5]);
                    break;
                case "LiquidBulkAndUnitisedCargo":
                    fragile = Boolean.parseBoolean(properties[4]);
                    pressurized = Boolean.parseBoolean(properties[5]);
                    break;
                case "LiquidBulkCargo":
                    pressurized = Boolean.parseBoolean(properties[4]);
                    break;
                case "UnitisedCargo":
                    fragile = Boolean.parseBoolean(properties[4]);
                    break;
                default:
                    break;
            }
            CargoEvent cargoEvent = new CargoEvent(this, cargoClass, customerName, value, hazards, fragile, pressurized, grainSize);
            if (createCargoHandler != null) {
                createCargoHandler.handle(cargoEvent);
                updateValue(1);
                return 1L;
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException ignored) {
        }
        return 1L;
    }
}
