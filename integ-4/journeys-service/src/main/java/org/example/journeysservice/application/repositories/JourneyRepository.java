package org.example.journeysservice.application.repositories;




import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import org.example.journeysservice.domain.dto.ScooterKmReportDTO;

import java.util.List;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {
    @Query("SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) " +
   " FROM Journey j " +
    " WHERE j.userId = :userId " +
     " AND j.date BETWEEN :startDate AND :endDate")

    List<JourneyDTO> getJourneyByUser(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    //Los viajes de un usuario
    @Query(" SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) FROM Journey j WHERE j.userId = :id")
    public List<JourneyDTO> findAllJourneysByUser(@Param("id") Long userId);
    //Los viajes de un scooter
    @Query(" SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) FROM Journey j WHERE j.scooterId = :id")
    public List<JourneyDTO> findAllJourneysByScooter(@Param("id") Long id);

    //Los viajes de un scooter por un anio
    @Query(" SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) FROM Journey j WHERE j.scooterId = :id AND YEAR(j.date) = :anio")
    public List<JourneyDTO> findAllJourneysByScooterByYear(@Param("id") Long id, Integer anio);

    @Query("SELECT new org.example.journeysservice.domain.dto.ScooterKmReportDTO(j.scooterId, " +
                    "CAST(SUM(j.kmTraveled) AS LONG) AS totalKm, " +
            "j.totalHoures , " +
            "CAST(SUM(j.pauseMinutes) AS LONG) AS totalPausedMinutes)" +
            "FROM Journey j " +
            "GROUP BY j.scooterId " +
            "HAVING CAST(SUM(j.kmTraveled) AS INTEGER) >= :kmSearched ")
    List<ScooterKmReportDTO> scooterKmPauseMinutesReport(int kmSearched);

    @Query("SELECT new org.example.journeysservice.domain.dto.ScooterKmReportDTO(j.scooterId, " +
            "CAST(SUM(j.kmTraveled) AS LONG) AS totalKm, " +
            "j.totalHoures ) " +
            "FROM Journey j " +
            "GROUP BY j.scooterId " +
            "HAVING CAST(SUM(j.kmTraveled) AS INTEGER) >= :kmSearched ")
    List<ScooterKmReportDTO> scooterKmReport(int kmSearched);

    @Query("SELECT new org.example.journeysservice.domain.dto.DateRangeUserIdDTO(j.userId, COUNT(DISTINCT j.scooterId), CAST(COUNT(j) AS INTEGER), CAST(SUM(j.kmTraveled) AS INTEGER), CAST(SUM(j.totalHoures) AS INTEGER) ) " +
            "FROM Journey j " +
            "WHERE j.userId = :userId " +
            "AND j.date = :initDate " +
            "AND j.finishDate = :finishDate")
    List<JourneyDTO> findJourneysByDateRange(Long userId, LocalDate initDate, LocalDate finishDate);
}
