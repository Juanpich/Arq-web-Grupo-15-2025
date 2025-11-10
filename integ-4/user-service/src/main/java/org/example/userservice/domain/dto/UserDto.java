package org.example.userservice.domain.dto;

import lombok.*;
import org.example.userservice.domain.entities.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String name;
    private String last_name;
    private String mail;
    private String phone_number;
    private LocalDateTime created;

    public  UserDto(User user) {
        this.id = user.getUser_id();
        this.name = user.getName();
        this.last_name = user.getLast_name();
        this.mail = user.getMail();
        this.phone_number = user.getPhone_number();
        this.created = user.getCreated_at();
    }
}

