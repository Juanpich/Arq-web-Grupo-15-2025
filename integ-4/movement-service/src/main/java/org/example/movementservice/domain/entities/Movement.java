package org.example.movementservice.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.Date;

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
    private Date date;

    public Movement(int accountId, int userId, int amount, Date date) {
        this.account_id = accountId;
        this.user_id = userId;
        this.amount = amount;
        this.date = date;
    }
}
