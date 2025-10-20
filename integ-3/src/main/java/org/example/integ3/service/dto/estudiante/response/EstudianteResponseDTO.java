package org.example.integ3.service.dto.estudiante.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.integ3.model.Estudiante;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EstudianteResponseDTO {
    private int dni;

    private String nombre;

    private String apellido;

    private String genero;

    private int edad;

    private String ciudad;

    private int LU;

    public EstudianteResponseDTO(Estudiante estudiante) {
        this.dni=estudiante.getDni();
        this.nombre=estudiante.getNombre();
        this.apellido=estudiante.getApellido();
        this.genero=estudiante.getGenero();
        this.edad=estudiante.getEdad();
        this.ciudad=estudiante.getCiudad();
        this.LU=estudiante.getLU();
    }

    public EstudianteResponseDTO(EstudianteResponseDTO estudiante) {
        this.dni=estudiante.getDni();
        this.nombre=estudiante.getNombre();
        this.apellido=estudiante.getApellido();
        this.genero=estudiante.getGenero();
        this.edad=estudiante.getEdad();
        this.ciudad=estudiante.getCiudad();
        this.LU=estudiante.getLU();
    }

    public Estudiante DTOToEntity() {
        Estudiante estudiante = new Estudiante(this.getDni(),
                            this.getNombre(), this.getApellido(),
                            this.getGenero(), this.getEdad(),
                            this.getCiudad(), this.getLU(), new ArrayList<>());
        return estudiante;
    }
}

