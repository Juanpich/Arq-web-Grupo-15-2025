package Repository;

import DTO.CarreraConInscriptosDTO;
import DTO.CarreraReporteDTO;
import Modelo.Carrera;

import java.util.List;

public interface CarreraRepository {
    public void insertarDesdeCSV();
    public void insertarCarrera (Carrera carr);
    public List<CarreraReporteDTO> consultarReporteCarrera();
    public List<CarreraConInscriptosDTO> consultarCarrerasConInscriptos();
}
