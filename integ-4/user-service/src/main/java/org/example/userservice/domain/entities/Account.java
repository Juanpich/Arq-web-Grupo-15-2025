package org.example.userservice.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.userservice.domain.enums.AccountType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;

    private Float amount;
    @Enumerated(EnumType.STRING)
    private AccountType type;

    // LADO PROPIETARIO (define la tabla intermedia)
    @ManyToMany
    private Set<User> users= new HashSet<>();
    public void addUser(User user) {
        this.users.add(user);
        user.getAccounts().add(this);
    }
}

