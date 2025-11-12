package org.example.userservice.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.userservice.domain.enums.AccountType;
import java.time.LocalDateTime;
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
    private LocalDateTime created_at;

    // LADO PROPIETARIO (define la tabla intermedia)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_account",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore // ← también lo ignoramos si no necesitás mostrar los usuarios dentro de la cuenta
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getAccounts().add(this);
    }
    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }
}

