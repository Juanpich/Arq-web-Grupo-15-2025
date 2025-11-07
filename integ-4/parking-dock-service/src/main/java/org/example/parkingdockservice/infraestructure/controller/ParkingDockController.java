package org.example.parkingdockservice.infraestructure.controller;

import org.example.parkingdockservice.application.service.ParkingDockService;
import org.example.parkingdockservice.domain.dto.ParkingDockDTO;
import org.example.parkingdockservice.domain.entities.ParkingDock;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("api/parkingdock")
public class ParkingDockController {

    private final ParkingDockService parkingDockService;

    public ParkingDockController(ParkingDockService parkingDockService) {
        this.parkingDockService = parkingDockService;
    }

    //Traer todas las paradas
    @GetMapping("")
    public List<ParkingDockDTO> findAllParkingDocks() {
        return parkingDockService.findAllParkingDocks();
    }
    //Traer una parada por su id
    @GetMapping("/{id}")
    public ParkingDockDTO findParkingDockById(@PathVariable Long id) {
        return parkingDockService.findParkingDockById(id);
    }
    //Crear una parada
    @PostMapping("/{id}")
    public ParkingDockDTO persistParkingDock(@PathVariable Long id, @Valid @RequestBody ParkingDock parkingDock) {
        return parkingDockService.persistParkingDock(parkingDock);
    }
    //Editar una parada
    @PutMapping("/{id}")
    public ParkingDockDTO updateParkingDock(@PathVariable Long id, @Valid @RequestBody ParkingDock parkingDock) {
        return parkingDockService.updateParkingDock(parkingDock);
    }
    //Eliminar una parada
    @DeleteMapping("/{id}")
    public void deleteParkingDock(@PathVariable Long id) {
        parkingDockService.deleteParkingDock(id);
    }
    //Traer una lista de los id de todos los monopatines
    @GetMapping("/scooters")
    public List<Long> findAllScootersIds(){
        return parkingDockService.findAllScootersIds();
    }
}
