package org.example.journeysservice.infraestructure.controllers;

import org.apache.coyote.Response;
import org.example.journeysservice.application.services.JourneyService;
import org.example.journeysservice.domain.dto.*;
import org.example.journeysservice.domain.entities.Journey;
import org.example.journeysservice.domain.exceptions.JourneyNotFoundException;
import org.example.journeysservice.domain.exceptions.ScooterNotAviableException;
import org.example.journeysservice.domain.exceptions.UnfinishedJourneyException;
import org.example.journeysservice.domain.exceptions.UserNotAvailableException;
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

    //consultar todos los viajes. ADMIN
    @GetMapping("")
    public List<JourneyDTO> findAllJourneys() {
        return this.journeyService.findAllJourneys();
    }

    //consultar viaje por Id de viaje. USER
    @GetMapping("/{journeyId}")
    public List<JourneyDTO> findJourneyById(@PathVariable Long journeyId) {
        return this.journeyService.findJourneyById(journeyId);
    }

    //reporte kilometros de uso de monopatines por kilómetros para establecer si un monopatín requiere de mantenimiento. ADMIN
    @GetMapping("/kmByScooter/{kmSearch}")
    public List<ScooterKmReportDTO> scooterKmReport(@PathVariable int kmSearch, @RequestParam(required = true) String includePausedMinutes) {
        return this.journeyService.findAllScooterKm(kmSearch, includePausedMinutes);
    }

    //eliminar viaje. ADMIN
    @DeleteMapping("/{journeyId}")
    public ResponseEntity<?> deleteJourneyById(@PathVariable Long journeyId) {
        this.journeyService.deleteJourneyById(journeyId);
        return ResponseEntity.status(HttpStatus.OK).body("El viaje se eliminó exitosamente");
    }

    //crear viaje. USER
    @PostMapping("")
    public ResponseEntity<?> insertJourney(@RequestBody Journey journey) {
        try{
            var result = this.journeyService.insertJourney(journey);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se creo el viaje (jurney)");
            }
        } catch (UserNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ScooterNotAviableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    //consultar viajes de un monopatin. ADMIN
    @GetMapping("/scooter/{scooter_id}")
    public ResponseEntity<?> FindAllJourneysByScooter(@PathVariable Long scooter_id){
        List<JourneyDTO> result = this.journeyService.FindAllJourneysByScooter(scooter_id);
        return ResponseEntity.ok(result);
    }

    //consultar viaje por ID de usuario. USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findAllJourneysByUser(@PathVariable Long userId){
        List<JourneyDTO> result = this.journeyService.findAllJourneysByUser(userId);
        return ResponseEntity.ok(result);
    }

    //consultar los viajes de un monopatin en determinado año. ADMIN
    @GetMapping("scooter/{id}/year/{anio}")
    public ResponseEntity<?> FindAllJourneysByScooterANDYear(@PathVariable(name="id") Long scooter_id, @PathVariable Integer anio) {
        List<JourneyDTO> result = this.journeyService.FindAllJourneysByScooterANDYear(scooter_id, anio);
        return ResponseEntity.ok(result);
    }

    // consultar viajes entre fechas. ADMIN
    @GetMapping("/user/{userId}/dateRange")
    public ResponseEntity<?> findJourneysByUserInGivenDate(@PathVariable(name="userId") Long userId, @RequestParam LocalDate initDate, @RequestParam LocalDate finishDate,
                                                           @RequestParam(required = true) String includeOtherUsers) {
        DateRangeUserIdDTO result = this.journeyService.findJourneysByDateRange(userId, initDate, finishDate, includeOtherUsers);
        return ResponseEntity.ok(result);
    }

    //actualizar un viaje. ADMIN
    @PutMapping("/{journeyId}")
    public ResponseEntity<?> updateJourney(@RequestBody Journey journey, @PathVariable Long journeyId) {
        var result = this.journeyService.updateJourney(journeyId, journey);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se modifico el viaje de id " + journeyId);
        }
    }

    //los viajes de un usuario entre fechas. USER
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<?> getJourneyByUser(@PathVariable Long userId, @RequestParam(required = true, name="start-date") String startDate
            , @RequestParam(required = true, name="end-date") String endDate) {
        LocalDate startDateN = LocalDate.parse(startDate,  DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDateN = LocalDate.parse(endDate,  DateTimeFormatter.ISO_LOCAL_DATE);
        List<JourneyDTO> journeys = this.journeyService.getJourneyByUser(userId, startDateN, endDateN);
        return ResponseEntity.status(HttpStatus.OK).body(journeys);

    }

    //finalizar un viaje. USER
    @PutMapping("/finishJourney/{id}")
    public ResponseEntity<?> endJourney(@PathVariable(name = "id") Long journeyId) {
        var result = this.journeyService.endJourney(journeyId);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo finalizar el viaje con id " + journeyId);
        }
    }

    //el total facturado en un rango de meses de cierto año. ADMIN
    @GetMapping("/year/{year}/range/{startMonth}/{endMonth}")
    public ResponseEntity<?> findByYearAndMonthRange(@PathVariable(name = "year") int year, @PathVariable(name="startMonth") int startMonth, @PathVariable(name = "endMonth") int endMonth){
        try{
            JourneyPriceDTO total = this.journeyService.findByYearAndMonthRange(year, startMonth, endMonth);
            return ResponseEntity.status(HttpStatus.OK).body(total);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo calcular el total facturado en el rango ingresado.");
        }
    }

    //saber el precio de un viaje. USER
    @GetMapping("/{id}/price")
    public ResponseEntity<?> getPriceByJourneyId(@PathVariable Long id){
        try{
            PriceJourneyDto priceJourneyDto = this.journeyService.getPriceJourney(id);
            return ResponseEntity.status(HttpStatus.OK).body(priceJourneyDto);
        }catch(JourneyNotFoundException | UnfinishedJourneyException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //pausar un viaje. USER
    @PutMapping("/{id}/pause")
    public ResponseEntity<?> pauseJourney(@PathVariable Long id){
        try{
            JourneyDTO journey = this.journeyService.pauseJourney(id);
            return ResponseEntity.status(HttpStatus.OK).body(journey);
        }catch (JourneyNotFoundException | UnfinishedJourneyException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // despausar un viaje. USER
    @PutMapping("/{id}/finishPause")
    public ResponseEntity<?> finishPauseJourney(@PathVariable Long id) {
        try{
            JourneyDTO journey = this.journeyService.finishPause(id);
            return ResponseEntity.status(HttpStatus.OK).body(journey);
        }catch (JourneyNotFoundException | UnfinishedJourneyException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/execute-sql")
    public ResponseEntity<?> executeSqlRequest(@RequestBody String sqlQuery) {
        try {
            System.out.println("=== SQL RECIBIDA ===");
            System.out.println(sqlQuery);
            System.out.println("===================");
            List<Object[]> results = this.journeyService.executeSqlQuery(sqlQuery);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error ejecutando la query SQL " + e.getMessage());
        }
    }
}