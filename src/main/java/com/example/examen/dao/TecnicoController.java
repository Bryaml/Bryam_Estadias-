package com.example.examen.dao;

import com.example.examen.Entity.Tecnico;
import com.example.examen.Repository.TecnicoRepository;
import com.example.examen.request.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @GetMapping("")
    public List<Tecnico> getTecnicos() {
        return tecnicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Tecnico getTecnico(@PathVariable Long id) {
        return tecnicoRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public Tecnico addTecnico(@RequestBody Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }

    @PutMapping("/{id}")
    public Tecnico updateTecnico(@PathVariable Long id, @RequestBody Tecnico tecnico) {
        Tecnico existingTecnico = tecnicoRepository.findById(id).orElse(null);
        if (existingTecnico != null) {
            existingTecnico.setNombres(tecnico.getNombres());
            existingTecnico.setApellidos(tecnico.getApellidos());
            existingTecnico.setEmail(tecnico.getEmail());
            // Actualizar más campos aquí si es necesario
            return tecnicoRepository.save(existingTecnico);
        }
        return null;
    }
    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("imagen") MultipartFile file) {
        try {
            Tecnico tecnico = tecnicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado con ID " + id));

            tecnico.setImagen(file.getBytes());
            tecnicoRepository.save(tecnico);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("No se pudo cargar la imagen");
        }
    }
    @DeleteMapping("/{id}")
    public void deleteTecnico(@PathVariable Long id) {
        tecnicoRepository.deleteById(id);
    }

}