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
    private int movement_id;
    private int account_id;
    private int user_id;
    private int amount;
    private LocalDate date;

    public MovementDTO(Movement movement){
        if (movement != null) {
            this.movement_id = movement.getMovement_id();
            this.account_id = movement.getAccount_id();
            this.user_id = movement.getUser_id();
            this.amount = movement.getAmount();
            this.date = movement.getDate();
        }
    }

    public Movement DTOToEntity() {
        Movement movement = new Movement(this.getAccount_id(), this.getUser_id(), this.getAmount(), this.getDate());
        return movement;
    }
}

