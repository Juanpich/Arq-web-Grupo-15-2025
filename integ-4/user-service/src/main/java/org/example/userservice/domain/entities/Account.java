package org.example.userservice.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.userservice.domain.enums.AccountType;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;

    private Float amount;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private LocalDateTime created_at;

    // LADO PROPIETARIO (define la tabla intermedia)
    @ManyToMany
    private Set<User> users= new HashSet<>();
    public void addUser(User user) {
        this.users.add(user);
        user.getAccounts().add(this);
    }
    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }
}

