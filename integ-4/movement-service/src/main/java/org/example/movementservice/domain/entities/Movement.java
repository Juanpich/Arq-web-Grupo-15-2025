package org.example.movementservice.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDate;


@Entity
@NoArgsConstructor
@Data
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    private Long accountId;
    private Long userId;
    private Long amount;
    private LocalDate date;

    public Movement(Long accountId, Long userId, Long amount) {
        this.accountId = accountId;
        this.userId = userId;
        this.amount = amount;
    }

    public Movement(Long movement_id ,Long accountId, Long userId, Long amount, LocalDate date) {
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
    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }
}
