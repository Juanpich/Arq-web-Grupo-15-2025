package org.example.journeysservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.journeysservice.domain.entities.Journey;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class JourneyDTO {

    private Long journeyId;
    private Long userId;
    private Long scooterId;
    private LocalDate date;
    private LocalTime initHour;
    private LocalTime finishHour;
    private int kmTraveled;
    private String pauseMinutes;
    private LocalDate finishDate;
    private LocalTime init_pause;
    private LocalTime finish_pause;
    private Duration totalMinutespause;

    @JsonIgnore
    private Duration totalHoures;
    @JsonProperty("totalHoures")
    private String journeyDuration;

    public JourneyDTO(Journey newjourney) {
        this.journeyId = newjourney.getJourneyId();
        this.userId = newjourney.getUserId();
        this.scooterId = newjourney.getScooterId();
        this.date = newjourney.getDate();
        this.initHour = newjourney.getInitHour();
        this.finishHour = newjourney.getFinishHour();
        this.kmTraveled = newjourney.getKmTraveled();
        this.pauseMinutes = newjourney.getPauseMinutes();
        this.finishDate = newjourney.getFinishDate();
        this.totalHoures = newjourney.getTotalHoures();
        this.journeyDuration = this.getFormattedTotalHours();
        this.init_pause = newjourney.getInit_pause();
        this.finish_pause = newjourney.getFinish_pause();
        this.totalMinutespause = newjourney.getTotalMinutespause();
    }
    //Totalhoures guarda la duracion del viaje en nanosegundos por lo que
    // se lo quiere mostrar al usuario de una manera mas amigable.
    @JsonIgnore
    public String getFormattedTotalHours() {
        if (totalHoures == null) return null;

        long hours = totalHoures.toHours();
        long minutes = totalHoures.toMinutesPart();
        long seconds = totalHoures.toSecondsPart();
        long nanos = totalHoures.toNanosPart();

        return String.format("%02d:%02d:%02d:%07d", hours, minutes, seconds, nanos);
    }

}
