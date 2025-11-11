package org.example.journeysservice.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@NoArgsConstructor

public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journeyId;

    private Long scooterId;
    private Long userId;
    private LocalDate date;
    private LocalTime initHour;
    private LocalTime finishHour;
    private int kmTraveled;
    private Long pauseMinutes;
    private LocalTime totalHoures;
    private LocalDate finishDate;

    public Journey(Long scooterId, Long userId, int kmTraveled, Long pauseMinutes) {
        this.scooterId = scooterId;
        this.userId = userId;
        this.finishHour = null;
        this.kmTraveled = kmTraveled;
        this.pauseMinutes = pauseMinutes;
    }

    public Journey(Long journeyId, Long scooterId, Long userId, int kmTraveled, Long pauseMinutes) {
        this.journeyId = journeyId;
        this.userId = userId;
        this.scooterId = scooterId;
        this.date = LocalDate.now();
        this.initHour = LocalTime.now();
        this.finishHour = null;
        this.finishDate = null;
        this.kmTraveled = kmTraveled;
        this.pauseMinutes = pauseMinutes;
    }

    public void calcTotalHoures() {
        if (this.date != null && this.finishDate != null && this.initHour != null && this.finishHour != null) {
            LocalDateTime startDateTime = LocalDateTime.of(this.date, this.initHour);
            LocalDateTime endDateTime = LocalDateTime.of(this.finishDate, this.finishHour);

            long minutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);

            // Convertimos los minutos totales a horas:minutos reales
            long hoursPart = minutes / 60;
            long minutesPart = minutes % 60;

            this.totalHoures = LocalTime.of((int) hoursPart, (int) minutesPart);
        } else {
            this.totalHoures = LocalTime.of(0, 0);
        }
    }


    @PrePersist
    protected void onCreate(){
        this.date = LocalDate.now();
        this.initHour = LocalTime.now();
    }

    public void finishJourney() {
        this.finishHour = LocalTime.now();
        this.finishDate = LocalDate.now();
    }
}
