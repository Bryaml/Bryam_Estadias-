package com.example.examen.service;

import com.example.examen.Entity.Aula;
import com.example.examen.Repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AulaService {
    @Autowired
    private AulaRepository aulaRepository;

    public List<Aula> getAllAulas() {
        return aulaRepository.findAll();
    }

    public Aula createAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    public Optional<Aula> getAulaById(Long id) {
        return aulaRepository.findById(id);
    }

    public void deleteAulaById(Long id) {
        aulaRepository.deleteById(id);
    }

    public void updateAula(Aula aula) {
        aulaRepository.save(aula);
    }
}
