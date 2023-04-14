package com.example.examen.Repository;

import com.example.examen.Entity.Docente;
import com.example.examen.Entity.Mensaje;
import com.example.examen.Entity.MensajeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    @Query("SELECT m FROM Mensaje m WHERE (m.docente.id = :docenteId AND m.tecnico.id = :tecnicoId) OR (m.docente.id = :tecnicoId AND m.tecnico.id = :docenteId) ORDER BY m.fechaEnvio ASC")
    List<Mensaje> findMensajesByDocenteIdAndTecnicoId(@Param("docenteId") Long docenteId, @Param("tecnicoId") Long tecnicoId);



    @Query("SELECT DISTINCT d FROM Mensaje m JOIN m.docente d WHERE m.tecnico.id = :tecnicoId")
    List<Docente> findDocentesByTecnicoId(@Param("tecnicoId") Long tecnicoId);

    List<Mensaje> findByDocenteId(Long docenteId);
}