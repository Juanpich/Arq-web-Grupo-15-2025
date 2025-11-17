package org.example.journeysservice.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
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
    private String pauseMinutes;
    private Duration totalHoures;
    private LocalDate finishDate;
    private LocalTime init_pause;
    private LocalTime finish_pause;
    private Duration totalMinutespause;

    public Journey(Long scooterId, Long userId, int kmTraveled) {
        this.scooterId = scooterId;
        this.userId = userId;
        this.finishHour = null;
        this.kmTraveled = kmTraveled;
        this.pauseMinutes = null;
        this.totalHoures = null;
        this.init_pause = null;
        this.finish_pause = null;
        this.totalMinutespause = null;
    }

    public Journey(Long journeyId, Long scooterId, Long userId, int kmTraveled) {
        this.journeyId = journeyId;
        this.userId = userId;
        this.scooterId = scooterId;
        this.date = LocalDate.now();
        this.initHour = LocalTime.now();
        this.finishHour = null;
        this.finishDate = null;
        this.kmTraveled = kmTraveled;
        this.pauseMinutes = null;
        this.totalHoures = null;
        this.init_pause = null;
        this.finish_pause = null;
        this.totalMinutespause = null;
    }

    public void calcTotalHoures() {
        if (this.date != null && this.finishDate != null && this.initHour != null && this.finishHour != null) {
            LocalDateTime start = LocalDateTime.of(this.date, this.initHour);
            LocalDateTime end = LocalDateTime.of(this.finishDate, this.finishHour);
            this.totalHoures = Duration.between(start, end);
        } else {
            this.totalHoures = Duration.ZERO;
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
        this.calcTotalHoures();
    }

    public void initPause() {
        if(this.finishHour == null && this.finishDate == null) {
            this.init_pause = LocalTime.now();
        }
    }

    public void finishPause(){
        if(this.init_pause != null && this.finishHour == null && this.finishDate == null){
            this.finish_pause = LocalTime.now();
            this.totalMinutespause = Duration.between(this.init_pause, this.finish_pause);
            this.pauseMinutes = formatDuration(totalMinutespause);
        }

    }

    public static String formatDuration(Duration duration) {
        if(duration == null){
            return null;
        }
        long seconds = duration.getSeconds();

        long days = seconds / (24 * 3600);
        seconds %= (24 * 3600);

        long hours = seconds / 3600;
        seconds %= 3600;

        long minutes = seconds / 60;
        seconds %= 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append(" días ");
        if (hours > 0) sb.append(hours).append(" horas ");
        if (minutes > 0) sb.append(minutes).append(" minutos ");
        if (seconds > 0) sb.append(seconds).append(" segundos");

        return sb.toString().trim();
    }
}
