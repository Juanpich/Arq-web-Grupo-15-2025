package org.example.journeysservice.application.repositories;


import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

}
