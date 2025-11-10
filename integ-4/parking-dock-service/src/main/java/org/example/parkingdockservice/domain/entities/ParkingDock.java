package org.example.parkingdockservice.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingDock_id;
    private String parkingDock_ubication;

    @ElementCollection
    private List<Long> scooters;

    public ParkingDock(String parkingDock_ubication) {
        this.parkingDock_ubication = parkingDock_ubication;
        this.scooters = new ArrayList<>();
    }

    //Cuando se crea una parada, tiene el arreglo de scooters vacia
    public List<Long> getScooters(){
        if(scooters==null){
            this.scooters= new ArrayList<>();
        }
        return this.scooters;
    }

    //Agregar una scooter a la parada
    public void addScooter(Long id){
        this.scooters.add(id);
    }
}
