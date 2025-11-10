package org.example.journeysservice.application.repositories;


import org.example.journeysservice.domain.dto.JourneyDTO;
import org.example.journeysservice.domain.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {

    //Los viajes de un scooter
    @Query(" SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) FROM Journey j WHERE j.scooterId = :id")
    public List<JourneyDTO> findAllJourneysByScooter(@Param("id") Long id);

    //Los viajes de un scooter por un anio
    @Query(" SELECT new org.example.journeysservice.domain.dto.JourneyDTO(j) FROM Journey j WHERE j.scooterId = :id AND YEAR(j.date) = :anio")
    public List<JourneyDTO> findAllJourneysByScooterByYear(@Param("id") Long id, Integer anio);

}
