package org.example.parkingdockservice.infraestructure.controller;

import org.example.parkingdockservice.application.service.ParkingDockService;
import org.example.parkingdockservice.domain.Exceptions.DeletefailParking;
import org.example.parkingdockservice.domain.Exceptions.DeletefailScooter;
import org.example.parkingdockservice.domain.Exceptions.InvalidFields;
import org.example.parkingdockservice.domain.Exceptions.NotExistsParkingDock;
import org.example.parkingdockservice.domain.dto.ParkingDockDTO;
import org.example.parkingdockservice.domain.entities.ParkingDock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parkingdock")
public class ParkingDockController {

    private final ParkingDockService parkingDockService;

    public ParkingDockController(ParkingDockService parkingDockService) {
        this.parkingDockService = parkingDockService;
    }

    // =====================
    //      Parking
    // =====================

    //Traer todas las paradas
    @GetMapping("")
    public ResponseEntity<?> findAllParkingDocks() {
        List<ParkingDockDTO> Parking = parkingDockService.findAllParkingDocks();
        if(Parking == null){
            return ResponseEntity.badRequest().body("No hay paradas registradas.");
        }else {
            return ResponseEntity.ok(Parking);
        }
    }

    //Traer una parada por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> findParkingDockById(@PathVariable Long id) {
        try{
            ParkingDockDTO parking = parkingDockService.findParkingDockById(id);
            return ResponseEntity.ok(parking);

        }catch(NotExistsParkingDock e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // todo lanzar excepcion desde el service

    //Crear una parada
    @PostMapping("")
    public ResponseEntity<?> persistParkingDock(@RequestBody ParkingDock parkingDock) {
        try{
            ParkingDockDTO parking = parkingDockService.persistParkingDock(parkingDock);
            return ResponseEntity.ok(parking);
        } catch(InvalidFields e){
            return ResponseEntity.badRequest().body("Campos invalidos.");
        }
    }

    //Editar una parada
    @PutMapping("/{id}")
    public ResponseEntity<?> updateParkingDock(@PathVariable Long id, @Valid @RequestBody ParkingDock parkingDock) {
        try{
            ParkingDockDTO parking = parkingDockService.updateParkingDock(id, parkingDock);
            return ResponseEntity.ok(parking);
        }catch (InvalidFields e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Eliminar una parada
    @DeleteMapping("/{id}")
    public void deleteParkingDock(@PathVariable Long id) {
        try{
            parkingDockService.deleteParkingDock(id);
        } catch (DeletefailParking e) {
            e.getMessage();
        }
    }

    // =====================
    //      Scooter
    // =====================

    //Traer una lista de los id de todos los monopatines
    @GetMapping("/{id}/scooters")
    public ResponseEntity<?> findAllScootersIds(@PathVariable Long id){
        List<Long> scooters = parkingDockService.findAllScootersIds(id);
        if(scooters == null){
            return ResponseEntity.badRequest().body("La parada no tiene scooters.");
        }else{
            return ResponseEntity.ok(scooters);
        }
    }

    //Insertar un scooter en la parada
    @PutMapping("/{id}/addscooter")
    public ResponseEntity<?> addScooter(@PathVariable Long id, @Valid @RequestBody Map<String, Long> scooter) {
        Long scooter_id = scooter.get("scooter_id");
        try {
            ParkingDockDTO parking = parkingDockService.addScooter(id, scooter_id);
            return ResponseEntity.ok(parking);
        } catch (InvalidFields e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Eliminar un scooter de una parada
    @PutMapping("/{parking_id}/removescooter/{scooter_id}")
    public ResponseEntity<?> removeScooter (@PathVariable Long parking_id, @PathVariable Long scooter_id){
        try {
            ParkingDockDTO parking = parkingDockService.removeScooter(parking_id, scooter_id);
            return ResponseEntity.ok(parking);
        } catch(DeletefailScooter e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
