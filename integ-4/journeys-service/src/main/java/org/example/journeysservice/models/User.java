package org.example.journeysservice.models;

import lombok.*;
import org.example.userservice.domain.enums.State;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String name;
    private String last_name;
    private String mail;
    private String phone_number;
    private State state;


}


