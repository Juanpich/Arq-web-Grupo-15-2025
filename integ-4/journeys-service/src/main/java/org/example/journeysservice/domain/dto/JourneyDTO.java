package org.example.journeysservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.journeysservice.domain.entities.Journey;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class JourneyDTO {

    private Long journeyId;

    private Long scooterId;
    private Date date;
    private int initHour;
    private int finishHour;
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

    public Journey DTOToEntity() {
        Journey journey = new Journey(this.getJourneyId(), this.getScooterId(), this.getDate(),
                this.getInitHour(), this.getFinishHour(), this.getKmTraveled(), this.getPauseMinutes());
        return journey;
    }
}
