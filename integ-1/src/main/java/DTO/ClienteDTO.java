package DTO;

import lombok.Data;

@Data
public class ClienteDTO {
    private String nombre;
    private String mail;
    private float importeFacturado;
    
    public ClienteDTO(String nombre, String mail, float importeFacturado) {
        this.nombre = nombre;
        this.mail = mail;
        this.importeFacturado = importeFacturado;
    }
}
