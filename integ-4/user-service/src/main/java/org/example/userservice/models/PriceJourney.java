package org.example.userservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.Duration;
@Data
@AllArgsConstructor
@Getter
public class PriceJourney {
    private Long idJourney;
    private Long userId;
    private int kmTraveled;
    private Duration totalHoures;
    private float totalPrice;
    private String totalTime;
}
