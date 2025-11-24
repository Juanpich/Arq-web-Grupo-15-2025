package org.example.parkingdockservice.infraestructure.feingClient;

import org.example.parkingdockservice.infraestructure.models.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="scooter-service", url="http://localhost:8004/scooter")

public interface ScooterFeignClient {
    @GetMapping("scooter/{id}")
    Scooter getSccoterById (
            @PathVariable(name = "id") Long scooter_id);
}