package org.example.maintenanceservice.application.services;

import org.example.maintenanceservice.application.repositories.MaintenanceRepository;
import org.example.maintenanceservice.domain.dto.MaintenanceDto;
import org.example.maintenanceservice.domain.entities.Maintenance;
import org.example.maintenanceservice.domain.exceptions.InvalidMaintenanceDataException;
import org.example.maintenanceservice.domain.exceptions.MaintenanceAlreadyFinishedException;
import org.example.maintenanceservice.domain.exceptions.MaintenanceNotFoundException;
import org.example.maintenanceservice.infrastructure.feing.ScooterFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final ScooterFeignClient scooterFeignClient;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, ScooterFeignClient scooterFeignClient) {
        this.maintenanceRepository = maintenanceRepository;
        this.scooterFeignClient = scooterFeignClient;
    }

    @Transactional(readOnly = true)
    public List<MaintenanceDto> getAll() {
        return maintenanceRepository.findAll().stream().map(MaintenanceDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MaintenanceDto getMaintenanceById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new MaintenanceNotFoundException(id));
        return new MaintenanceDto(maintenance);
    }

    @Transactional
    public MaintenanceDto save(Maintenance maintenance) {
        if (maintenance.getScooterId() == null || maintenance.getUser_id() == null) {
            throw new InvalidMaintenanceDataException("Scooter ID and User ID cannot be null.");
        }
        if (maintenance.getInit_date() == null) {
            throw new InvalidMaintenanceDataException("Init date cannot be null.");
        }
        if (maintenance.getFinishDate() != null) {
            throw new InvalidMaintenanceDataException("New maintenance cannot have a finish date.");
        }
        return new MaintenanceDto(maintenanceRepository.save(maintenance));
    }

    @Transactional
    public MaintenanceDto startMaintenance(Long scooterId, Long userId) {
        if (scooterId == null || userId == null) {
            throw new InvalidMaintenanceDataException("Scooter ID and User ID cannot be null.");
        }
        Map<String, String> stateBody = Map.of("state", "MAINTENANCE");
        scooterFeignClient.updateScooterState(scooterId, stateBody);
        Maintenance maintenance = new Maintenance(scooterId, userId, LocalDateTime.now(), null);
        return new MaintenanceDto(maintenanceRepository.save(maintenance));
    }

    @Transactional
    public MaintenanceDto finishMaintenance(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException(maintenanceId));

        if (maintenance.getFinishDate() != null) {
            throw new MaintenanceAlreadyFinishedException(maintenanceId);
        }

        maintenance.setFinishDate(LocalDateTime.now());
        Map<String, String> stateBody = Map.of("state", "AVAILABLE");
        scooterFeignClient.updateScooterState(maintenance.getScooterId(), stateBody);
        return new MaintenanceDto(maintenanceRepository.save(maintenance));
    }

    @Transactional(readOnly = true)
    public List<MaintenanceDto> getMaintenancesByScooterId(Long scooterId) {
        return maintenanceRepository.findByScooterId(scooterId).stream()
                .map(MaintenanceDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MaintenanceDto> getActiveMaintenances() {
        return maintenanceRepository.findByFinishDateIsNull().stream()
                .map(MaintenanceDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new MaintenanceNotFoundException(id);
        }
        maintenanceRepository.deleteById(id);
    }
}
