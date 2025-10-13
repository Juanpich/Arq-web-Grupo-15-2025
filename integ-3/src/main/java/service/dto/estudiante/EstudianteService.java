package service.dto.estudiante;


import lombok.RequiredArgsConstructor;
import model.Estudiante;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.EstudianteRepository;
import service.dto.estudiante.request.EstudianteDtoRequest;
import service.dto.estudiante.response.EstudianteResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;

    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findAll(){
        return this.estudianteRepository.findAll().stream()
                .map( EstudianteResponseDTO::new  ).toList();
    }

    public EstudianteResponseDTO insert(EstudianteDtoRequest estudianteDtoRequest) {
        Estudiante estudiante = new Estudiante(estudianteDtoRequest);
        final var result = this.estudianteRepository.save( estudiante );
        return new EstudianteResponseDTO(estudianteDtoRequest.getDni(), estudianteDtoRequest.getNombre(), estudianteDtoRequest.getApellido(), estudianteDtoRequest.getGenero(), estudianteDtoRequest.getEdad(), estudianteDtoRequest.getCiudad(), estudianteDtoRequest.getLU());
    }


}
