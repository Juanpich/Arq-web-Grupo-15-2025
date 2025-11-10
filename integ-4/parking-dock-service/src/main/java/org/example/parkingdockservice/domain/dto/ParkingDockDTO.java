package org.example.parkingdockservice.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.parkingdockservice.domain.entities.ParkingDock;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParkingDockDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingDock_id;
    private String parkingDock_ubication;
    private List<Long> scooters;

    public ParkingDockDTO(ParkingDock parkingDock){
        this.parkingDock_id= parkingDock.getParkingDOck_id();
        this.parkingDock_ubication= parkingDock.getParkingDock_ubication();
        this.scooters = parkingDock.getScooters();
    }
}
