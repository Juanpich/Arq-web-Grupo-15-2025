package org.example.scooterservice.domain.exceptions;

public class ScooterNotFoundException extends RuntimeException {
    public ScooterNotFoundException(Long id) {
        super("Scooter with id " + id + " not found");
    }
}
