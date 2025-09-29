package Repository;

import Modelo.Estudiante;
import Modelo.EstudianteCarrera;

public interface EstudianteRepository {
    public void insertarDesdeCSV();
    public void insertarEstudiante(Estudiante es);
}
