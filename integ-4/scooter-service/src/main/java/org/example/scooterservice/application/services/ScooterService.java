package org.example.scooterservice.application.services;

import org.example.scooterservice.domain.entities.State;
import org.example.scooterservice.domain.exceptions.ScooterNotFoundException;
import org.example.scooterservice.domain.exceptions.ScooterWithIDAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.scooterservice.application.repositories.ScooterRepository;
import org.example.scooterservice.domain.dtos.ScooterDto;
import org.example.scooterservice.domain.entities.Scooter;

import java.util.List;
import java.util.Optional;


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
    public ScooterDto findScooterById(Long id) {
        Optional<Scooter> optionalScooter = scooterRepository.findByScooterId(id);
        if (optionalScooter.isEmpty()) {
            throw new ScooterNotFoundException(id);
        }
        return new ScooterDto(optionalScooter.get());
    }

    //Editar un scooter
    @Transactional
    public ScooterDto updateScooter(Long id, Scooter scooterDto){
        Optional<Scooter> scooter = this.scooterRepository.findByScooterId(id);
        if(scooter.isEmpty()){
            throw new ScooterNotFoundException(id);
        }
        //Actualizar los campos del scooter
        scooter.get().setState(scooterDto.getState());
        scooter.get().setGps(scooterDto.getGps());
        scooter.get().setParkingDockId(scooterDto.getParkingDockId());
        Scooter updatedScooter = this.scooterRepository.save(scooter.get()); //scooter editado
        return new ScooterDto(updatedScooter);
    }

    @Transactional
    //Eliminar un scooter
    public void deleteScooter(Long id){
        this.scooterRepository.deleteByScooter_id(id);
    }

    @Transactional
    //Crear un scooter
    public ScooterDto persistScooter(Scooter scooter){
        Optional<Scooter> scooterV = this.scooterRepository.findByScooterId(scooter.getScooter_id());
        if(scooterV.isPresent()){
            throw new ScooterWithIDAlreadyExistsException(scooter.getScooter_id());
        }
        Scooter newScooter = this.scooterRepository.save(scooter);
        return new ScooterDto(newScooter);
    }
    @Transactional(readOnly = true)
    public List<ScooterDto> getAllByState(State stateEnum) {
        return this.scooterRepository.findAllByState(stateEnum).stream().map(ScooterDto:: new).toList();
    }
}
