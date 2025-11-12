package org.example.maintenanceservice.infrastructure.controllers;

import org.example.maintenanceservice.application.services.MaintenanceService;
import org.example.maintenanceservice.domain.dto.MaintenanceDto;
import org.example.maintenanceservice.domain.entities.Maintenance;
import org.example.maintenanceservice.domain.exceptions.InvalidMaintenanceDataException;
import org.example.maintenanceservice.domain.exceptions.MaintenanceAlreadyFinishedException;
import org.example.maintenanceservice.domain.exceptions.MaintenanceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("")
    public ResponseEntity<List<MaintenanceDto>> getAllMaintenances() {
        return ResponseEntity.ok(maintenanceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaintenanceById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(maintenanceService.getMaintenanceById(id));
        } catch (MaintenanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/scooter/{scooterId}")
    public ResponseEntity<List<MaintenanceDto>> getMaintenancesByScooterId(@PathVariable Long scooterId) {
        return ResponseEntity.ok(maintenanceService.getMaintenancesByScooterId(scooterId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<MaintenanceDto>> getActiveMaintenances() {
        return ResponseEntity.ok(maintenanceService.getActiveMaintenances());
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Maintenance maintenance) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.save(maintenance));
        } catch (InvalidMaintenanceDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> startMaintenance(@RequestBody Map<String, Long> request) {
        try {
            Long scooterId = request.get("scooterId");
            Long userId = request.get("userId");
            return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.startMaintenance(scooterId, userId));
        } catch (InvalidMaintenanceDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error starting maintenance: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<?> finishMaintenance(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(maintenanceService.finishMaintenance(id));
        } catch (MaintenanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MaintenanceAlreadyFinishedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error finishing maintenance: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            maintenanceService.delete(id);
            return ResponseEntity.ok().build();
        } catch (MaintenanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

