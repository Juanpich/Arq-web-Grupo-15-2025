package org.example.userservice.domain.dto;

import lombok.*;
import org.example.userservice.domain.entities.User;
import org.example.userservice.domain.enums.Role;
import org.example.userservice.domain.enums.State;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserEmailDto {
    private Long id;
    private String name;
    private String last_name;
    private String mail;
    private String phone_number;
    private State state;
    private Role role;
    private String password;


    public  UserEmailDto(User user) {
        this.id = user.getUser_id();
        this.name = user.getName();
        this.last_name = user.getLast_name();
        this.mail = user.getMail();
        this.phone_number = user.getPhone_number();
        this.state = user.getState();
        this.role = user.getRole();
        this.password = user.getPassword();
    }
}


