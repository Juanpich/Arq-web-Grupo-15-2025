package org.example.integ3.service.dto.estudianteCarreraRepository;

import lombok.RequiredArgsConstructor;
import org.example.integ3.model.Carrera;
import org.example.integ3.model.Estudiante;
import org.example.integ3.model.EstudianteCarrera;
import org.example.integ3.repository.CarreraRepository;
import org.example.integ3.repository.EstudianteCarreraRepository;
import org.example.integ3.repository.EstudianteRepository;
import org.example.integ3.service.dto.estudianteCarreraRepository.request.EstudianteCarreraRequestDTO;
import org.example.integ3.service.dto.estudianteCarreraRepository.response.EstudianteCarreraResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteCarreraService  {

    private final EstudianteCarreraRepository estudianteCarreraRepository;
    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;
    public EstudianteCarreraRequestDTO insert(EstudianteCarreraRequestDTO inscripcionDto) throws IllegalArgumentException {
    //se debe buscar la carrera y el estudiantes antes de guardar
        Estudiante est = estudianteRepository.findById(inscripcionDto.getId_estudiante()).orElse(null);
        Carrera carr = carreraRepository.findById(inscripcionDto.getId_carrera()).orElse(null);
        if(est == null || carr == null) {
            throw new IllegalArgumentException();
        }
        EstudianteCarrera estCarr = new EstudianteCarrera(LocalDate.now().getYear(), 0, 0, est, carr );
        EstudianteCarrera result = this.estudianteCarreraRepository.save(estCarr);

        return new EstudianteCarreraResponseDTO(result);
    }
}
