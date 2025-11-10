package org.example.userservice.aplication.services;


import org.example.userservice.aplication.repositories.AccountRepository;
import org.example.userservice.aplication.repositories.UserRepository;
import org.example.userservice.domain.dto.UserDto;
import org.example.userservice.domain.entities.Account;
import org.example.userservice.domain.entities.User;
import org.example.userservice.domain.enums.AccountType;
import org.example.userservice.domain.exceptions.AccountNotFoundException;
import org.example.userservice.domain.exceptions.UserAlreadyAssociatedException;
import org.example.userservice.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    public UserService(UserRepository ur, AccountRepository accountRepository){
        this.userRepository=ur;
        this.accountRepository = accountRepository;
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
        User userNew =  userRepository.save(user);
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
        if (!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException(id);
        }
        User userUpdated = userRepository.save(user);
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
}
