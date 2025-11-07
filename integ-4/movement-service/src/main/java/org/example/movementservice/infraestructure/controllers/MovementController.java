package org.example.movementservice.infraestructure.controllers;


import org.example.movementservice.application.services.MovementService;
import org.example.movementservice.domain.dto.MovementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
