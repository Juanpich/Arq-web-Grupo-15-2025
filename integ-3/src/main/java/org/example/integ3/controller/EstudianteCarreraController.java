package org.example.integ3.controller;
import jakarta.validation.Valid;
import org.example.integ3.service.dto.estudianteCarreraRepository.EstudianteCarreraService;
import org.example.integ3.service.dto.estudianteCarreraRepository.request.EstudianteCarreraRequestDTO;
import org.example.integ3.service.dto.estudianteCarreraRepository.response.EstudianteCarreraResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.integ3.service.dto.estudiante.EstudianteService;
import org.example.integ3.service.dto.estudiante.request.EstudianteDtoRequest;
import org.example.integ3.service.dto.estudiante.response.EstudianteResponseDTO;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/estudiantes-matricular/")
public class EstudianteCarreraController {
    @Autowired
    private EstudianteCarreraService estudianteCarreraService;

    public  EstudianteCarreraController(EstudianteCarreraService estudianteCarreraService){
        this.estudianteCarreraService = estudianteCarreraService;
    }

    @PostMapping("")
    public List<EstudianteCarreraResponseDTO> matricularEstudiante(@RequestBody @Valid EstudianteCarreraRequestDTO estudianteDtoRequest){
        final var result = this.estudianteCarreraService.insert( estudianteDtoRequest );
        return (List<EstudianteCarreraResponseDTO>) ResponseEntity.accepted().body( result );

    }
}
