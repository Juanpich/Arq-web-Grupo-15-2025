package org.example.journeysservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ScooterKmReportDTO {

    private Long scooterId;
    private Long kmTraveled;
    private String totalHoures;
    private String pauseMinutes;

    public ScooterKmReportDTO(Long id, Long kmTraveled, Long totalHoures, Long pauseMinutes) {
        this.scooterId = id;
        this.kmTraveled = kmTraveled;
        this.totalHoures = formatDuration(Duration.ofNanos(totalHoures));
        this.pauseMinutes = formatDuration(Duration.ofNanos(pauseMinutes));
    }
    public ScooterKmReportDTO (Long id, Long km, Long totalHoures){
        this.scooterId = id;
        this.kmTraveled = km;
        this.totalHoures = formatDuration(Duration.ofNanos(totalHoures));
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
