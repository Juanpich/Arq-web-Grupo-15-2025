package service.dto.estudiante.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Data
public class EstudianteDtoRequest {
    private int dni;

    private String nombre;

    private String apellido;

    private String genero;

    private int edad;

    private String ciudad;

    private int LU;
}

