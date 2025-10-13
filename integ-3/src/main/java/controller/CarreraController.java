package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.CarreraService;
import service.dto.carrera.response.CarreraConInscriptosDTO;

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