package org.example.integ3.service.dto.estudianteCarreraRepository.request;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.integ3.model.Carrera;
import org.example.integ3.model.Estudiante;
@Data
public class EstudianteCarreraRequestDTO {
    private int inscripcion;
    private int graduacion;
    private int antiguedad;
    private Long id_estudiante;
    private Long id_carrera;
}
