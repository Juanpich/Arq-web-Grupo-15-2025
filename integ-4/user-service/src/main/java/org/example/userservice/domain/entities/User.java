package org.example.userservice.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.userservice.domain.enums.State;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonIgnore // ← evita que se serialice recursivamente
    private Set<Account> accounts = new HashSet<>();

    public User(String mail, String name, String last_name, String phone_number) {
        this.mail = mail;
        this.name = name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.state = State.UNCANCELLED;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.getUsers().add(this);
    }
}   
