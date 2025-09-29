package Repository;

import Modelo.EstudianteCarrera;

public interface EstudianteRepository {
    public void insertarDesdeCSV();
    public void insertarEstudiante(EstudianteCarrera es);
}
