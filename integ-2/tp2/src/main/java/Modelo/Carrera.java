package Modelo;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Carrera {
    @Id
    @Column(name= "id_carrera")
    private int id_carrera;

    @Column(name= "carrera")
    private String carrera;

    @Column(name= "duracion")
    private int duracion;

    @OneToMany(mappedBy = "id_carrera")
    private List<EstudianteCarrera> Inscriptos;

}
