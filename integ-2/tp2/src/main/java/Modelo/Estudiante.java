package Modelo;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private int LU;
    @OneToMany(mappedBy = "id_estudiante")
    private List<EstudianteCarrera> carreras;

    public Estudiante() {
        super();
        this.carreras = new ArrayList<>();
    }
    public Estudiante(int dni, String nombre, String apellido, String genero, int edad, int LU) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.LU = LU;
        this.carreras = new ArrayList<>();
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getApellido() {
        return apellido;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public int getEdad() {
        return edad;
    }
    public void setLU(int LU) {
        this.LU = LU;
    }
    public int getLU() {
        return LU;
    }
    public void setDni(int dni) {
        this.dni = dni;
    }
    public int getDni() {
        return dni;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getGenero() {
        return genero;
    }
}
