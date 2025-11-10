package org.example.parkingdockservice.application.service;

import lombok.RequiredArgsConstructor;
import org.example.parkingdockservice.application.repository.ParkingDockRepository;
import org.example.parkingdockservice.domain.dto.ParkingDockDTO;
import org.example.parkingdockservice.domain.entities.ParkingDock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingDockService {
    private final ParkingDockRepository parkingDockRepository;

    // =====================
    //      Parking
    // =====================

    @Transactional(readOnly = true)
    //Devuelve todas las paradas (DTO)
    public List<ParkingDockDTO> findAllParkingDocks() {
        final List<ParkingDockDTO> result = parkingDockRepository.findAll().stream().map(ParkingDockDTO:: new ).toList();
        return result;
    }

    @Transactional(readOnly = true)
    //Devuelve la parada por su Id (DTO)
    public ParkingDockDTO findParkingDockById(Long id) {
        final ParkingDockDTO parking = parkingDockRepository.findById(id).map(ParkingDockDTO:: new).orElse(null);
        return parking;
    }

    //Inserta una parada (DTO)
    public ParkingDockDTO persistParkingDock(ParkingDock parking) {
        ParkingDock saved = parkingDockRepository.save(parking);
        return new ParkingDockDTO(saved);
    }

    //Elimina una parada (VOID)
    public void deleteParkingDock(Long id) {
        parkingDockRepository.deleteById(id);
    }

    @Transactional
    // Edita una parada (DTO)
    public ParkingDockDTO updateParkingDock(Long id, ParkingDock parking) {
        // Busco la parada que se quiere editar
        ParkingDock parking_obj = parkingDockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ParkingDock not found"));

        // Seteo los nuevos valores
        parking_obj.setParkingDock_ubication(parking.getParkingDock_ubication());
        parking_obj.setScooters(parking.getScooters());

        // Guardo los cambios
        ParkingDock saved = parkingDockRepository.save(parking_obj);

        return new ParkingDockDTO(saved);
    }

    // ====================
    //      Scooter
    // ====================

    @Transactional(readOnly = true)
    //Devuelve todos los id de scooters
    public List<Long> findAllScootersIds(Long id) {
        final List<Long> result = parkingDockRepository.findAllScootersIds(id);
        return result;
    }

    @Transactional
    //Inserta un scooter a una parada
    public ParkingDockDTO addScooter(Long parkingDock_id, Long scooter_id) {
        Optional<ParkingDock> parada = parkingDockRepository.findById(parkingDock_id);
        parada.get().addScooter(scooter_id);
        return new ParkingDockDTO(parada.get());
    }

    @Transactional
    //Elimina un scooter de una parada
    public ParkingDockDTO removeScooter (Long parkingDock_id, Long scooter_id){
        Optional<ParkingDock> parada = parkingDockRepository.findById(parkingDock_id);
        parada.get().removeScooter(scooter_id);
        return new ParkingDockDTO(parada.get());
    }
}
