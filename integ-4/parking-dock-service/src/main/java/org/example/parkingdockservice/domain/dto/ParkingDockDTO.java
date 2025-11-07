package org.example.parkingdockservice.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.parkingdockservice.domain.entities.ParkingDock;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParkingDockDTO {

    private Long parkingDock_id;
    private String parkingDock_ubication;
    private List<Long> scooters;

    public ParkingDockDTO(ParkingDock parkingDock){
        this.parkingDock_id= parkingDock.getParkingDock_id();
        this.parkingDock_ubication= parkingDock.getParkingDock_ubication();
        this.scooters = parkingDock.getScooters();
    }
}
