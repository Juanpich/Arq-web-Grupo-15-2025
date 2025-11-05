package org.example.userservice.domain.dto;

import lombok.*;
import org.example.userservice.domain.entities.Account;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountDto {
    private Long account_id;
    private Float amount;
    private String type;
    public AccountDto(Account account){
        this.account_id = account.getAccount_id();
        this.amount = account.getAmount();
        this.type = account.getType().toString();
    }
}
