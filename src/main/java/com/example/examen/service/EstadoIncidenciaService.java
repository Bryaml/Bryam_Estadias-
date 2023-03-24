/*
package com.example.examen.service;

import com.example.examen.Repository.EstadoIncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EstadoIncidenciaService {

    @Autowired
    private EstadoIncidenciaRepository estadoIncidenciaRepository;

    public EstadoIncidencia crearEstadoIncidencia(EstadoIncidencia estadoIncidencia) {
        return estadoIncidenciaRepository.save(estadoIncidencia);
    }

    public List<EstadoIncidencia> obtenerTodos() {
        return estadoIncidenciaRepository.findAll();
    }
    @Transactional
    public EstadoIncidencia obtenerPorId(Long id) throws ChangeSetPersister.NotFoundException {
        return estadoIncidenciaRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public EstadoIncidencia obtenerPorNombre(String nombre) {
        return EstadoIncidenciaRepository.findByNombre(nombre);
    }

}


 */