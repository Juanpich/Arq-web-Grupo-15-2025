package org.example.integ3.service.dto.carrera.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CarreraResponseReportDTO {
    private String carrera;
    private int cantInscriptos;
    private int cantEgresados;
    private int anio;

    public CarreraResponseReportDTO(CarreraResponseReportDTO carreraResponseReportDTO) {
        this.carrera = carreraResponseReportDTO.getCarrera();
        this.cantInscriptos = carreraResponseReportDTO.getCantInscriptos();
        this.cantEgresados = carreraResponseReportDTO.getCantEgresados();
        this.anio = carreraResponseReportDTO.getAnio();
    }
}