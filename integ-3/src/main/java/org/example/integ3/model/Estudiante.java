package org.example.integ3.model;


import jakarta.persistence.*;
import lombok.*;
import org.example.integ3.service.dto.estudiante.request.EstudianteDtoRequest;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
@Entity
public class Estudiante {
    @Id
    private int dni;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String genero;
    @Column
    private int edad;
    @Column
    private String ciudad;
    @Column
    private int LU;
    @OneToMany(mappedBy = "id_estudiante")
    private List<EstudianteCarrera> carreras;

    public Estudiante(EstudianteDtoRequest estudianteDtoRequest) {
        this.dni = estudianteDtoRequest.getDni();
        this.nombre = estudianteDtoRequest.getNombre();
        this.apellido = estudianteDtoRequest.getApellido();
        this.genero = estudianteDtoRequest.getGenero();
        this.edad = estudianteDtoRequest.getEdad();
        this.ciudad = estudianteDtoRequest.getCiudad();
        this.LU = estudianteDtoRequest.getLU();
        this.carreras = new ArrayList<>();
    }
}
