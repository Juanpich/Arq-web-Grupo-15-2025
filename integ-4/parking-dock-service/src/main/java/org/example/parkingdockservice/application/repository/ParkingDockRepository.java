package org.example.parkingdockservice.application.repository;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingDockRepository {
=======
import org.example.parkingdockservice.domain.dto.ParkingDockDTO;
import org.example.parkingdockservice.domain.entities.ParkingDock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingDockRepository extends JpaRepository<ParkingDock, Long> {

    //obtener todos los id de los scooters de la parada
    @Query("SELECT scooters FROM ParkingDock ")
    List<Long> findAllScootersIds();
>>>>>>> a1a2112804e417048cc4de7f5d1e07aef6b65b71
}
