package org.example.parkingdockservice.infraestructure.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Scooter {
    private Long scooter_id;
    private String state;
    private String gps;
    private Long parkingDock_id;

}
