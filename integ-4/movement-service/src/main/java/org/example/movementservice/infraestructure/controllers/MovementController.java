package org.example.movementservice.infraestructure.controllers;


import org.example.movementservice.application.services.MovementService;
import org.example.movementservice.domain.dto.MovementDTO;
//import jakarta.validation.Valid;
import org.example.movementservice.domain.entities.Movement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movement")
public class MovementController {

    private final MovementService movementService;
    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping("")
    public List<MovementDTO> findAllMovements(){
        return this.movementService.findAllMovements();
    }

    @GetMapping("/{movementId}")
    public List<MovementDTO> findMovementById(@PathVariable("movementId") Long movementId){
        return this.movementService.findMovementById(movementId);
    }

    @GetMapping("/userId/{userId}")
    public List<MovementDTO> findAllMovementsByUser(@PathVariable int userId){
        return this.movementService.findMovementsByUser(userId);
    }

    @GetMapping("/accountId/{accountId}")
    public List<MovementDTO> findMovementsByAccountId(@PathVariable int accountId){
        return this.movementService.findMovementsByAccount(accountId);
    }

//    @Valid
    @PostMapping("")
    public ResponseEntity<?> createMovement(@RequestBody Movement movementBody){
        final var movementCreated = this.movementService.insert( movementBody );
        if (movementCreated == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se creo el movimiento");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movementCreated);
    }

    @PutMapping("/{movementId}")
    public ResponseEntity<?> updateMovement(@RequestBody Movement movementToUpdate, @PathVariable Long movementId){
        MovementDTO updatedMovementDTO = this.movementService.updateMovement(movementId, movementToUpdate);
        if(updatedMovementDTO == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se modifico el movimiento de id " + movementId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedMovementDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovement(@PathVariable Long movementId) {
        this.movementService.delete(movementId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
