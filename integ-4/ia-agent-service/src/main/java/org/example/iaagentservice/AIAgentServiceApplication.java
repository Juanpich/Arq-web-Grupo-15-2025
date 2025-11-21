package org.example.iaagentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AIAgentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AIAgentServiceApplication.class, args);
    }

}
