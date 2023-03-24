package com.example.examen.Repository;

import com.example.examen.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    List<Incidencia> findByEstadoAndFechaCreacionBetweenOrderByFechaCreacionDesc(EstadoIncidencia estado, LocalDateTime fechaInicio, LocalDateTime fechaFin);






}
