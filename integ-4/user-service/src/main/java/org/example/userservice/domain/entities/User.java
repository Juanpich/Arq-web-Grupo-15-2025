package org.example.userservice.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.userservice.domain.enums.State;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;
    private String last_name;
    private String mail;
    private String phone_number;
    @Enumerated(EnumType.STRING)
    private State state;
    // LADO INVERSO (no propietario)
    @ManyToMany(mappedBy = "users")
    private Set<Account> accounts =new HashSet<>();
    public void addAccount(Account account) {
        this.accounts.add(account);
        account.getUsers().add(this);
    }
}
