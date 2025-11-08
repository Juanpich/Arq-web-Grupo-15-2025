package org.example.journeysservice.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journeyId;

    private Long scooterId;
    private Date date;
    private int initHour;
    private int finishHour;
    private int kmTraveled;
    private Long pauseMinutes;


    public Journey(Long scooterId, Date date, int initHour, int finishHour, int kmTraveled, Long pauseMinutes) {
        this.scooterId = scooterId;
        this.date = date;
        this.initHour = initHour;
        this.finishHour = finishHour;
        this.kmTraveled = kmTraveled;
        this.pauseMinutes = pauseMinutes;
    }

    public Journey(Long journeyId, Long scooterId, Date date, int initHour, int finishHour, int kmTraveled, Long pauseMinutes) {
        this.journeyId = journeyId;
        this.scooterId = scooterId;
        this.date = date;
        this.initHour = initHour;
        this.finishHour = finishHour;
        this.kmTraveled = kmTraveled;
        this.pauseMinutes = pauseMinutes;
    }

    public Journey(Journey newjourney) {
        this.journeyId = newjourney.getJourneyId();
        this.scooterId = newjourney.getScooterId();
        this.date = newjourney.getDate();
        this.initHour = newjourney.getInitHour();
        this.finishHour = newjourney.getFinishHour();
        this.kmTraveled = newjourney.getKmTraveled();
        this.pauseMinutes = newjourney.getPauseMinutes();
    }
}
