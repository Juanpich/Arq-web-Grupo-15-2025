package org.example.integ3.repository;

import org.example.integ3.model.Carrera;
import org.example.integ3.service.dto.carrera.response.CarreraConInscriptosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {








    @Query( "SELECT new org.example.integ3.service.dto.carrera.response.CarreraConInscriptosDTO(CAST(count(i.id_estudiante) AS integer), c.carrera, c.duracion) " +
            "FROM Carrera c " +
            "JOIN c.Inscriptos i " +
            "GROUP BY c.id_carrera " +
            "ORDER BY COUNT(i.id_estudiante) DESC ")
    List<CarreraConInscriptosDTO> consultarCarrerasConInscriptos();
}
