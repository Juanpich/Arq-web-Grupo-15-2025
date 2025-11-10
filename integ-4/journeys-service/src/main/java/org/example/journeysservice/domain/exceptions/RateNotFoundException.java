package org.example.journeysservice.domain.exceptions;

public class RateNotFoundException extends RuntimeException {
    public RateNotFoundException(Long id) {
        super("Rate with ID " + id + " was not found");
    }

    public RateNotFoundException(String message) {
        super(message);
    }
}

