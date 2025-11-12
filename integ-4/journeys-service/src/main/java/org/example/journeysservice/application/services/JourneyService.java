package org.example.journeysservice.application.services;

import org.example.journeysservice.application.repositories.JourneyRepository;
import org.example.journeysservice.application.repositories.RateRepository;
import org.example.journeysservice.domain.dto.DateRangeUserIdDTO;
import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.dto.JourneyPriceDTO;
import org.example.journeysservice.domain.dto.RateDto;
import org.example.journeysservice.domain.dto.ScooterKmReportDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.example.journeysservice.infraestructure.feing.AccountFeingClient;
import org.example.journeysservice.models.Account;
import org.example.journeysservice.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepo;

    private final RateRepository rateRepo;
    private AccountFeingClient accountFeingClient;

    public JourneyService(JourneyRepository journeyRepo, RateRepository rateRepo, AccountFeingClient accountFeingClient) {
        this.journeyRepo = journeyRepo;
        this.rateRepo = rateRepo;
        this.accountFeingClient = accountFeingClient;
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
        Journey journeyNew = this.journeyRepo.save(journey);
        return new JourneyDTO(journeyNew);
    }

    //Los viajes de un rango.
    public JourneyPriceDTO findByYearAndMonthRange(int year, int startMonth, int endMonth) {
        //Los viajes de un rango y anio.
        List<JourneyDTO> journeys = this.journeyRepo.findByYearAndMonthRange(year, startMonth, endMonth);
        Float totalPriceJourneys = 0.f;

        for (JourneyDTO journey : journeys) {
            // Convertir las fechas + horas del viaje a LocalDateTime
            LocalDateTime journeyStart = LocalDateTime.of(journey.getDate(), journey.getInitHour());
            LocalDateTime journeyEnd = LocalDateTime.of(journey.getFinishDate(), journey.getFinishHour());

            // Buscar rates que afectan al viaje
            List<RateDto> rates = this.rateRepo.findRatesForRange(journeyStart, journeyEnd);

            float totalPriceJourney = 0f;

            for (RateDto rate : rates) {
                // Determinar la parte del rate que se solapa con el viaje
                LocalDateTime overlapStart = journeyStart.isAfter(rate.getInit_date()) ? journeyStart : rate.getInit_date();
                LocalDateTime overlapEnd = journeyEnd.isBefore(rate.getFinish_date()) ? journeyEnd : rate.getFinish_date();

                Duration duration = Duration.between(overlapStart, overlapEnd);
                float hours = duration.toMinutes() / 60f;

                if (hours > 0) {
                    totalPriceJourney += hours * rate.getPrice();
                }

            }
            totalPriceJourneys += totalPriceJourney;
        }
        return new JourneyPriceDTO(startMonth, endMonth, year, totalPriceJourneys);
    }

    public DateRangeUserIdDTO findJourneysByDateRange(Long userId, LocalDate initDate, LocalDate finishDate, String includeOtherUsers) {

        //si se quiere incluir la info de los usuarios de misma cuenta, entra en el if
        if (includeOtherUsers.equals("include")) {

            //dto principal (de el usuario principal)
            DateRangeUserIdDTO mainUser = this.journeyRepo.findJourneysByDateRange(userId, initDate, finishDate);

            //cuentas del usuario
            List<Account> relatedAccounts = this.accountFeingClient.getAccountsByUser(userId);

            // usuarios de todas las cuentas
            ArrayList<List<User>> relatedUsers = new ArrayList<>();
            for (Account account : relatedAccounts) {
                relatedUsers.add(this.accountFeingClient.getUsersByAccountId(account.getAccount_id()));
            }

            // lista de usuarios para que no se repitan (guarda el id de cada usuario)
            ArrayList<Long> visitedUsersId = new ArrayList<>();

            // genera el dto con la info de cada usuario relacionado al principal
            ArrayList<DateRangeUserIdDTO> relatedUsersInfo = new ArrayList<>();

            for (List<User> userList : relatedUsers) {
                for (User relatedUser : userList) {

                    if (!visitedUsersId.contains(relatedUser.getId())) {
                        visitedUsersId.add(relatedUser.getId());
                        relatedUsersInfo.add(this.journeyRepo.findJourneysByDateRange(relatedUser.getId(), initDate, finishDate));
                    }
                }
            }
            if(mainUser == null){
                return null;
            }
            mainUser.setRelatedUsers(relatedUsersInfo);
            return mainUser;
        } else {
            return this.journeyRepo.findJourneysByDateRange(userId, initDate, finishDate);
        }

    }
}

