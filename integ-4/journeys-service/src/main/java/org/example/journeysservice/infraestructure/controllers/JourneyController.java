package org.example.journeysservice.infraestructure.controllers;

import org.apache.coyote.Response;
import org.example.journeysservice.application.services.JourneyService;
import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journey")
public class JourneyController {

    private final JourneyService journeyService;

    public JourneyController(JourneyService journeyService ) {
        this.journeyService = journeyService;
    }

    @GetMapping("")
    public List<JourneyDTO> findAllJourneys() {
        return this.journeyService.findAllJourneys();
    }

    @GetMapping("/{journeyId}")
    public List<JourneyDTO> findJourneyById(@PathVariable Long journeyId) {
        return this.journeyService.findJourneyById(journeyId);
    }

    @DeleteMapping("/{journeyId}")
    public ResponseEntity<?> deleteJourneyById(@PathVariable Long journeyId) {
        this.journeyService.deleteJourneyById(journeyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("")
    public ResponseEntity<?> insertJourney(@RequestBody Journey journey) {
        var result = this.journeyService.insertJourney(journey);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se creo el viaje (jurney)");
        }
    }
}
