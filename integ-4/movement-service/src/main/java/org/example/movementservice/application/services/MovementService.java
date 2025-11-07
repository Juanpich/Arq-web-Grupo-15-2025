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

    public MovementDTO insert(MovementDTO movementDTObody) throws IllegalArgumentException {
        //se debe buscar el usuario y la cuenta antes de guardar, como buscar?
//        if(no existe el usuario o no existe la cuenta {
//            throw new IllegalArgumentException();
//        }
        Movement newMovement = new Movement(movementDTObody.getAccount_id(), movementDTObody.getUser_id(), movementDTObody.getAmount(), LocalDate.now());
        Movement result = this.movementRepo.save(newMovement);
        return new MovementDTO(result);
    }
}
