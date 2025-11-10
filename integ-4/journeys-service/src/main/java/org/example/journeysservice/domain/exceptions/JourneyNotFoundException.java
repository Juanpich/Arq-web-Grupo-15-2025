package org.example.journeysservice.domain.exceptions;

public class JourneyNotFoundException extends RuntimeException {
    public JourneyNotFoundException(Long id) {
        super("Journey with ID " + id + " was not found");
    }
}

