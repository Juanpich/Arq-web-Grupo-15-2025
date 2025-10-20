package org.example.integ3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int inscripcion;
    @Column
    private int graduacion;
    @Column
    private int antiguedad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "id_estudiante")
    private Estudiante id_estudiante;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "id_carrera")
    private Carrera id_carrera;

    public EstudianteCarrera(int inscripcion, int graduacion, int antiguedad, Estudiante id_estudiante, Carrera id_carrera) {
        this.inscripcion = inscripcion;
        this.graduacion = graduacion;
        this.antiguedad = antiguedad;
        this.id_estudiante = id_estudiante;
        this.id_carrera = id_carrera;
    }
}
