package com.example.examen.service;

import com.example.examen.Entity.Laboratorio;
import com.example.examen.Repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratorioService {
    @Autowired
    private LaboratorioRepository laboratorioRepository;

    public List<Laboratorio> getAllLaboratorios() {
        return laboratorioRepository.findAll();
    }

    public Laboratorio createLaboratorio(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }

    public Optional<Laboratorio> getLaboratorioById(Long id) {
        return laboratorioRepository.findById(id);
    }

    public void deleteLaboratorioById(Long id) {
        laboratorioRepository.deleteById(id);
    }

    public void updateLaboratorio(Laboratorio laboratorio) {
        laboratorioRepository.save(laboratorio);
    }
}
