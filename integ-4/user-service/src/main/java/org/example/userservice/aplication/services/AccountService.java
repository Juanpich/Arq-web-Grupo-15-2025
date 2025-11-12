package org.example.userservice.aplication.services;

import org.example.userservice.aplication.repositories.AccountRepository;
import org.example.userservice.aplication.repositories.UserRepository;
import org.example.userservice.domain.dto.AccountDto;
import org.example.userservice.domain.dto.PaymentMadeDto;
import org.example.userservice.domain.dto.UserDto;
import org.example.userservice.domain.entities.Account;
import org.example.userservice.domain.entities.User;
import org.example.userservice.domain.exceptions.AccountNotFoundException;
import org.example.userservice.domain.exceptions.InvalidAmountException;
import org.example.userservice.domain.exceptions.UserNotAssociatedWithTheAccount;
import org.example.userservice.domain.exceptions.UserNotFoundException;
import org.example.userservice.infraestructure.feign.JourneysFeignClient;
import org.example.userservice.models.PriceJourney;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    private AccountRepository  accountRepository;
    private UserRepository userRepository;
    private JourneysFeignClient journeysFeignClient;
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, JourneysFeignClient journeysFeignClient){
        this.accountRepository=accountRepository;
        this.userRepository=userRepository;
        this.journeysFeignClient = journeysFeignClient;
    }
    @Transactional
    public List<AccountDto> getAll(){
        return accountRepository.findAll().stream().map(AccountDto::new).toList();
    }
    @Transactional
    public AccountDto save(Account account){
        Account accountNew = accountRepository.save(account);
        return new AccountDto(accountNew);
    }
    @Transactional
    public void delete(Long account){
        accountRepository.deleteById(account);
    }
    @Transactional
    public AccountDto getAccountById(Long id){
        return accountRepository.findById(id)
                .map(AccountDto::new)
                .orElse(null);
    }
    @Transactional
    public List<AccountDto> getAllByUserId(Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            return user.getAccounts().stream().map(AccountDto::new).toList();
        }else {
            throw new UserNotFoundException(id);
        }
    }
    @Transactional
    public List<UserDto> getUsersByAccountId(Long id){
        Account account = accountRepository.findById(id).orElse(null);
        if(account != null){
            return account.getUsers().stream().map(UserDto::new).toList();
        }else{
            throw new AccountNotFoundException(id);
        }
    }
    @Transactional
    public AccountDto loadAmount(Account accountData) {
        Long id = accountData.getAccount_id();
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        if(accountData.getAmount() <= 0){
            throw new InvalidAmountException(accountData.getAmount());
        }
        account.setAmount(accountData.getAmount() + account.getAmount());
        Account updated = accountRepository.save(account);
        return new AccountDto(updated);
    }

    @Transactional
    public AccountDto changeType(Account accountData) {
        Long id = accountData.getAccount_id();
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        account.setType(accountData.getType());
        Account updated = accountRepository.save(account);
        return new AccountDto(updated);
    }
    @Transactional
    public PaymentMadeDto makePayment(Long idAccount, Long idJourney) {
        Account account = this.accountRepository.findById(idAccount)
                .orElseThrow(() -> new AccountNotFoundException(idAccount));
        PriceJourney priceJourney = this.journeysFeignClient.getPriceJourneyById(idJourney);
        if(priceJourney == null)
            throw new RuntimeException("Journey not found");
        User user = this.userRepository.findById(priceJourney.getUserId())
                .orElseThrow(()->new UserNotFoundException(priceJourney.getUserId()));
        if(!account.getUsers().contains(user))
            throw new UserNotAssociatedWithTheAccount(idAccount, user.getUser_id());
        account.setAmount(account.getAmount() - priceJourney.getTotalPrice());
        Account accountA = this.accountRepository.save(account);
        return new PaymentMadeDto(idAccount, user.getUser_id(), idJourney,priceJourney.getTotalPrice(), accountA.getAmount());
    }




}
