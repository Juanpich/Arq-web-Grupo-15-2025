package Repository;

import DTO.EstudianteFiltradioDTO;
import Modelo.Carrera;
import Modelo.Estudiante;
import Modelo.EstudianteCarrera;

import java.util.List;

public interface EstudianteRepository {
    public void insertarDesdeCSV();
    public void insertarEstudiante(Estudiante es);
    public List<Estudiante> obtenerEstudiantes();
    public List<Estudiante> estudiantesByGenero(String genero);
    public List<EstudianteFiltradioDTO> estudiantesByCarrera(Carrera carrera, String ciudad);
}
