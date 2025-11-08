package org.example.movementservice.application.services;


import org.example.movementservice.application.repositories.MovementRepository;
import org.example.movementservice.domain.dto.MovementDTO;
import org.example.movementservice.domain.entities.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepo;

    @Transactional(readOnly = true)
    public List<MovementDTO> findAllMovements() {
        return this.movementRepo.findAll()
                .stream().map(MovementDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findMovementsByUser(int userId) {
        return this.movementRepo.findMovementsByUser(userId)
                .stream().map(MovementDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findMovementsByAccount(int accountId) {
        return this.movementRepo.findMovementsByAccount(accountId)
                .stream().map(MovementDTO::new).toList();
    }

    @Transactional
    public MovementDTO insert(Movement movementBody) throws IllegalArgumentException {
        //se debe buscar el usuario y la cuenta antes de guardar, como buscar?
//        if(no existe el usuario o no existe la cuenta {
//            throw new IllegalArgumentException();
//        }
        Movement result = this.movementRepo.save(movementBody);
        return new MovementDTO(result);
    }

    @Transactional
    public MovementDTO updateMovement(Long oldMovementId, Movement newMovement) {
        Movement oldMovement = this.movementRepo.findById(oldMovementId).orElseThrow(() -> new RuntimeException("No se encontro el movimiento con id " + oldMovementId));
        oldMovement.setAccountId(newMovement.getAccountId());
        oldMovement.setUserId(newMovement.getUserId());
        oldMovement.setAmount(newMovement.getAmount());
        oldMovement.setDate(newMovement.getDate());

        Movement updatedMovement = this.movementRepo.save(oldMovement);
        return new MovementDTO(updatedMovement);
    }
}
