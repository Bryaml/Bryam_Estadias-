package com.example.examen.Repository;

import com.example.examen.Entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {


    Optional<Aula> findByNombreAndAreaId(String nombre, Long areaId);
}