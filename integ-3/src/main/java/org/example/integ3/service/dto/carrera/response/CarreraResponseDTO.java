package org.example.integ3.service.dto.carrera.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.integ3.model.Carrera;
import org.example.integ3.model.EstudianteCarrera;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CarreraResponseDTO {

    private int id_carrera;

    private String nombre_carrera;

    private int duracion;

    public CarreraResponseDTO(Carrera carrera){
        this.id_carrera = carrera.getId_carrera();
        this.duracion = carrera.getDuracion();
        this.nombre_carrera = carrera.getCarrera();
    }

    public Carrera DTOToEntity() {
        Carrera car = new Carrera(this.getId_carrera(), this.getNombre_carrera(), this.getDuracion(), new ArrayList<>());
        return car;
    }
}