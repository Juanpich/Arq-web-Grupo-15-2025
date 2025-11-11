package org.example.journeysservice.models;

import lombok.*;

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
    private String state;


}


