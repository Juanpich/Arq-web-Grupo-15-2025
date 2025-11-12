package org.example.maintenanceservice.application.repositories;

import org.example.maintenanceservice.domain.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByScooterId(Long scooterId);
    List<Maintenance> findByFinishDateIsNull();
}

