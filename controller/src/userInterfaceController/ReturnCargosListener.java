package userInterfaceController;

import eventSystemInfrastructure.CargosSettable;
import eventSystemInfrastructure.Listener;
import events.CargoListEvent;

public class ReturnCargosListener implements Listener<CargoListEvent> {
    private final CargosSettable cargosSettable;

    public ReturnCargosListener(CargosSettable cargosSettable) {
        this.cargosSettable = cargosSettable;
    }

    @Override
    public void onEvent(CargoListEvent event) {
        cargosSettable.setCargos(event.getCargos());
    }
}
