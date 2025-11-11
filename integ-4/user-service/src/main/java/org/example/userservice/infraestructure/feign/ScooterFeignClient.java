package org.example.userservice.infraestructure.feign;

import org.example.userservice.models.Journey;
import org.example.userservice.models.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="scooter-service", url="http://localhost:8004/scooter")
public interface ScooterFeignClient {
    @GetMapping("/gps/{gps}")
    List<Scooter> getScooterByGps(@PathVariable("gps") String gps);
}

