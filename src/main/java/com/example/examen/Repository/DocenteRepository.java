package com.example.examen.Repository;

import com.example.examen.Entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Optional<Docente> findByEmail(String email);
    @Query("SELECT DISTINCT d.division FROM Docente d")
    List<String> findDistinctDivisionesAcademicas();

}