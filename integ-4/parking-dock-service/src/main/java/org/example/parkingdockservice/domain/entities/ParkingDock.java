package org.example.parkingdockservice.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingDock_id;
    private String parkingDock_ubication;

    @ElementCollection
    private List<Long> scooters;
}
