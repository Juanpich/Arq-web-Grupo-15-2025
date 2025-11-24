package org.example.movementservice.application.services;

import org.example.movementservice.application.repositories.MovementRepository;
import org.example.movementservice.domain.dto.MovementDTO;
import org.example.movementservice.domain.dto.payment.PaymentRequest;
import org.example.movementservice.domain.dto.payment.PaymentResponse;
import org.example.movementservice.domain.entities.Movement;
import org.example.movementservice.domain.exceptions.AccountNotFoundException;
import org.example.movementservice.domain.exceptions.PaymentProcessingException;
import org.example.movementservice.domain.exceptions.UserNotAssociatedWithTheAccount;
import org.example.movementservice.domain.exceptions.UserNotFoundException;
import org.example.movementservice.domain.models.Account;
import org.example.movementservice.domain.models.User;
import org.example.movementservice.infraestructure.feign.AccountFeingClient;
import org.example.movementservice.infraestructure.feign.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovementService {

    private static final Logger log = LoggerFactory.getLogger(MovementService.class);

    private final MovementRepository movementRepo;
    private final UserFeignClient userFeignClient;
    private final AccountFeingClient accountFeignClient;
    private final PaymentService paymentService;


    public MovementService(
            MovementRepository movementRepo,
            UserFeignClient userFeignClient,
            AccountFeingClient accountFeignClient,
            PaymentService paymentService) {
        this.movementRepo = movementRepo;
        this.accountFeignClient = accountFeignClient;
        this.userFeignClient = userFeignClient;
        this.paymentService = paymentService;
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
        //Verificar que usuario y cuenta existan
        User user = this.userFeignClient.getUser(movementBody.getUserId());
        if (user == null) {
            throw new UserNotFoundException(movementBody.getUserId());
        }
        Account account = this.accountFeignClient.getAccountsById(movementBody.getAccountId());
        if (account == null) {
            throw new AccountNotFoundException(movementBody.getAccountId());
        }
        // Verificar que el usuario esté asociado a la cuenta
        List<Account> accounts = this.accountFeignClient.getAccountsByUserId(movementBody.getUserId());
        boolean existAccount = accounts.stream()
                .anyMatch(a -> a.getAccount_id().equals(movementBody.getAccountId()));
        if (!existAccount) {
            throw new UserNotAssociatedWithTheAccount(
                    movementBody.getAccountId(),
                    movementBody.getUserId()
            );
        }
        //Procesar el pago con MercadoPago
        PaymentRequest paymentRequest = PaymentRequest.fromMovement(
                movementBody.getAmount(),
                user.getMail()
        );
        PaymentResponse paymentResponse;
        try {
            paymentResponse = paymentService.processPayment(paymentRequest);
        } catch (Exception e) {
            log.error("Error processing payment for user {}: {}", user.getId(), e.getMessage());
            throw new PaymentProcessingException("No se pudo procesar el pago: " + e.getMessage());
        }
        movementBody.setPaymentId(paymentResponse.getId());
        movementBody.setPaymentStatus(paymentResponse.getStatus());
        movementBody.setPaymentStatusDetail(paymentResponse.getStatusDetail());
        movementBody.setAuthorizationCode(paymentResponse.getAuthorizationCode());
        if (!paymentResponse.isApproved()) {
            log.warn("Payment rejected for movement: status={}, detail={}",
                    paymentResponse.getStatus(),
                    paymentResponse.getStatusDetail());
            throw new PaymentProcessingException(
                    "Pago rechazado: " + paymentResponse.getStatusDetail()
            );
        }
        Movement result = this.movementRepo.save(movementBody);
        log.info("Movement created successfully: id={}, paymentId={}, amount={}",
                result.getMovementId(),
                result.getPaymentId(),
                result.getAmount());
        account.setAmount(Float.valueOf(movementBody.getAmount()));
        this.accountFeignClient.loadAmount(account, account.getAccount_id());
        return new MovementDTO(result);
    }

    @Transactional
    public MovementDTO updateMovement(Long oldMovementId, Movement newMovement) {
        Movement oldMovement = this.movementRepo.findById(oldMovementId)
                .orElseThrow(() -> new RuntimeException("No se encontró el movimiento con id " + oldMovementId));
        if (newMovement.getUserId() != null) {
            User user = this.userFeignClient.getUser(newMovement.getUserId());
            if (user == null) {
                throw new UserNotFoundException(newMovement.getUserId());
            }
            oldMovement.setUserId(newMovement.getUserId());
        }
        if (newMovement.getAccountId() != null) {
            Account account = this.accountFeignClient.getAccountsById(newMovement.getAccountId());
            if (account == null) {
                throw new AccountNotFoundException(newMovement.getAccountId());
            }
            List<Account> accounts = this.accountFeignClient.getAccountsByUserId(newMovement.getUserId());
            boolean existAccount = accounts.stream()
                    .anyMatch(a -> a.getAccount_id().equals(newMovement.getAccountId()));

            if (!existAccount) {
                throw new UserNotAssociatedWithTheAccount(
                        newMovement.getAccountId(),
                        newMovement.getUserId()
                );
            }
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