package org.example.journeysservice.application.services;

import org.example.journeysservice.application.repositories.JourneyRepository;
import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.dto.ScooterKmReportDTO;
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

    @Transactional(readOnly = true)
    public List<ScooterKmReportDTO> findAllScooterKm(int kmSearch, String includePausedMinutes) {
        if (includePausedMinutes == "include") {
            return this.journeyRepo.scooterKmPauseMinutesReport(kmSearch);
        } else {
            return this.journeyRepo.scooterKmReport(kmSearch);
        }
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

    @Transactional
    public JourneyDTO updateJourney(Long journeyId, Journey journeyBody) throws IllegalArgumentException {
        Journey oldJourney = this.journeyRepo.findById(journeyId).orElseThrow(() -> new RuntimeException("No se encontro el movimiento con id " + journeyId));
        oldJourney.setScooterId(journeyBody.getScooterId());
        oldJourney.setDate(journeyBody.getDate());
        oldJourney.setInitHour(journeyBody.getInitHour());
        oldJourney.setFinishHour(journeyBody.getFinishHour());
        oldJourney.setKmTraveled(journeyBody.getKmTraveled());
        oldJourney.setPauseMinutes(journeyBody.getPauseMinutes());

        Journey upadtedJourney = this.journeyRepo.save(oldJourney);
        return new JourneyDTO(upadtedJourney);
    }
}
