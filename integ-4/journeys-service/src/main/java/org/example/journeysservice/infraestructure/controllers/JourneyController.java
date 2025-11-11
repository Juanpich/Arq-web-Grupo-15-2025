package org.example.journeysservice.infraestructure.controllers;

import org.apache.coyote.Response;
import org.example.journeysservice.application.services.JourneyService;
import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.dto.ScooterKmReportDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/journey")
public class JourneyController {

    private final JourneyService journeyService;

    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    //Consultar viajes
    @GetMapping("")
    public List<JourneyDTO> findAllJourneys() {
        return this.journeyService.findAllJourneys();
    }

    //Consultar viaje por Id.
    @GetMapping("/{journeyId}")
    public List<JourneyDTO> findJourneyById(@PathVariable Long journeyId) {
        return this.journeyService.findJourneyById(journeyId);
    }

    //Reporte kilometros.
    @GetMapping("/kmByScooter/{kmSearch}")
    public List<ScooterKmReportDTO> scooterKmReport(@PathVariable int kmSearch, @RequestParam(required = true) String includePausedMinutes) {
        return this.journeyService.findAllScooterKm(kmSearch, includePausedMinutes);
    }

    //Eliminar viaje.
    @DeleteMapping("/{journeyId}")
    public ResponseEntity<?> deleteJourneyById(@PathVariable Long journeyId) {
        this.journeyService.deleteJourneyById(journeyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Crear viaje.
    @PostMapping("")
    public ResponseEntity<?> insertJourney(@RequestBody Journey journey) {
        var result = this.journeyService.insertJourney(journey);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se creo el viaje (jurney)");
        }
    }
    
    //Consultar viaje por monopatin.
    @GetMapping("/scooter/{scooter_id}")
    public ResponseEntity<?> FindAllJourneysByScooter(@PathVariable Long scooter_id){
        List<JourneyDTO> result = this.journeyService.FindAllJourneysByScooter(scooter_id);
        return ResponseEntity.ok(result);
    }

    //Consultar viaje por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findAllJourneysByUser(@PathVariable Long userId){
        List<JourneyDTO> result = this.journeyService.findAllJourneysByUser(userId);
        return ResponseEntity.ok(result);
    }

    //Consultar viaje por monopatin en determinado año.
    // journey/scooter/2/year/2025
    @GetMapping("scooter/{id}/year/{anio}")
    public ResponseEntity<?> FindAllJourneysByScooterANDYear(@PathVariable(name="id") Long scooter_id, @PathVariable Integer anio) {
        List<JourneyDTO> result = this.journeyService.FindAllJourneysByScooterANDYear(scooter_id, anio);
        return ResponseEntity.ok(result);
    }

    //Actualizar un viaje.
    @PutMapping("/{journeyId}")
    public ResponseEntity<?> updateJourney(@RequestBody Journey journey, @RequestParam Long journeyId) {
        var result = this.journeyService.updateJourney(journeyId, journey);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se modifico el viaje de id " + journeyId);
        }
    }
    //Los viajes de un usuario entre fechas.
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<?> getJourneyByUser(@PathVariable Long userId, @RequestParam(required = true, name="start-date") String startDate
            , @RequestParam(required = true, name="end-date") String endDate) {
        LocalDate startDateN = LocalDate.parse(startDate,  DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDateN = LocalDate.parse(endDate,  DateTimeFormatter.ISO_LOCAL_DATE);
        List<JourneyDTO> journeys = this.journeyService.getJourneyByUser(userId, startDateN, endDateN);
        return ResponseEntity.status(HttpStatus.OK).body(journeys);

    }
    //Finalizar un viaje.
    @PutMapping("/finishJourney/{id}")
    public ResponseEntity<?> endJourney(@PathVariable(name = "id") Long journeyId) {
        var result = this.journeyService.endJourney(journeyId);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo finalizar el viaje con id " + journeyId);
        }
    }
}
