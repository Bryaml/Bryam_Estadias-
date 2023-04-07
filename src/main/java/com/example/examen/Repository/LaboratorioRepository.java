package com.example.examen.Repository;

import com.example.examen.Entity.Aula;
import com.example.examen.Entity.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
    Optional<Laboratorio> findByNombre(String nombre);

    Optional<Laboratorio> findByNombreAndAreaId(String nombre, Long areaId);
}