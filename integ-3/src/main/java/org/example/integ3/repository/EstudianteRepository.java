package org.example.integ3.repository;
import org.example.integ3.model.Carrera;
import org.example.integ3.model.Estudiante;
import org.example.integ3.service.dto.estudiante.response.EstudianteResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import org.example.integ3.service.dto.estudiante.response.EstudianteResponseDTO;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("SELECT new org.example.integ3.service.dto.estudiante.response.EstudianteResponseDTO(e)" +
            " FROM Estudiante e ORDER BY e.apellido ASC")
    List<EstudianteResponseDTO> findAllEstudiantes();

    @Query("Select new org.example.integ3.service.dto.estudiante.response.EstudianteResponseDTO(e) " +
            "From Estudiante e WHERE e.genero LIKE :genero ")
    List<EstudianteResponseDTO> findByGenro(String genero);

    @Query("SELECT new org.example.integ3.service.dto.estudiante.response.EstudianteResponseDTO(e)" +
            " FROM Estudiante e WHERE e.LU = :lu") List<EstudianteResponseDTO> estudianteByLu(int lu);

    @Query("SELECT new org.example.integ3.service.dto.estudiante.response.EstudianteResponseDTO(e) " +
            "FROM Estudiante e " +
            "JOIN e.carreras c  " +
            "WHERE c.id_carrera = :carrera " +
            "AND e.ciudad LIKE :ciudad")
    List<EstudianteResponseDTO> estudianteByCarreraCiudad(Carrera carrera, String ciudad);

}
