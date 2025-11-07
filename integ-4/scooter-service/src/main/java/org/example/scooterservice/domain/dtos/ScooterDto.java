package org.example.scooterservice.domain.dtos;
import org.example.scooterservice.domain.entities.Scooter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scooterservice.domain.entities.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ScooterDto {
    private Long scooter_id;
    private State state;
    private String gps;
    private Long parkingDock_id;

    public ScooterDto(Scooter scooter) {
        this.scooter_id = scooter.getScooter_id();
        this.state = scooter.getState();
        this.gps = scooter.getGps();
        this.parkingDock_id = scooter.getParkingDockId();
    }
}
