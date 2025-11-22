package org.example.journeysservice.domain.exceptions;

public class ScooterNotAviableException extends Throwable {
    public ScooterNotAviableException(Long scooterId) {
        super("Scooter with ID "+ scooterId +" not available or scooter not available for use");
    }
}
