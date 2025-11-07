package org.example.userservice.domain.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(Float amount) {
        super("The amount entered is invalid: " + amount);
    }
}
