package org.example.parkingdockservice.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private List<Long> scooters;
}
