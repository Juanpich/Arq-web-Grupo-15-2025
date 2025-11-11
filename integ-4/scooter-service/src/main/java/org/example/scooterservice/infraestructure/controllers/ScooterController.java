package org.example.scooterservice.infraestructure.controllers;


import org.example.scooterservice.application.services.ScooterService;
import org.example.scooterservice.domain.dtos.ScooterDto;
import org.example.scooterservice.domain.entities.Scooter;
import org.example.scooterservice.domain.entities.State;
import org.example.scooterservice.domain.exceptions.ScooterNotFoundException;
import org.example.scooterservice.domain.exceptions.ScooterWithIDAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/scooter")
public class ScooterController {
    private ScooterService scooterService;

    public ScooterController(ScooterService scooterService){
        this.scooterService = scooterService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllScooter(@RequestParam(required = false) String state){
        List<ScooterDto> scooters;
        try {
            if (state != null) {
                State stateEnum = State.valueOf(state.toUpperCase());
                scooters = this.scooterService.getAllByState(stateEnum);
            } else {
                scooters = this.scooterService.findAllScooter();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("State invalid");
        }
        if(scooters == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(scooters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSccoterById(@PathVariable("id") Long id) {
        try{
            ScooterDto scooterDto = this.scooterService.findScooterById(id);
            return ResponseEntity.ok(scooterDto);
        }catch(ScooterNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createScooter(@RequestBody Scooter scooter){
        try{
            ScooterDto scooterDto =  this.scooterService.persistScooter(scooter);
            return ResponseEntity.status(HttpStatus.CREATED).body(scooterDto);
        }catch (ScooterWithIDAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateScooter(@RequestBody Scooter scooter, @PathVariable Long id){
        try{
            ScooterDto scooterDto = this.scooterService.updateScooter(id, scooter);
            return ResponseEntity.status(HttpStatus.CREATED).body(scooterDto);
        }catch (ScooterNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("{id}/changeState")
    public ResponseEntity<?>  changeState(@RequestBody Map<String, String> body, @PathVariable Long id){
        ScooterDto scooter;
        try{
            String state = body.get("state");
            State stateEnum = State.valueOf(state.toUpperCase());
            scooter = this.scooterService.changeState(stateEnum, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(scooter);
        }catch (ScooterNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("State invalid" );
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScooter(@PathVariable Long id){
        this.scooterService.deleteScooter(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    //los monopatines con más de X viajes en un cierto año.
    @GetMapping("/year/{anio}/countJourneys/{count}")
    public ResponseEntity<?> scootersForYear(@PathVariable Integer anio, @PathVariable Integer count){
        try{
            List<ScooterDto> result = this.scooterService.scootersForYear(anio, count);
            return ResponseEntity.ok(result);
        }catch (ScooterNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
