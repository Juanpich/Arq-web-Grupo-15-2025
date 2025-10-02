package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CarreraConInscriptosDTO {
    private int cant;
    private String nombreCarrera;
    private int duracion;

}
