package org.example.journeysservice.application.services;

import org.example.journeysservice.application.repositories.JourneyRepository;
import org.example.journeysservice.domain.dto.DateRangeUserIdDTO;
import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.dto.ScooterKmReportDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Transactional(readOnly = true)
    public List<ScooterKmReportDTO> findAllScooterKm(int kmSearch, String includePausedMinutes) {
        if (includePausedMinutes.equals("include")) {
            return this.journeyRepo.scooterKmPauseMinutesReport(kmSearch);
        } else {
            return this.journeyRepo.scooterKmReport(kmSearch);
        }
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

    // viajes por usuario
    @Transactional
    public List<JourneyDTO> findAllJourneysByUser(Long userId) {
        return this.journeyRepo.findAllJourneysByUser(userId);
    }

    //Los viajes de un scooter en un anio.
    @Transactional
    public List<JourneyDTO> FindAllJourneysByScooterANDYear(Long scooter_id, Integer anio) {
        return this.journeyRepo.findAllJourneysByScooterByYear(scooter_id, anio);
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
    @Transactional(readOnly = true)
    public List<JourneyDTO> getJourneyByUser(Long userId, LocalDate startDate, LocalDate endDate) {
        return this.journeyRepo.getJourneyByUser(userId, startDate, endDate);
    }
    public Object endJourney(Long journeyId) {
        Journey journey = this.journeyRepo.findById(journeyId).orElseThrow(() -> new RuntimeException("No se encontro el viaje con id " + journeyId));
        journey.finishJourney();
        return new JourneyDTO(journey);
    }

    public List<JourneyDTO> findJourneysByDateRange(Long userId, LocalDate initDate, LocalDate finishDate) {
        return this.journeyRepo.findJourneysByDateRange(userId, initDate, finishDate);
    }
}
