package org.example.parkingdockservice.domain.Exceptions;

public class InvalidFields extends RuntimeException {
    public InvalidFields(String message) {
        super("Campos invalidos.");
    }
}
