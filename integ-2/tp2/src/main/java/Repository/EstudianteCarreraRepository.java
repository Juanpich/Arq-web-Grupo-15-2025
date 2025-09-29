package Repository;

import Modelo.EstudianteCarrera;

public interface EstudianteCarreraRepository {
    public void insertarInscripcion(EstudianteCarrera inscripcion);
    public void insertarDesdeCSV();
}
