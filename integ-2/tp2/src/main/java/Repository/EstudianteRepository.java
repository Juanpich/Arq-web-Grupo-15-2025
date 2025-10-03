package Repository;

import DTO.EstudianteFiltradoDTO;
import Modelo.Carrera;
import Modelo.Estudiante;
import Modelo.EstudianteCarrera;

import java.util.List;

public interface EstudianteRepository {
    public void insertarDesdeCSV();
    public void insertarEstudiante(Estudiante es);
    public EstudianteFiltradoDTO obtenerEstudianteSegunLU(int lu);
    public List<EstudianteFiltradoDTO> obtenerEstudiantes();
    public List<EstudianteFiltradoDTO> estudiantesByGenero(String genero);
    public List<EstudianteFiltradoDTO> estudiantesByCarrera(Carrera carrera, String ciudad);
}
