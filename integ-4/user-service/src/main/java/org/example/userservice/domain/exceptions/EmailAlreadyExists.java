package org.example.userservice.domain.exceptions;

public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists() {
        super("The email address entered already exists");
    }
}
