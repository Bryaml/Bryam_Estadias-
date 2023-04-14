package com.example.examen.Repository;

import com.example.examen.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    List<Incidencia> findAllByDocenteEmail(String emailDocente);
    List<Incidencia> findAllByTecnicoEmail(String emailTecnico);
    List<Incidencia> findAllByEstadoAndDocenteEmail(EstadoIncidencia estado, String emailDocente);
    List<Incidencia> findAllByEstadoAndTecnicoEmailAndDocenteEmail(EstadoIncidencia estado, String emailTecnico, String emailDocente);
    List<Incidencia> findAllByEstado(EstadoIncidencia estado);
    List<Incidencia> findAllByEstadoAndTecnicoEmail(EstadoIncidencia estado, String emailTecnico);
    List<Incidencia> findByEstadoAndFechaCreacionBetweenOrderByFechaCreacionDesc(EstadoIncidencia estado, LocalDateTime fechaInicio, LocalDateTime fechaFin);


    List<Incidencia> findByDocenteAndTecnicoAndEstado(Docente docente, Tecnico tecnico, EstadoIncidencia estado);


    long countByAulaArea(Area area);


    long countByDocente(Docente docente);

    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.docente.division = :division")
    long countByDocenteDivisionAcademica(@Param("division") String division);

    @Query("SELECT DISTINCT i.tecnico FROM Incidencia i WHERE i.docente.id = :docenteId AND i.estado = :estado")
    List<Tecnico> findDistinctTecnicosByDocenteIdAndEstado(@Param("docenteId") Long docenteId, @Param("estado") EstadoIncidencia estado);
    @Query("SELECT DISTINCT i.docente FROM Incidencia i WHERE i.tecnico.id = :tecnicoId AND i.estado = :estado")
    List<Docente> findDistinctDocentesByTecnicoIdAndEstado(@Param("tecnicoId") Long tecnicoId, @Param("estado") EstadoIncidencia estado);
}
