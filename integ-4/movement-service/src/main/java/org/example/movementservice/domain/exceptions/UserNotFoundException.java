package org.example.movementservice.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with ID " +  id  + " was not found" );
    }
}
