package org.example.journeysservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.journeysservice.domain.entities.Journey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class JourneyDTO {

    private Long journeyId;

    private Long scooterId;
    private LocalDate date;
    private LocalTime initHour;
    private LocalTime finishHour;
    private int kmTraveled;
    private Long pauseMinutes;

    public JourneyDTO(Journey newjourney) {
        this.journeyId = newjourney.getJourneyId();
        this.scooterId = newjourney.getScooterId();
        this.date = newjourney.getDate();
        this.initHour = newjourney.getInitHour();
        this.finishHour = newjourney.getFinishHour();
        this.kmTraveled = newjourney.getKmTraveled();
        this.pauseMinutes = newjourney.getPauseMinutes();
    }
}
