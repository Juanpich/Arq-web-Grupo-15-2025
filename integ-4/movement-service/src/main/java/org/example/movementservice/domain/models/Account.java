package org.example.movementservice.domain.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    private Long account_id;
    private Float amount;
    private String type;

    public Account(Account account){
        this.account_id = account.getAccount_id();
        this.amount = account.getAmount();
        this.type = account.getType().toString();

    }
}

