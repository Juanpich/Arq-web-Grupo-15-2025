package org.example.journeysservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ScooterKmReportDTO {

    private Long scooterId;
    private Long kmTraveled;
    //private Long totalHoures;
    private Long pauseMinutes;

    public ScooterKmReportDTO(Long id, Long kmTraveled, /*Long totalHoures,*/ Long pauseMinutes) {
        this.scooterId = id;
        this.kmTraveled = kmTraveled;
        //this.totalHoures = totalHoures;
        this.pauseMinutes = pauseMinutes;
    }
    public ScooterKmReportDTO (Long id, Long km/*, Long hours*/){
        this.scooterId = id;
        this.kmTraveled = km;
        //this.totalHoures = hours;
    }
}
