package org.example.journeysservice.infraestructure.feing;

import org.example.journeysservice.models.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="scooter-service", url="http://localhost:8004/scooter")
public interface ScooterFeignClient {
    @GetMapping("/{id}")
    Scooter getScooterById(@PathVariable("id") Long id);


}
