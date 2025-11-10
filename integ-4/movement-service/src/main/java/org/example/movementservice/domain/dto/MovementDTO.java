package org.example.movementservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.movementservice.domain.entities.Movement;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MovementDTO {
    private Long movementId;
    private int accountId;
    private int userId;
    private int amount;
    private LocalDate date;

    public MovementDTO(Movement movement){
        if (movement != null) {
            this.movementId = movement.getMovementId();
            this.accountId = movement.getAccountId();
            this.userId = movement.getUserId();
            this.amount = movement.getAmount();
            this.date = movement.getDate();
        }
    }

    public Movement DTOToEntity() {
        Movement movement = new Movement(this.getAccountId(), this.getUserId(), this.getAmount(), this.getDate());
        return movement;
    }
}

