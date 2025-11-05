package org.example.userservice.infraestructure.controller;

import org.example.userservice.aplication.services.UserService;
import org.example.userservice.domain.dto.UserDto;
import org.example.userservice.domain.entities.User;
import org.example.userservice.domain.exceptions.AccountNotFoundException;
import org.example.userservice.domain.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAll();
        if (users.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        UserDto user = userService.getUserById(id);
        if (user == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @PostMapping("")
    public ResponseEntity<UserDto> save(@RequestBody User user) {
        UserDto userNew = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
        return ResponseEntity.ok("DELETED");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> save(@RequestBody User user, @PathVariable("id") Long id) {
        user.setUser_id(id);
        try{
            UserDto userNew = userService.update(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
        }catch (UserNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/{id}/associate/{id_account}")
    public ResponseEntity<?> associate(@PathVariable("id") Long id, @PathVariable("id_account") Long id_account) {
        try{
            this.userService.associate(id, id_account);
            return ResponseEntity.status(HttpStatus.CREATED).body("associate");
        }catch(UserNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }catch(AccountNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

}
