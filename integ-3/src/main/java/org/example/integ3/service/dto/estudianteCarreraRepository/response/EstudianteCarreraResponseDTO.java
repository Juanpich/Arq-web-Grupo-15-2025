package org.example.integ3.service.dto.estudianteCarreraRepository.response;

import lombok.Data;
import org.example.integ3.model.Carrera;
import org.example.integ3.model.Estudiante;
import org.example.integ3.model.EstudianteCarrera;

@Data
public class EstudianteCarreraResponseDTO {
    private Long id_inscripcion;
    private int inscripcion;
    private int graduacion;
    private int antiguedad;
    private Estudiante id_estudiante;
    private Carrera id_carrera;

    public EstudianteCarreraResponseDTO(EstudianteCarrera result) {
        this.id_inscripcion = (long) result.getInscripcion();
        this.inscripcion = result.getInscripcion();
        this.graduacion = result.getGraduacion();
        this.antiguedad = result.getAntiguedad();
        this.id_estudiante = result.getId_estudiante();
        this.id_carrera = result.getId_carrera();
    }
}
