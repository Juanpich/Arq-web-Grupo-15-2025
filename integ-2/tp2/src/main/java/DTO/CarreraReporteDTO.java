package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CarreraReporteDTO {
    private String carrera;
    private int cantInscriptos;
    private int cantEgresados;
    private int anio;

}
