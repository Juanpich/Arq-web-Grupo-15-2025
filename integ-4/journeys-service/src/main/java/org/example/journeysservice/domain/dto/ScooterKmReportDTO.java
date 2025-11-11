package org.example.journeysservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterKmReportDTO {

    private Long scooterId;
    private Long kmTraveled;
    private Long totalHoures;
    private Long pauseMinutes;

    public ScooterKmReportDTO(Long id, Long kmTraveled, int totalHoures, Long pauseMinutes) {
        this.scooterId = id;
        this.kmTraveled = kmTraveled;
        this.totalHoures = Long.valueOf(totalHoures);
        this.pauseMinutes = pauseMinutes;
    }
    public ScooterKmReportDTO (Long id, Long km, int hours){
        this.scooterId = id;
        this.kmTraveled = km;
        this.totalHoures = Long.valueOf(hours);
    }
}
