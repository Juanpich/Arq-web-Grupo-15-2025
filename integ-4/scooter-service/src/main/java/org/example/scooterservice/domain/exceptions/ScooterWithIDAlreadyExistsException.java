package org.example.scooterservice.domain.exceptions;

public class ScooterWithIDAlreadyExistsException extends RuntimeException {
    public ScooterWithIDAlreadyExistsException(Long id) {
        super("Scooter with id " + id + " already exists");
    }
}
