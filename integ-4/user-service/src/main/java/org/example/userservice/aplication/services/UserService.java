package org.example.userservice.aplication.services;


import org.example.userservice.aplication.repositories.AccountRepository;
import org.example.userservice.aplication.repositories.UserRepository;
import org.example.userservice.domain.dto.ScootesNearbyDto;
import org.example.userservice.domain.dto.UserDto;
import org.example.userservice.domain.dto.UserEmailDto;
import org.example.userservice.domain.dto.UserTopUsageDto;
import org.example.userservice.domain.entities.Account;
import org.example.userservice.domain.entities.User;
import org.example.userservice.domain.enums.AccountType;
import org.example.userservice.domain.enums.State;
import org.example.userservice.domain.exceptions.AccountNotFoundException;
import org.example.userservice.domain.exceptions.EmailAlreadyExists;
import org.example.userservice.domain.exceptions.UserAlreadyAssociatedException;
import org.example.userservice.domain.exceptions.UserNotFoundException;
import org.example.userservice.infraestructure.feign.JourneysFeignClient;
import org.example.userservice.infraestructure.feign.ScooterFeignClient;
import org.example.userservice.models.Journey;
import org.example.userservice.models.Scooter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private JourneysFeignClient journeysFeignClient;
    private ScooterFeignClient scooterFeignClient;
    private PasswordEncoder passwordEncoder;
    public UserService(UserRepository ur, AccountRepository accountRepository,  JourneysFeignClient journeysFeignClient, ScooterFeignClient scooterFeignClient, PasswordEncoder passwordEncoder) {
        this.userRepository=ur;
        this.accountRepository = accountRepository;
        this.journeysFeignClient=journeysFeignClient;
        this.scooterFeignClient = scooterFeignClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAll(){
        return userRepository.findAll().stream().map(UserDto::new).toList();
    }
    @Transactional(readOnly = true)
    public List<UserDto> getAllByAccountType(AccountType type) {
        return userRepository.findAllByAccountType(type);
    }

    @Transactional
    public UserDto save(User user){
        //constructor que uso para crear
        if(user.getMail() == null)
            throw new RuntimeException("Faltan datos, Email obligatorio");
        int userEmail = userRepository.countFindByMail(user.getMail());
        if( userEmail > 0)
            throw new EmailAlreadyExists();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newU = new User(user.getMail(), user.getName(), user.getLast_name(), user.getPhone_number(), user.getPassword());
        User userNew =  userRepository.save(newU);
        return new UserDto(userNew);
    }
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElse(null);
    }
    @Transactional
    public UserDto update(User user){
        Long id = user.getUser_id();
        Optional<User> upd = userRepository.findById(id);
        if (upd.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        if(user.getMail() != null) {
            int userEmail = userRepository.countFindByMail(user.getMail());
            if (userEmail > 0)
                throw new EmailAlreadyExists();
        }
        if(user.getName()!= null)
            upd.get().setName(user.getName());
        if(user.getLast_name() != null)
            upd.get().setLast_name(user.getLast_name());
        if(user.getPhone_number() != null)
            upd.get().setPhone_number(user.getPhone_number());
        if(user.getPassword() != null)
            upd.get().setPassword(passwordEncoder.encode(user.getPassword()));
        User userUpdated = userRepository.save(upd.get());
        return new UserDto(userUpdated);
    }
    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void associate(Long id, Long idAccount) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        Account account = accountRepository.findById(idAccount).orElse(null);
        if (account == null) {
            throw new AccountNotFoundException(idAccount);
        }
        if (user.getAccounts().contains(account)) {
            throw new UserAlreadyAssociatedException(id, idAccount);
        }
        user.addAccount(account);
        userRepository.save(user);
    }
    @Transactional
    public UserDto changeState(State stateEnum, Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException(id);
        }
        user.get().setState(stateEnum);
        User userUpdated = this.userRepository.save(user.get()); //scooter editado
        return new UserDto(userUpdated);
    }
    @Transactional(readOnly = true)
    public List<UserTopUsageDto> getTopUsers(AccountType typeN, LocalDate startDateN, LocalDate endDateN) {
        List<UserTopUsageDto> userTopUsageDtos = new ArrayList<>();
        List<UserDto> users = this.userRepository.findAllByAccountType(typeN);
        if(users.isEmpty()){
            return null;
        }
        for(UserDto user : users){
            List<Journey> journeys = this.journeysFeignClient.getJourneyByUser(user.getId(), startDateN.toString(), endDateN.toString());
            userTopUsageDtos.add(new UserTopUsageDto(user, journeys.size()));
        }
        return userTopUsageDtos.stream()
                .sorted(Comparator.comparingInt(UserTopUsageDto::getCant_journeys).reversed())
                .toList();
    }

    public List<ScootesNearbyDto> getNearbyRates(Long id, String gps) {
        Optional<User> user =  this.userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException(id);
        }
        List<Scooter> scooters = this.scooterFeignClient.getScooterByGps(gps);
        List<ScootesNearbyDto> scootesNearby =  new ArrayList<>();
        for(Scooter s :scooters){
            scootesNearby.add(new ScootesNearbyDto(s));
        }
        return scootesNearby;

    }

    //Traer un usuario con ese mail
    public UserEmailDto userByEmail(String email){
        UserEmailDto user = this.userRepository.findByMail(email);
        return  user;
    }
}
