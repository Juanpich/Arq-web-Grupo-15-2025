package org.example.scooterservice.infraestructure.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Journey {
    private Long journeyId;

    private Long scooterId;
    private LocalDate date;
    private LocalTime initHour;
    private LocalTime finishHour;
    private int kmTraveled;
    private Long pauseMinutes;
}
