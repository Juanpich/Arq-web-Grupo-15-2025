package org.example.journeysservice.application.services;

import org.example.journeysservice.application.repositories.JourneyRepository;
import org.springframework.stereotype.Service;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepo;

    public JourneyService(JourneyRepository journeyRepo) {
        this.journeyRepo = journeyRepo;
    }
}
