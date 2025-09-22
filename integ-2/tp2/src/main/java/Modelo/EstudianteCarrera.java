package Modelo;

import jakarta.persistence.*;

@Entity
public class EstudianteCarrera {
    @Id
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "id_estudiante")
    private Estudiante id_estudiante;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "id_carrera")
    private Carrera id_carrera;
    @Column
    private int inscripcion;
    @Column
    private int graduacion;
    @Column
    private int antiguedad;

    public EstudianteCarrera() {
        super();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Estudiante getId_estudiante() {
        return id_estudiante;
    }
    public void setId_estudiante(Estudiante id_estudiante) {
        this.id_estudiante = id_estudiante;
    }
    public Carrera getId_carrera() {
        return id_carrera;
    }
    public void setId_carrera(Carrera id_carrera) {
        this.id_carrera = id_carrera;
    }
    public int getInscripcion() {
        return inscripcion;
    }
    public void setInscripcion(int inscripcion) {
        this.inscripcion = inscripcion;
    }
    public int getGraduacion() {
        return graduacion;
    }
    public void setGraduacion(int graduacion) {
        this.graduacion = graduacion;
    }
    public int getAntiguedad() {
        return antiguedad;
    }
    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }
}
