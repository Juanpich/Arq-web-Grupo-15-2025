package org.example.journeysservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JourneysServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JourneysServiceApplication.class, args);
    }

}
