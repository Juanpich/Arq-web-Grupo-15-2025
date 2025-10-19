package org.example.integ3.controller;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.integ3.service.dto.estudiante.EstudianteService;
import org.example.integ3.service.dto.estudiante.request.EstudianteDtoRequest;
import org.example.integ3.service.dto.estudiante.response.EstudianteResponseDTO;

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

    @GetMapping("/order")
    public List<EstudianteResponseDTO> findByOrderName() {
        return this.estudianteService.findAllOrderByName();
    }
    @GetMapping("/genero/{genero}")
    public List<EstudianteResponseDTO> findByGenero(@PathVariable("genero") String genero) {
        return this.estudianteService.findByGenero(genero);
    }

    @PostMapping("")
    public ResponseEntity<EstudianteResponseDTO> save(@RequestBody @Valid EstudianteDtoRequest request ){
        final var result = this.estudianteService.insert( request );
        return ResponseEntity.accepted().body( result );
    }

    @GetMapping("/lu/{lu}")
    public List<EstudianteResponseDTO> estudianteByLu(@PathVariable("lu") int lu){
        return  this.estudianteService.estudianteByLu(lu);
    }


}
