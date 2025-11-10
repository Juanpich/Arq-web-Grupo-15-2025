package org.example.userservice.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with ID " +  id  + " was not found" );
    }
}
