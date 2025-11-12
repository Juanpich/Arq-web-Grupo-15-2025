package org.example.journeysservice.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    //private Long totalHoures;
    private List<DateRangeUserIdDTO> relatedUsers;

    public DateRangeUserIdDTO(Long userId, Long totalScooters, int journeysCount, int totalKm/*, int totalHoures*/) {
        this.userId = userId;
        this.totalScooters = totalScooters;
        this.journeysCount = Long.valueOf(journeysCount);
        this.totalKm = Long.valueOf(totalKm);
        //this.totalHoures = Long.valueOf(totalHoures);
        this.relatedUsers = new ArrayList<DateRangeUserIdDTO>();
    }

    public void setRelatedUsers(ArrayList<DateRangeUserIdDTO> relatedUsers) {
        this.relatedUsers = relatedUsers;
    }
}
