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
    @Query("""
    SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j)
    FROM Journey j
    WHERE j.userId = :userId
      AND j.date BETWEEN :startDate AND :endDate
""")
    List<JourneyDTO> getJourneyByUser(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    //Los viajes de un scooter
    @Query(" SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) FROM Journey j WHERE j.scooterId = :id")
    public List<JourneyDTO> findAllJourneysByScooter(@Param("id") Long id);

    //Los viajes de un scooter por un anio
    @Query(" SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) FROM Journey j WHERE j.scooterId = :id AND YEAR(j.date) = :anio")
    public List<JourneyDTO> findAllJourneysByScooterByYear(@Param("id") Long id, Integer anio);


    @Query("SELECT new org.example.journeysservice.domain.dto.ScooterKmReportDTO(j.scooterId, " +
                    "CAST(SUM(j.kmTraveled) AS INTEGER) AS totalKm, " +
            "j.totalHoures , " +
            "CAST(SUM(j.pauseMinutes) AS LONG) AS totalPausedMinutes)" +
            "FROM Journey j " +
            "GROUP BY j.scooterId " +
            "HAVING CAST(SUM(j.kmTraveled) AS INTEGER) >= :kmSearched ")
    List<ScooterKmReportDTO> scooterKmPauseMinutesReport(int kmSearched);

    @Query("SELECT new org.example.journeysservice.domain.dto.ScooterKmReportDTO(j.scooterId, " +
            "CAST(SUM(j.kmTraveled) AS INTEGER) AS totalKm, " +
            "j.totalHoures ) " +
            "FROM Journey j " +
            "GROUP BY j.scooterId " +
            "HAVING CAST(SUM(j.kmTraveled) AS INTEGER) >= :kmSearched ")
    List<ScooterKmReportDTO> scooterKmReport(int kmSearched);

}
