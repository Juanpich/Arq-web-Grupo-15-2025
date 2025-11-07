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
@RequiredArgsConstructor
@Data
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movement_id;

    private int account_id;
    private int user_id;
    private int amount;
    private LocalDate date;

    public Movement(int accountId, int userId, int amount, LocalDate date) {
        this.account_id = accountId;
        this.user_id = userId;
        this.amount = amount;
        this.date = date;
    }

    public Movement(Movement newMovement) {
        this.account_id = newMovement.getAccount_id();
        this.user_id = newMovement.getUser_id();
        this.amount = newMovement.getAmount();
        this.date = newMovement.getDate();
    }
}
