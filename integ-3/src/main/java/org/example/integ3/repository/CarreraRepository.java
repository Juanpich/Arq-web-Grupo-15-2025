package org.example.integ3.repository;

import org.example.integ3.model.Carrera;
import org.example.integ3.service.dto.carrera.response.CarreraResponseRegisteredCountDTO;
import org.example.integ3.service.dto.carrera.response.CarreraResponseReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {


    @Query( "SELECT new org.example.integ3.service.dto.carrera.response.CarreraResponseRegisteredCountDTO( CAST(count(i.id_estudiante) AS integer),c.carrera, c.duracion) " +
            "FROM Carrera c " +
            "JOIN c.Inscriptos i " +
            "GROUP BY c.id_carrera " +
            "ORDER BY COUNT(i.id_estudiante) DESC ")
    List<CarreraResponseRegisteredCountDTO> findAllOrderByRegisteredCount();

    @Query("SELECT new org.example.integ3.service.dto.carrera.response.CarreraResponseReportDTO(c.carrera, CAST(count(i.inscripcion) AS INTEGER ), CAST(SUM(CASE WHEN i.graduacion <> 0THEN 1 ELSE 0 END) AS INTEGER), i.inscripcion) " +
            "FROM Carrera c " +
            "JOIN c.Inscriptos i " +
            "GROUP BY c.id_carrera, i.inscripcion " +
            "ORDER BY c.carrera ASC, i.inscripcion DESC")
    List<CarreraResponseReportDTO> carreraReport();
}
