package org.example.journeysservice.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeUserIdDTO {

    private Long userId;
    private Long totalScooters;
    private Long journeysCount;
    private Long totalKm;
    private Long totalHoures;

    public DateRangeUserIdDTO(Long userId, int totalScooters, int journeysCount, int totalKm, int totalHoures) {
        this.userId = userId;
        this.totalScooters = Long.valueOf(totalScooters);
        this.journeysCount = Long.valueOf(journeysCount);
        this.totalKm = Long.valueOf(totalKm);
        this.totalHoures = Long.valueOf(totalHoures);
    }
}
