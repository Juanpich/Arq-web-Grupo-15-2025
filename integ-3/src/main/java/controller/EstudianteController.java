package controller;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.estudiante.EstudianteService;
import service.dto.estudiante.request.EstudianteDtoRequest;
import service.dto.estudiante.response.EstudianteResponseDTO;

import java.util.List;


@RestController
@RequestMapping("api/estudiantes")

public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService service) {
        this.estudianteService = service;
    }

    @GetMapping("")
    public List<EstudianteResponseDTO> findAll() {
        return this.estudianteService.findAll();
    }

    @PostMapping("")
    public ResponseEntity<EstudianteResponseDTO> save(@RequestBody @Valid EstudianteDtoRequest request ){
        final var result = this.estudianteService.insert( request );
        return ResponseEntity.accepted().body( result );
    }
}
