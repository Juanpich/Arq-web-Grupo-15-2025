package org.example.maintenanceservice.domain.exceptions;

public class MaintenanceNotFoundException extends RuntimeException {
    public MaintenanceNotFoundException(Long id) {
        super("Maintenance with ID " + id + " was not found");
    }
}

