package Modelo;

import javax.persistence.*;

@Entity
public class EstudianteCarrera {
    @Id
    private int id;
    @Column
    @ManyToOne(fetch = FetchType.LAZY)
    private int id_estudiante;
    @Column
    @ManyToOne(fetch = FetchType.LAZY)
    private int id_carrera;
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
    public int getId_estudiante() {
        return id_estudiante;
    }
    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }
    public int getId_carrera() {
        return id_carrera;
    }
    public void setId_carrera(int id_carrera) {
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
