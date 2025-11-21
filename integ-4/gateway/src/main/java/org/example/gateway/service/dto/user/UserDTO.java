package org.example.gateway.service.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.gateway.security.Type;

import java.util.Set;

@Data
public class UserDTO {

    @NotNull( message = "El mail es un campo requerido." )
    @NotEmpty( message = "El mail es un campo requerido." )
    private String mail;
    @NotNull( message = "La contraseña es un campo requerido." )
    @NotEmpty( message = "La contraseña es un campo requerido." )
    private String password;
    private String name;
    private String last_name;
    private String phone_number;
}
