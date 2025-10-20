package org.example.integ3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.integ3.service.CarreraService;
import org.example.integ3.service.dto.carrera.response.CarreraResponseRegisteredCountDTO;
import org.example.integ3.service.dto.carrera.response.CarreraResponseReportDTO;
import org.example.integ3.service.dto.carrera.response.CarreraResponseDTO;
import java.util.List;

@RestController
@RequestMapping("api/carreras")
public class CarreraController {

    private final CarreraService carreraService;

    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping("")
    public List<CarreraResponseDTO> findAll(){
        return this.carreraService.findAll();
    }

    @GetMapping("/orderInscriptos")
    public List<CarreraResponseRegisteredCountDTO> consultarCarrerasPorInscriptos() {
        return this.carreraService.findAllOrderByRegisteredCount();
    }

    @GetMapping("/reporte")
    public List<CarreraResponseReportDTO> carreraReport() {
        return this.carreraService.carreraReport();
    }
}