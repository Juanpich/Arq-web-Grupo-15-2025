package org.example.parkingdockservice.application.service;

import lombok.RequiredArgsConstructor;
import org.example.parkingdockservice.application.repository.ParkingDockRepository;
import org.example.parkingdockservice.domain.dto.ParkingDockDTO;
import org.example.parkingdockservice.domain.entities.ParkingDock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingDockService {
    private final ParkingDockRepository parkingDockRepository;

    @Transactional(readOnly = true)
    //Devuelve todas las paradas DTO
    public List<ParkingDockDTO> findAllParkingDocks() {
        final List<ParkingDockDTO> result = parkingDockRepository.findAll().stream().map(ParkingDockDTO:: new ).toList();
        return result;
    }

    @Transactional(readOnly = true)
    //Devuelve todos los id de scooters
    public List<Long> findAllScootersIds() {
        final List<Long> result = parkingDockRepository.findAllScootersIds();
        return result;
    }

    @Transactional(readOnly = true)
    //Devuelve la parada por su Id
    public ParkingDockDTO findParkingDockById(Long id) {
        final ParkingDockDTO parking = parkingDockRepository.findById(id).map(ParkingDockDTO:: new).orElse(null);
        return parking;
    }

    //Inserta una parada
    public ParkingDockDTO persistParkingDock(ParkingDock parking) {
        ParkingDock saved = parkingDockRepository.save(parking);
        return new ParkingDockDTO(saved);
    }

    //Elimina una parada
    public void deleteParkingDock(Long id) {
        parkingDockRepository.deleteById(id);
    }

    //Edita una parada
    public ParkingDockDTO updateParkingDock(ParkingDock parking) {
        //Traigo la parada que se quiere editar
        Long parking_id = parking.getParkingDOck_id();
        ParkingDock parking_obj = parkingDockRepository.findById(parking_id).orElse(null) ;
        //Le seteo los nuevos valores
        parking_obj.setParkingDock_ubication(parking.getParkingDock_ubication());
        parking_obj.setScooters(parking.getScooters());
        //Retorno la parada recien creada en dto.
        ParkingDock saved =  parkingDockRepository.save(parking_obj);
        return new ParkingDockDTO(saved);
    }
}
