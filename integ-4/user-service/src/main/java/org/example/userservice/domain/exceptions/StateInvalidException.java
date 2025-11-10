package org.example.userservice.domain.exceptions;

public class StateInvalidException extends RuntimeException {
    public StateInvalidException(String state) {
        super("Invalid State: " + state);
    }
}
