package org.example.movementservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.movementservice.domain.entities.Movement;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MovementDTO {
    private int movement_id;
    private int account_id;
    private int user_id;
    private int amount;
    private Date date;

    public MovementDTO(Movement movement){
        this.movement_id = movement.getMovement_id();
        this.account_id =  movement.getAccount_id();
        this.user_id = movement.getUser_id();
        this.amount =  movement.getAmount();
    }

    public Movement DTOToEntity() {
        Movement movement = new Movement(this.getAccount_id(), this.getUser_id(), this.getAmount(), this.getDate());
        return movement;
    }
}

