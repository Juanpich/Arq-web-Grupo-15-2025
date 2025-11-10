package org.example.movementservice.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    private int accountId;
    private int userId;
    private int amount;
    private LocalDate date;

    public Movement(int accountId, int userId, int amount, LocalDate date) {
        this.accountId = accountId;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
    }

    public Movement(Long movement_id ,int accountId, int userId, int amount, LocalDate date) {
        this.movementId = movement_id;
        this.accountId = accountId;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
    }

    public Movement(Movement newMovement) {
        this.movementId = newMovement.getMovementId();
        this.accountId = newMovement.getAccountId();
        this.userId = newMovement.getUserId();
        this.amount = newMovement.getAmount();
        this.date = newMovement.getDate();
    }
}
