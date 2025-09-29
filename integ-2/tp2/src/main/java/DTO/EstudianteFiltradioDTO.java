package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class EstudianteFiltradioDTO {
    private int dni;

    private String nombre;

    private String apellido;

    private String genero;

    private int edad;

    private String ciudad;

    private int LU;
}
