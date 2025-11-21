package org.example.userservice.infraestructure.controller;

import org.example.userservice.aplication.services.UserService;
import org.example.userservice.domain.dto.ScootesNearbyDto;
import org.example.userservice.domain.dto.UserDto;
import org.example.userservice.domain.dto.UserTopUsageDto;
import org.example.userservice.domain.entities.User;
import org.example.userservice.domain.enums.AccountType;
import org.example.userservice.domain.enums.State;
import org.example.userservice.domain.exceptions.AccountNotFoundException;
import org.example.userservice.domain.exceptions.StateInvalidException;
import org.example.userservice.domain.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false, name="type") String type) {
        List<UserDto> users;
        try {
            if (type != null) {
                AccountType accountType = AccountType.valueOf(type.toUpperCase());
                users = userService.getAllByAccountType(accountType);
            } else {
                users = userService.getAll();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Type invalid");
        }
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
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
    public ResponseEntity<?> save(@RequestBody User user) {
        try{
            UserDto userNew = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
        //todo verificar que si el gmail se cambia no exista sino lanzar excepcion
    }
    @PostMapping("/{id}/associate/{id-account}")
    public ResponseEntity<?> associate(@PathVariable("id") Long id, @PathVariable("id-account") Long id_account) {
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
    @PutMapping("/{id}/change-state")
    public ResponseEntity<?>  changeState(@RequestBody Map<String, String> body, @PathVariable Long id){
        UserDto user;
        try{
            String state = body.get("state");
            State stateEnum = State.valueOf(state.toUpperCase());
            user = this.userService.changeState(stateEnum, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("State invalid" );
        }
    }
    //Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
    //período y por tipo de usuario
    @GetMapping("/usage/top-users")
    public ResponseEntity<?> getTopUsers(
            @RequestParam(name = "start-date", required = true) String startDate,
            @RequestParam(name = "end-date", required = true) String endDate,
            @RequestParam(name = "type", required = true) String type
    ) {
        List<UserTopUsageDto> users;
        try{
            AccountType typeN = AccountType.valueOf(type.toUpperCase());
            LocalDate startDateN = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate endDateN = LocalDate.parse(endDate,  DateTimeFormatter.ISO_LOCAL_DATE);
            users =  this.userService.getTopUsers(typeN, startDateN, endDateN);
        } catch (StateInvalidException e) {
           return  ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(users);

    }
    // Como usuario quiero buscar un listado de los monopatines cercanos a mi zona, para poder
    //encontrar un monopatín cerca de mi ubicación
    @GetMapping("{id}/scooter/nearby/{gps}")
    public ResponseEntity<?> getNearbyRates(@PathVariable("id") Long id, @PathVariable("gps") String gps) {
        List<ScootesNearbyDto> scootesNearby;
        try{
            scootesNearby = this.userService.getNearbyRates(id, gps);
            return ResponseEntity.ok(scootesNearby);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //traer un usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getEmail(@PathVariable("email") String email) {
        UserDto user = this.userService.userByEmail(email);
        if( user != null){
            return ResponseEntity.ok(user);
        }
        return null;
    }

}
