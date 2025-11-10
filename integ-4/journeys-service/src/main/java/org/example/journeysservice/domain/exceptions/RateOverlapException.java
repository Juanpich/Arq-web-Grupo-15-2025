package org.example.journeysservice.domain.exceptions;

public class RateOverlapException extends RuntimeException {
    public RateOverlapException(String message) {
        super(message);
    }
}

