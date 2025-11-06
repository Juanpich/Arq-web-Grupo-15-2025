package org.example.movementservice.application.services;


import org.example.movementservice.application.repositories.MovementRepository;
import org.example.movementservice.domain.dto.MovementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
