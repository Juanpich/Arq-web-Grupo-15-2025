package org.example.integ3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.integ3.repository.CarreraRepository;
import org.example.integ3.service.dto.carrera.response.CarreraConInscriptosDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarreraService {

    private final CarreraRepository carreraRepository;
    //private final EstudianteoRepository estudianteRepository;


    @Transactional(readOnly = true)
    public List<CarreraConInscriptosDTO> consultarCarrerasConInscriptos() {
        return this.carreraRepository.consultarCarrerasConInscriptos()
                .stream().map(CarreraConInscriptosDTO::new).toList();
    }
}