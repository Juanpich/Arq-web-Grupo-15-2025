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
public class CarreraRegisteredCountDTO {

    private int cant;

    private String nombreCarrera;

    private int duracion;

    public CarreraRegisteredCountDTO(Carrera carrera) {

        this.cant = carrera.getCant();
        this.nombreCarrera =  carrera.getCarrera();
        this.duracion = carrera.getDuracion();
    }

    public CarreraRegisteredCountDTO(CarreraRegisteredCountDTO carreraDTO) {

        this.cant = carreraDTO.getCant();
        this.nombreCarrera =  carreraDTO.getNombreCarrera();
        this.duracion = carreraDTO.getDuracion();
    }
}