package org.example.maintenanceservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.maintenanceservice.domain.entities.Maintenance;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MaintenanceDto {
    private Long maintenance_id;
    private Long scooter_id;
    private Long user_id;
    private LocalDateTime init_date;
    private LocalDateTime finish_date;

    public MaintenanceDto(Maintenance maintenance) {
        this.maintenance_id = maintenance.getMaintenance_id();
        this.scooter_id = maintenance.getScooter_id();
        this.user_id = maintenance.getUser_id();
        this.init_date = maintenance.getInit_date();
        this.finish_date = maintenance.getFinish_date();
    }
}
