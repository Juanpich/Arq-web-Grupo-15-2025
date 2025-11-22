package org.example.journeysservice.domain.exceptions;

public class UserNotAvailableException extends Throwable {
    public UserNotAvailableException(Long userId) {
        super("User with ID "+ userId +" not available or user cancelled");
    }
}
