package org.example.parkingdockservice.application.repository;

import org.springframework.stereotype.Repository;

import org.example.parkingdockservice.domain.dto.ParkingDockDTO;
import org.example.parkingdockservice.domain.entities.ParkingDock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingDockRepository extends JpaRepository<ParkingDock, Long> {

    //obtener todos los id de los scooters de la parada
    @Query("SELECT p.scooters FROM ParkingDock p WHERE p.parkingDock_id = :id")
    List<Long> findAllScootersIds(@Param("id") Long id);
}
