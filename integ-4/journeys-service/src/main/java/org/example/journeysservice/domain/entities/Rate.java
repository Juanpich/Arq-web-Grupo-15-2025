package org.example.journeysservice.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rate_id;

    @Column(nullable = false)
    private LocalDateTime init_date;

    private LocalDateTime finish_date;

    @Column(nullable = false)
    private Float price;

    public Rate(LocalDateTime init_date, LocalDateTime finish_date, Float price) {
        this.init_date = init_date;
        this.finish_date = finish_date;
        this.price = price;
    }
}

