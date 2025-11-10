package org.example.userservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Journey {
    private Long journeyId;

    private Long scooterId;
    private LocalDateTime date;
    private int initHour;
    private int finishHour;
    private int kmTraveled;
    private Long pauseMinutes;
}
