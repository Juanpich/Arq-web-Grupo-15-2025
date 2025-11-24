package org.example.parkingdockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ParkingDockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingDockServiceApplication.class, args);
    }

}
