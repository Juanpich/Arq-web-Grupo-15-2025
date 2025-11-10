package org.example.journeysservice.domain.exceptions;

public class InvalidJourneyDataException extends RuntimeException {
    public InvalidJourneyDataException(String message) {
        super(message);
    }
}

