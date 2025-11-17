package org.example.journeysservice.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DateRangeUserIdDTO {

    private Long userId;
    private Long totalScooters;
    private Long journeysCount;
    private Long totalKm;
    private String totalHoures;
    private List<DateRangeUserIdDTO> relatedUsers;

    public DateRangeUserIdDTO(Long userId, Long totalScooters, int journeysCount, int totalKm, Long totalHoures) {
        this.userId = userId;
        this.totalScooters = totalScooters;
        this.journeysCount = Long.valueOf(journeysCount);
        this.totalKm = Long.valueOf(totalKm);
        this.totalHoures = formatDuration(Duration.ofNanos(totalHoures));
        this.relatedUsers = new ArrayList<DateRangeUserIdDTO>();
    }

    public void setRelatedUsers(ArrayList<DateRangeUserIdDTO> relatedUsers) {
        this.relatedUsers = relatedUsers;
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
