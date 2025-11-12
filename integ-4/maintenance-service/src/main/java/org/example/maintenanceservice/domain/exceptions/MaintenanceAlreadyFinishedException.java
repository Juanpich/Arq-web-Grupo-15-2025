package org.example.maintenanceservice.domain.exceptions;

public class MaintenanceAlreadyFinishedException extends RuntimeException {
    public MaintenanceAlreadyFinishedException(Long maintenanceId) {
        super("Maintenance with ID " + maintenanceId + " is already finished and cannot be modified");
    }
}

