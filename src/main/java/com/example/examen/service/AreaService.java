package com.example.examen.service;

import com.example.examen.Entity.Area;
import com.example.examen.Entity.Aula;
import com.example.examen.Entity.Laboratorio;
import com.example.examen.Repository.AreaRepository;
import com.example.examen.Repository.AulaRepository;
import com.example.examen.Repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Transactional
    public void registrarAreaConAulas(Area area, List<Aula> aulas) {
        areaRepository.save(area);
        aulaRepository.saveAll(aulas);
    }

    @Transactional
    public void registrarAreaConLaboratorios(Area area, List<Laboratorio> laboratorios) {
        areaRepository.save(area);
        laboratorioRepository.saveAll(laboratorios);
    }

    // Método que registra un área con sus aulas y laboratorios
    @Transactional
    public void registrarAreaConAulasYLaboratorios(Area area, List<Aula> aulas, List<Laboratorio> laboratorios) {
        areaRepository.save(area);
        aulaRepository.saveAll(aulas);
        laboratorioRepository.saveAll(laboratorios);
    }

    // Otros métodos
}
