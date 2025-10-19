package org.example.integ3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.integ3.repository.CarreraRepository;
import org.example.integ3.service.dto.carrera.response.CarreraResponseRegisteredCountDTO;
import org.example.integ3.service.dto.carrera.response.CarreraResponseReportDTO;
import org.example.integ3.service.dto.carrera.response.CarreraResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarreraService {

    private final CarreraRepository carreraRepository;

    @Transactional(readOnly = true)
    public List<CarreraResponseRegisteredCountDTO> findAllOrderByRegisteredCount() {
        return this.carreraRepository.findAllOrderByRegisteredCount()
                .stream().map(CarreraResponseRegisteredCountDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> findAll() {
        return this.carreraRepository.findAll()
            .stream().map(CarreraResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<CarreraResponseReportDTO> carreraReport() {
        return this.carreraRepository.carreraReport()
                .stream().map(CarreraResponseReportDTO::new).toList();
    }
}