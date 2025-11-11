package org.example.journeysservice.application.repositories;

import org.example.journeysservice.domain.dto.RateDto;
import org.example.journeysservice.domain.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query("SELECT r FROM Rate r WHERE r.init_date <= :date AND (r.finish_date IS NULL OR r.finish_date >= :date)")
    Optional<Rate> findActiveRateForDate(@Param("date") LocalDateTime date);

    //Todos los rates donde la fecha_init y finish este entre la fecha del viaje
    @Query("""
       SELECT new org.example.journeysservice.domain.dto.RateDto(r)
       FROM Rate r
       WHERE r.init_date <= :finishDate
         AND r.finish_date >= :startDate
       """)
    List<RateDto> findRatesForRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("finishDate") LocalDateTime finishDate);
}

