package org.example.journeysservice.infraestructure.controllers;

import org.example.journeysservice.application.services.JourneyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journey")
public class JourneyController {

    private final JourneyService journeyService;

    public JourneyController(JourneyService journeyService ) {
        this.journeyService = journeyService;
    }

//    @GetMapping("")
//    public List
}
