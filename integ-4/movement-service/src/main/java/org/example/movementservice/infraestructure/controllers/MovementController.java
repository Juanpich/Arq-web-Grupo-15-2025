package org.example.movementservice.infraestructure.controllers;


import org.example.movementservice.application.services.MovementService;
import org.example.movementservice.domain.dto.MovementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
