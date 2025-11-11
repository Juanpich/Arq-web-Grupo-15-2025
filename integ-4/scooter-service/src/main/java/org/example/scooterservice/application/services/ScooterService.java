package org.example.scooterservice.application.services;

import org.example.scooterservice.domain.entities.State;
import org.example.scooterservice.domain.exceptions.ScooterNotFoundException;
import org.example.scooterservice.domain.exceptions.ScooterWithIDAlreadyExistsException;
import org.example.scooterservice.infraestructure.feingClient.ScooterFeignClient;
import org.example.scooterservice.infraestructure.models.Journey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.scooterservice.application.repositories.ScooterRepository;
import org.example.scooterservice.domain.dtos.ScooterDto;
import org.example.scooterservice.domain.entities.Scooter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ScooterService {
    private final ScooterRepository scooterRepository;
    private final ScooterFeignClient scooterFeignClient;

    public ScooterService(ScooterRepository scooterRepository, ScooterFeignClient scooterFeignClient) {
        this.scooterRepository = scooterRepository;
        this.scooterFeignClient = scooterFeignClient;
    }
    
    //Listar todos los scooters
    @Transactional(readOnly = true)
    public List<ScooterDto> findAllScooter(){
        return this.scooterRepository.findAll().stream().map(ScooterDto:: new).toList();
    }

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
    @Transactional
    public ScooterDto changeState(State stateEnum, Long id) {
        Optional<Scooter> scooter = this.scooterRepository.findByScooterId(id);
        if(scooter.isEmpty()){
            throw new ScooterNotFoundException(id);
        }
        scooter.get().setState(stateEnum);
        Scooter updatedScooter = this.scooterRepository.save(scooter.get()); //scooter editado
        return new ScooterDto(updatedScooter);
    }

    //los monopatines con más de X viajes en un cierto año.
    @Transactional
    public List<ScooterDto> scootersForYear(Integer year, Integer count){
        //scooter a retornar
        List<ScooterDto> scooterResult = new ArrayList<>();
        //todos los scooters
        List<Scooter> scootersGet = this.scooterRepository.findAll();
        for (int i = 0; i < scootersGet.size(); i++) {
            Long scooter_id = scootersGet.get(i).getScooter_id();
            List<Journey> countJourney = this.scooterFeignClient.FindAllJourneysByScooterANDYear(scooter_id, year);
            if(countJourney.size() >= count){
                scooterResult.add(new ScooterDto(scootersGet.get(i)));
            }
        }
        return scooterResult;
    }

    public List<ScooterDto> getAllByGps(String gps) {
        List<ScooterDto> scooterResult = this.scooterRepository.findAllByGps(gps).stream().map(ScooterDto:: new).toList();
        return scooterResult;
    }
}
