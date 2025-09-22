package Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EstudianteCarrera {
    @Id
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


}
