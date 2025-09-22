package Modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
}
