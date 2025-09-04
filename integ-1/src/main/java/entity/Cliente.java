package entity;


import lombok.*;

@Data
public class Cliente {
    private int id;
    private String nombre;
    private String email;



    public Cliente(int id, String nombre, String email){
        this.id=id;
        this.nombre=nombre;
        this.email=email;
    }

    

}

