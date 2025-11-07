package org.example.scooterservice.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.scooterservice.application.repositories.ScooterRepository;
import org.example.scooterservice.domain.dtos.ScooterDto;
import org.example.scooterservice.domain.entities.Scooter;

import java.util.List;


@Service
public class ScooterService {
    private final ScooterRepository scooterRepository;

    public ScooterService(ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
    }
    
    //Listar todos los scooters
    @Transactional(readOnly = true)
    public List<ScooterDto> findAllScooter(){
        return this.scooterRepository.findAll().stream().map(ScooterDto:: new).toList();
    }

    //todo cambiar el get por id
    //Listar un scooter por su id
    @Transactional(readOnly = true)
    public ScooterDto findScooterById(Long id){
        Scooter scooter = this.scooterRepository.findById(id).orElseThrow(()-> new RuntimeException("Scooter not found"));
        return new ScooterDto(scooter);
    }

    //Editar un scooter
    @Transactional
    public ScooterDto updateScooter(Long id, Scooter scooterDto){
        Scooter scooter = this.scooterRepository.findById(id).orElseThrow(()-> new RuntimeException("Scooter not found"));
        //Actualizar los campos del scooter
        scooter.setState(scooterDto.getState());
        scooter.setGps(scooterDto.getGps());
        scooter.setParkingDockId(scooter.getParkingDockId());
        Scooter updatedScooter = this.scooterRepository.save(scooter); //scooter editado
        return new ScooterDto(updatedScooter);
    }

    @Transactional
    //Eliminar un scooter
    public void deleteScooter(Long id){
        this.scooterRepository.deleteById(id);
    }

    @Transactional
    //Crear un scooter
    public ScooterDto persistScooter(Scooter scooter){
        Scooter newScooter = this.scooterRepository.save(scooter);
        return new ScooterDto(newScooter);
    }
}
