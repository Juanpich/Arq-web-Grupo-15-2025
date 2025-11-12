package org.example.maintenanceservice.domain.exceptions;

public class InvalidMaintenanceDataException extends RuntimeException {
    public InvalidMaintenanceDataException(String message) {
        super("Invalid maintenance data: " + message);
    }
}

