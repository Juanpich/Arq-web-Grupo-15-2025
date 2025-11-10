package org.example.journeysservice.application.services;

import org.example.journeysservice.application.repositories.JourneyRepository;
import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepo;

    public JourneyService(JourneyRepository journeyRepo) {
        this.journeyRepo = journeyRepo;
    }

    @Transactional(readOnly = true)
    public List<JourneyDTO> findAllJourneys() {
        return this.journeyRepo.findAll()
                .stream().map(JourneyDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<JourneyDTO> findJourneyById(Long id) {
        return this.journeyRepo.findById(id)
                .stream().map(JourneyDTO::new).toList();
    }

    @Transactional
    public void  deleteJourneyById(Long id) {
        this.journeyRepo.deleteById(id);
    }

    @Transactional
    public JourneyDTO insertJourney(Journey journeyBody) throws IllegalArgumentException {
//        chequear si el id del scooter existe?
        Journey result = this.journeyRepo.save(journeyBody);
        return new JourneyDTO(result);
    }
}
