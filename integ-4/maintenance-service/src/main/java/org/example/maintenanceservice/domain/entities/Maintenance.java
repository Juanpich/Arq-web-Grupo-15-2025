package org.example.maintenanceservice.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenance_id;

    @Column(nullable = false)
    private Long scooter_id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private LocalDateTime init_date;

    @Column
    private LocalDateTime finish_date;

    public Maintenance(Long scooter_id, Long user_id, LocalDateTime init_date, LocalDateTime finish_date) {
        this.scooter_id = scooter_id;
        this.user_id = user_id;
        this.init_date = init_date;
        this.finish_date = finish_date;
    }
}

