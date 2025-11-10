package org.example.journeysservice.domain.exceptions;

public class InvalidRateDataException extends RuntimeException {
    public InvalidRateDataException(String message) {
        super(message);
    }
}

