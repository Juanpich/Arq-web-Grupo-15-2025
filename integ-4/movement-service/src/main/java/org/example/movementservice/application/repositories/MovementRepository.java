package org.example.movementservice.application.repositories;


import org.example.movementservice.domain.dto.MovementDTO;
import org.example.movementservice.domain.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement,Long> {

    @Query("SELECT new org.example.movementservice.domain.entities.Movement(m) " +
            "FROM Movement m " +
            "WHERE m.userId = :userIdParam")
    public List<Movement> findMovementsByUser(int userIdParam);

    @Query("SELECT new org.example.movementservice.domain.entities.Movement(m) " +
            "FROM Movement m " +
            "WHERE m.accountId = :accountId")
    public List<Movement> findMovementsByAccount(int accountId);
}
