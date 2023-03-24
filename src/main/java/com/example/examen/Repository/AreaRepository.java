package com.example.examen.Repository;

import com.example.examen.Entity.Area;
import com.example.examen.Entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {


}
