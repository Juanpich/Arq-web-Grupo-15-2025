package org.example.journeysservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JourneyPriceDTO {

    private int startDate;
    private int finishDate;
    private int anio;
    private Float price;
}

