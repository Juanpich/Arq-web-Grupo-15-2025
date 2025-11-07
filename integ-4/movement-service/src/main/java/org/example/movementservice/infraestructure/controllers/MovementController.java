package org.example.movementservice.infraestructure.controllers;


import org.example.movementservice.application.services.MovementService;
import org.example.movementservice.domain.dto.MovementDTO;
import org.springframework.beans.factory.annotation.Autowired;
//import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movement")
public class MovementController {

    @Autowired
    MovementService movementService;

    @GetMapping("")
    public List<MovementDTO> findAllMovements(){
        return this.movementService.findAllMovements();
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
    public ResponseEntity<MovementDTO> matricularEstudiante(@RequestBody MovementDTO movementDTObody){
        final var result = this.movementService.insert( movementDTObody );
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }
}
