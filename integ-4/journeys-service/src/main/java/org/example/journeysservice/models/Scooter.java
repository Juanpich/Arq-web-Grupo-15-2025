package org.example.journeysservice.models;

import lombok.Data;

@Data
public class Scooter {
    private Long scooter_id;
    private String state;
    private String gps;
    private Long parkingDock_id;
}
