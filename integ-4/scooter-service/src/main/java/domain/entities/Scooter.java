package org.example.scooterservice.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "scooter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter{
    @Id
    private Long scooter_id;
    private enum state{
        AVAILABLE, UNAVAILABLE, IN_USE
    };
    private String gps;
    private Long parkingDock_id;

}