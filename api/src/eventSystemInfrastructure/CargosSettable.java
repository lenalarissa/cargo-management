package eventSystemInfrastructure;

import cargo.Cargo;

import java.util.HashMap;

public interface CargosSettable {
    void setCargos(HashMap<Integer, Cargo> cargos);
}
