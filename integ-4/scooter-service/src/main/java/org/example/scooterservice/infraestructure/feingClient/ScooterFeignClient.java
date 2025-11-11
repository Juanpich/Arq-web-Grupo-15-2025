package org.example.scooterservice.infraestructure.feingClient;

import org.example.scooterservice.infraestructure.models.Journey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="journeys-service", url="http://localhost:8002/journey")
public interface ScooterFeignClient {
    @GetMapping("scooter/{id}/year/{anio}")
    List<Journey> FindAllJourneysByScooterANDYear (
                @PathVariable(name = "id") Long scooter_id,
                @PathVariable(name = "anio") Integer anio);
}
