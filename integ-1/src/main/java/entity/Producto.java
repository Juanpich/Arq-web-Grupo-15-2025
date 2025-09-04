package entity;

import lombok.*;

@Data
public class Producto {
    private int id;
    private String nombre;
    private float valor;

    public Producto(int id, String nombre, float valor){
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }
    
}
