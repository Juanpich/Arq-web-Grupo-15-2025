package org.example.journeysservice.application.services;

import org.example.journeysservice.application.repositories.JourneyRepository;
import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepo;

    public JourneyService(JourneyRepository journeyRepo) {
        this.journeyRepo = journeyRepo;
    }

    //Trae todos los viajes.
    @Transactional(readOnly = true)
    public List<JourneyDTO> findAllJourneys() {
        return this.journeyRepo.findAll()
                .stream().map(JourneyDTO::new).toList();
    }

    //Trae un viaje por id.
    @Transactional(readOnly = true)
    public List<JourneyDTO> findJourneyById(Long id) {
        return this.journeyRepo.findById(id)
                .stream().map(JourneyDTO::new).toList();
    }

    //Elimina un viaje.
    @Transactional
    public void deleteJourneyById(Long id) {
        this.journeyRepo.deleteById(id);
    }

    //Insertar un viaje.
    @Transactional
    public JourneyDTO insertJourney(Journey journeyBody) throws IllegalArgumentException {
//        chequear si el id del scooter existe?
        Journey result = this.journeyRepo.save(journeyBody);
        return new JourneyDTO(result);
    }

    //Los viajes de un scooter
    @Transactional
    public List<JourneyDTO> FindAllJourneysByScooter(Long scooter_id) {
        return this.journeyRepo.findAllJourneysByScooter(scooter_id);
    }

    //Los viajes de un scooter en un anio.
    @Transactional
    public List<JourneyDTO> FindAllJourneysByScooterANDYear(Long scooter_id, Integer anio) {
        return this.journeyRepo.findAllJourneysByScooterByYear(scooter_id, anio);
    }
}
