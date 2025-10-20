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
    private Long id_estudiante;
    private Long id_carrera;

    public EstudianteCarreraResponseDTO(EstudianteCarrera result) {
        this.id_inscripcion = (long) result.getInscripcion();
        this.inscripcion = result.getInscripcion();
        this.graduacion = result.getGraduacion();
        this.antiguedad = result.getAntiguedad();
        this.id_estudiante = (long) result.getId_estudiante().getDni();
        this.id_carrera = (long) result.getId_carrera().getId_carrera();
    }
}
