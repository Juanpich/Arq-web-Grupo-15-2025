package org.example.parkingdockservice.infraestructure.controller;

import org.example.parkingdockservice.application.service.ParkingDockService;
import org.example.parkingdockservice.domain.dto.ParkingDockDTO;
import org.example.parkingdockservice.domain.entities.ParkingDock;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parkingdock")
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
    @PostMapping("")
    public ParkingDockDTO persistParkingDock(@RequestBody ParkingDock parkingDock) {
        return parkingDockService.persistParkingDock(parkingDock);
    }

    //Editar una parada
    @PutMapping("/{id}")
    public ParkingDockDTO updateParkingDock(@PathVariable Long id, @Valid @RequestBody ParkingDock parkingDock) {
        return parkingDockService.updateParkingDock(id, parkingDock);
    }

    //Eliminar una parada
    @DeleteMapping("/{id}")
    public void deleteParkingDock(@PathVariable Long id) {
        parkingDockService.deleteParkingDock(id);
    }

    //Traer una lista de los id de todos los monopatines
    @GetMapping("/{id}/scooters")
    public List<Long> findAllScootersIds(@PathVariable Long id){
        return parkingDockService.findAllScootersIds(id);
    }

    //Insertar un scooter en la parada
    @PutMapping("/{id}/addscooter")
    public ParkingDockDTO addScooter(@PathVariable Long id, @Valid @RequestBody Map<String, Long> scooter){
        Long scooter_id = scooter.get("scooter_id");
        return parkingDockService.addScooter(id, scooter_id);
    }

    //Eliminar un scooter de una parada
//    @DeleteMapping("/{id}/removescooter/{id}")
//    public void r
}
