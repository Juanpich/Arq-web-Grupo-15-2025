package org.example.userservice.infraestructure.feign;

import org.example.userservice.models.Journey;
import org.example.userservice.models.PriceJourney;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name="journeys-service", url="http://localhost:8002/journey")
public interface JourneysFeignClient {
    @GetMapping("/byUser/{userId}")
    List<Journey> getJourneyByUser(@PathVariable("userId") Long userId,
                                   @RequestParam("start-date") String startDate,
                                   @RequestParam("end-date") String endDate );
    @GetMapping("/{id}/price")
    PriceJourney getPriceJourneyById(@PathVariable("id") Long idJourney);
}
