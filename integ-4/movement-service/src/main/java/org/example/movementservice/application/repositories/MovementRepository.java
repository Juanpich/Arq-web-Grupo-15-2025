package org.example.movementservice.application.repositories;


import org.example.movementservice.domain.dto.MovementDTO;
import org.example.movementservice.domain.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement,Long> {

}
