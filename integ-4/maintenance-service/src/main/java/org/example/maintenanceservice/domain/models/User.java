package org.example.maintenanceservice.domain.models;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String last_name;
    private String mail;
    private String phone_number;
    private String state;
    private String role;
}
