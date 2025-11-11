package org.example.journeysservice.domain.dto;

import lombok.*;
import org.example.journeysservice.domain.entities.Rate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RateDto {
    private Long rate_id;
    private LocalDateTime init_date;
    private LocalDateTime finish_date;
    private Float price;

    public RateDto(Rate rate) {
        this.rate_id = rate.getRate_id();
        this.init_date = rate.getInit_date();
        this.finish_date = rate.getFinish_date();
        this.price = rate.getPrice();
    }
}

