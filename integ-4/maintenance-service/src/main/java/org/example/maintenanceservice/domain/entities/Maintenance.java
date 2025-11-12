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
    private Long scooterId;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private LocalDateTime init_date;

    @Column
    private LocalDateTime finishDate;

    public Maintenance(Long scooter_id, Long user_id, LocalDateTime init_date, LocalDateTime finish_date) {
        this.scooterId = scooter_id;
        this.user_id = user_id;
        this.init_date = init_date;
        this.finishDate = finish_date;
    }
}

