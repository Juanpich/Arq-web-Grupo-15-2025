package org.example.journeysservice.domain.dto;

import lombok.Data;

import java.time.Duration;
@Data
public class PriceJourneyDto {
    private Long idJourney;
    private Long userId;
    private int kmTraveled;
    private Duration totalHoures;
    private float totalPrice;
    private String totalTime;

    public PriceJourneyDto(Long idJourney, Long userId, int kmTraveled, Duration totalHoures, float totalPriceJourney) {
        this.idJourney = idJourney;
        this.userId = userId;
        this.kmTraveled = kmTraveled;
        this.totalPrice = totalPriceJourney;
        this.totalHoures = totalHoures;
        this.totalTime = formatDuration(totalHoures);
    }
    public static String formatDuration(Duration duration) {
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
