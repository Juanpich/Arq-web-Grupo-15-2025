package org.example.movementservice.application.services;


import org.example.movementservice.application.repositories.MovementRepository;
import org.example.movementservice.domain.dto.MovementDTO;
import org.example.movementservice.domain.entities.Movement;
import org.example.movementservice.domain.exceptions.AccountNotFoundException;
import org.example.movementservice.domain.exceptions.UserNotAssociatedWithTheAccount;
import org.example.movementservice.domain.exceptions.UserNotFoundException;
import org.example.movementservice.domain.models.Account;
import org.example.movementservice.domain.models.User;
import org.example.movementservice.infraestructure.feign.AccountFeingClient;
import org.example.movementservice.infraestructure.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MovementService {


    private MovementRepository movementRepo;
    private UserFeignClient userFeignClient;
    private AccountFeingClient accountFeignClient;

    public MovementService(MovementRepository movementRepo, UserFeignClient userFeignClient, AccountFeingClient accountFeignClient) {
        this.movementRepo = movementRepo;
        this.accountFeignClient = accountFeignClient;
        this.userFeignClient = userFeignClient;
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findAllMovements() {
        return this.movementRepo.findAll()
                .stream().map(MovementDTO::new).toList();
    }
    public List<MovementDTO> findMovementById(Long movementId) {
        return this.movementRepo.findById(movementId)
                .stream().map(MovementDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findMovementsByUser(Long userId) {
        return this.movementRepo.findMovementsByUser(userId)
                .stream().map(MovementDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findMovementsByAccount(Long accountId) {
        return this.movementRepo.findMovementsByAccount(accountId)
                .stream().map(MovementDTO::new).toList();
    }

    @Transactional
    public MovementDTO insert(Movement movementBody) throws IllegalArgumentException {
        //verificar usuario y cuenta existan
        User user =  this.userFeignClient.getUser(movementBody.getUserId());
        if(user == null)
            throw new UserNotFoundException(movementBody.getUserId());
        Account account =  this.accountFeignClient.getAccountsById(movementBody.getAccountId());
        if(account == null)
            throw new AccountNotFoundException(movementBody.getAccountId());
        List<Account> accounts = this.accountFeignClient.getAccountsByUserId(movementBody.getUserId());
        boolean existAccount =  false;
        for(Account a: accounts){
            if(a.getAccount_id().equals(movementBody.getAccountId()))
                existAccount = true;
        }
        if(!existAccount)
            throw new UserNotAssociatedWithTheAccount(movementBody.getAccountId(), movementBody.getUserId());
        Movement result = this.movementRepo.save(movementBody);
        return new MovementDTO(result);
    }

    @Transactional
    public MovementDTO updateMovement(Long oldMovementId, Movement newMovement) {
        Movement oldMovement = this.movementRepo.findById(oldMovementId).orElseThrow(() -> new RuntimeException("No se encontro el movimiento con id " + oldMovementId));
        if(newMovement.getUserId() != null){
            User user =  this.userFeignClient.getUser(newMovement.getUserId());
            if(user == null)
                throw new UserNotFoundException(newMovement.getUserId());
            oldMovement.setUserId(newMovement.getUserId());
        }
        if(newMovement.getAccountId() != null){
            Account account =  this.accountFeignClient.getAccountsById(newMovement.getAccountId());
            if(account == null)
                throw new AccountNotFoundException(newMovement.getAccountId());
            List<Account> accounts = this.accountFeignClient.getAccountsByUserId(newMovement.getUserId());
            boolean existAccount =  false;
            for(Account a: accounts){
                if(a.getAccount_id().equals(newMovement.getAccountId()))
                    existAccount = true;
            }
            if(!existAccount)
                throw new UserNotAssociatedWithTheAccount(newMovement.getAccountId(), newMovement.getUserId());
            oldMovement.setAccountId(newMovement.getAccountId());
        }
        oldMovement.setAmount(newMovement.getAmount());
        oldMovement.setDate(newMovement.getDate());

        Movement updatedMovement = this.movementRepo.save(oldMovement);
        return new MovementDTO(updatedMovement);
    }

    @Transactional
    public void delete(Long movementId) {
        this.movementRepo.deleteById(movementId);
    }

}
