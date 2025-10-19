package org.example.integ3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.integ3.service.CarreraService;
import org.example.integ3.service.dto.carrera.response.CarreraConInscriptosDTO;

import java.util.List;

@RestController
@RequestMapping("api/carreras")
@RequiredArgsConstructor
public class CarreraController {

    private final CarreraService carreraService;

    @GetMapping("/orderInscriptos")
    public List<CarreraConInscriptosDTO> consultarCarrerasConInscriptos() {
        return this.carreraService.consultarCarrerasConInscriptos();
    }
}