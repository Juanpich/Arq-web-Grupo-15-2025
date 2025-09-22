package Modelo;

import javax.persistence.*;
import java.util.List;

@Entity
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

    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public int getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(int id_carrera) {
        this.id_carrera = id_carrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public List<EstudianteCarrera> getInscriptos(){
        return Inscriptos;
    }

    public void setInscriptos(List<EstudianteCarrera> NuevosInscriptos){
        Inscriptos = NuevosInscriptos;
    }
}
