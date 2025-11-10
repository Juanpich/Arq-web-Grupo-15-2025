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
<<<<<<< HEAD
    private Long parkingDOck_id;
=======
    private Long parkingDock_id;
>>>>>>> a1a2112804e417048cc4de7f5d1e07aef6b65b71
    private String parkingDock_ubication;
    private List<Long> scooters;
}
