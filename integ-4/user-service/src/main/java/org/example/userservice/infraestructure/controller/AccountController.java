package org.example.userservice.infraestructure.controller;

import org.example.userservice.aplication.services.AccountService;
import org.example.userservice.domain.dto.AccountDto;
import org.example.userservice.domain.dto.UserDto;
import org.example.userservice.domain.entities.Account;
import org.example.userservice.domain.exceptions.AccountNotFoundException;
import org.example.userservice.domain.exceptions.InvalidAmountException;
import org.example.userservice.domain.exceptions.UserAlreadyAssociatedException;
import org.example.userservice.domain.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;
    public  AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    @GetMapping("")
    public ResponseEntity<List<AccountDto>> getAllAccount() {
        List<AccountDto> accounts = accountService.getAll();
        if (accounts.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") Long id) {
        AccountDto accountNew = accountService.getAccountById(id);
        if (accountNew == null) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " +  id  + " was not found");
        }
        return ResponseEntity.ok(accountNew);
    }
    @GetMapping("/{id}/users")
    public ResponseEntity<?> getUsersByAccountId(@PathVariable("id") Long id){
        try{
            List<UserDto> users= this.accountService.getUsersByAccountId(id);
            return ResponseEntity.ok(users);
        }catch(AccountNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAccountsByUserId(@PathVariable("id") Long id) {
        try {
            List<AccountDto> accounts = accountService.getAllByUserId(id);
            return ResponseEntity.ok(accounts);
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @PostMapping("")
    public ResponseEntity<AccountDto> save(@RequestBody Account account) {
        AccountDto accountNew = accountService.save(account);
        return ResponseEntity.ok(accountNew);
    }
    @PutMapping("/{id}/load-amount")
    public ResponseEntity<?> loadAmount(@RequestBody Account account, @PathVariable("id") Long id){
        try {
            account.setAccount_id(id);
            AccountDto accountRes = accountService.loadAmount(account);
            return ResponseEntity.ok(accountRes);
        } catch (AccountNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch (UserAlreadyAssociatedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (InvalidAmountException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }
    }
    @PutMapping("/{id}/change-type")
    public ResponseEntity<?> changeType(@RequestBody Account account, @PathVariable("id") Long id){
        try {
            account.setAccount_id(id);
            AccountDto accountRes = accountService.changeType(account);
            return ResponseEntity.ok(accountRes);
        } catch (AccountNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        this.accountService.delete(id);
        return ResponseEntity.ok("DELETED");
    }
}
