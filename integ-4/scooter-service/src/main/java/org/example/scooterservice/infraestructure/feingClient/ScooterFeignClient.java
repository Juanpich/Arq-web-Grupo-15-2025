package org.example.scooterservice.infraestructure.feingClient;

import org.example.scooterservice.domain.dtos.ScooterDto;
import org.example.scooterservice.infraestructure.models.Journey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="scooter-service", url="http://localhost:8005/scooter")
public interface ScooterFeignClient {
    @GetMapping("/{id}")
    ScooterDto getSccoterById (
                @PathVariable(name = "id") Long scooter_id);
}
