package org.example.maintenanceservice.infrastructure.feing;

import org.example.maintenanceservice.domain.models.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "scooter-service", url = "http://localhost:8004/scooter")
public interface ScooterFeignClient {

    @PutMapping("/{id}/changeState")
    void updateScooterState(@PathVariable(name = "id") Long scooterId,
                            @RequestBody Map<String, String> body);
    @GetMapping("/{id}")
    Scooter getScooterById(@PathVariable("id") Long id);
}