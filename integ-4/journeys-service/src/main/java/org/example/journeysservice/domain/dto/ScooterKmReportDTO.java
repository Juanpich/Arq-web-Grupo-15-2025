package org.example.journeysservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterKmReportDTO {

    private Long scooterId;
    private int kmTraveled;
    private Long totalHoures;
    private Long pauseMinutes;

    public ScooterKmReportDTO(Long id, int kmTraveled, Long totalHoures) {
        this.scooterId = id;
        this.kmTraveled = kmTraveled;
        this.totalHoures = totalHoures;
    }
}
