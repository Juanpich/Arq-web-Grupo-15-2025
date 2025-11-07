package org.example.scooterservice.infraestructure.controllers;


import org.example.scooterservice.application.services.ScooterService;
import org.example.scooterservice.domain.dtos.ScooterDto;
import org.example.scooterservice.domain.entities.Scooter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/scooter")
public class ScooterController {
    private ScooterService scooterService;
    public ScooterController(ScooterService scooterService){
        this.scooterService = scooterService;
    }
    @GetMapping("")
    public ResponseEntity<List<ScooterDto>> getAllScooter(){
        List<ScooterDto> scooters = this.scooterService.findAllScooter();
        return ResponseEntity.ok(scooters);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSccoterById(@PathVariable("id") Long id){
        ScooterDto scooterDto = this.scooterService.findScooterById(id);
        if (scooterDto == null) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Scooter with ID " +  id  + " was not found");
        }
        return ResponseEntity.ok(scooterDto);
    }
    @PostMapping("")
    public ResponseEntity<?> createScooter(@RequestBody Scooter scooter){
        ScooterDto scooterDto =  this.scooterService.persistScooter(scooter);
        if(scooterDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Scooter not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scooterDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateScooter(@RequestBody Scooter scooter, @PathVariable Long id){
        ScooterDto scooterDto = this.scooterService.updateScooter(id, scooter);
        if(scooterDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Scooter not updated");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scooterDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScooter(@PathVariable Long id){
        this.scooterService.deleteScooter(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
