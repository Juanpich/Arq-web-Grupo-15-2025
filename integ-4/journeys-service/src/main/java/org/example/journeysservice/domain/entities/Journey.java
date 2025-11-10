package org.example.journeysservice.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime initHour;
    private LocalDateTime finishHour;
    private int kmTraveled;
    private Long pauseMinutes;
    private int totalHoures;

    public Journey(Long scooterId, Long userId, int kmTraveled, Long pauseMinutes) {
        this.scooterId = scooterId;
        this.userId = userId;
        this.date = LocalDate.now();
        this.initHour = LocalDateTime.now();
        this.finishHour = null;
        this.kmTraveled = kmTraveled;
        this.pauseMinutes = pauseMinutes;
        this.totalHoures = 0;
    }

    public Journey(Long journeyId, Long scooterId, Long userId, int kmTraveled, Long pauseMinutes) {
        this.journeyId = journeyId;
        this.userId = userId;
        this.scooterId = scooterId;
        this.date = LocalDate.now();
        this.initHour = LocalDateTime.now();
        this.finishHour = null;
        this.kmTraveled = kmTraveled;
        this.pauseMinutes = pauseMinutes;
        this.totalHoures = 0;
    }
}
