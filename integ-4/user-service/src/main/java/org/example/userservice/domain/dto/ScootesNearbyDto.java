package org.example.userservice.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.userservice.models.Scooter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScootesNearbyDto {
    private Long scooter_id;
    private String gps;
    private Long parkingDock_id;
    public ScootesNearbyDto(Scooter scooter){
        this.scooter_id = scooter.getScooter_id();
        this.gps = scooter.getGps();
        this.parkingDock_id = scooter.getParkingDock_id();
    }
}


