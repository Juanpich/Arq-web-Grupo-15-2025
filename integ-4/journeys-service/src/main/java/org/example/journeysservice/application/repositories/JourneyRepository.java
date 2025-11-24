package org.example.journeysservice.application.repositories;

import org.example.journeysservice.domain.dto.DateRangeUserIdDTO;
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

    @Query("SELECT new org.example.journeysservice.domain.dto.ScooterKmReportDTO( j.scooterId, " +
        "CAST(SUM(j.kmTraveled)AS LONG) ,  CAST(SUM(COALESCE(j.totalHoures, 0)) AS long), " +
            "CAST(SUM(COALESCE(j.totalMinutespause, 0)) AS long)) " +
    "FROM Journey j " +
   " GROUP BY j.scooterId " +
    "HAVING SUM(j.kmTraveled) >= :kmSearched")
    List<ScooterKmReportDTO> scooterKmPauseMinutesReport(@Param("kmSearched") int kmSearched);



    @Query("SELECT new org.example.journeysservice.domain.dto.ScooterKmReportDTO(" +
            "j.scooterId, " +
            "CAST(SUM(j.kmTraveled) AS long), " +
            "CAST(SUM(COALESCE(j.totalHoures, 0)) AS long)) " +
            "FROM Journey j " +
            "GROUP BY j.scooterId " +
            "HAVING CAST(SUM(j.kmTraveled) AS int) >= :kmSearched")
    List<ScooterKmReportDTO> scooterKmReport(@Param("kmSearched") int kmSearched);




    //Todos los viajes que estan entre dos meses
    @Query("SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) FROM Journey j " +
            "WHERE YEAR(j.date) = :year " +
            "AND MONTH(j.date) BETWEEN :startMonth AND :endMonth")
    List<JourneyDTO> findByYearAndMonthRange(
            @Param("year") int year,
            @Param("startMonth") int startMonth,
            @Param("endMonth") int endMonth);


    @Query("SELECT new org.example.journeysservice.domain.dto.DateRangeUserIdDTO(" +
            "j.userId, " +
            "COUNT(DISTINCT j.scooterId), " +
            "CAST(COUNT(j) AS INTEGER), " +
            "CAST(SUM(j.kmTraveled) AS INTEGER), " +
            "CAST(SUM(COALESCE(j.totalHoures, 0)) AS long)) " +
            "FROM Journey j " +
            "WHERE j.userId = :userId " +
            "AND j.date >= :initDate " +
            "AND j.finishDate <= :finishDate " +
            "GROUP BY j.userId")
    DateRangeUserIdDTO findJourneysByDateRange(Long userId, LocalDate initDate, LocalDate finishDate);

    @Query(value = ":sqlQuery", nativeQuery = true)
    List<Object[]> executeSqlQuery(String sqlQuery);
}
