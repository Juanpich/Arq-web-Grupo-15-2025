package org.example.integ3.service.dto.carrera.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.integ3.model.Carrera;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CarreraConInscriptosDTO {
    private int cant;
    private String nombreCarrera;
    private int duracion;


    public CarreraConInscriptosDTO(CarreraConInscriptosDTO carreraConInscriptosDTO) {
        this.cant = carreraConInscriptosDTO.getCant();
        this.nombreCarrera =  carreraConInscriptosDTO.getNombreCarrera();
        this.duracion = carreraConInscriptosDTO.getDuracion();
    }
}