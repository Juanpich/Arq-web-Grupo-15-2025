package org.example.parkingdockservice.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.parkingdockservice.domain.entities.ParkingDock;

import java.util.List;

<<<<<<< HEAD

@Entity
=======
>>>>>>> a1a2112804e417048cc4de7f5d1e07aef6b65b71
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParkingDockDTO {
<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
=======

>>>>>>> a1a2112804e417048cc4de7f5d1e07aef6b65b71
    private Long parkingDock_id;
    private String parkingDock_ubication;
    private List<Long> scooters;

    public ParkingDockDTO(ParkingDock parkingDock){
<<<<<<< HEAD
        this.parkingDock_id= parkingDock.getParkingDOck_id();
=======
        this.parkingDock_id= parkingDock.getParkingDock_id();
>>>>>>> a1a2112804e417048cc4de7f5d1e07aef6b65b71
        this.parkingDock_ubication= parkingDock.getParkingDock_ubication();
        this.scooters = parkingDock.getScooters();
    }
}
