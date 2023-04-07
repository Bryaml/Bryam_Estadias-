package com.example.examen.dao;

import com.example.examen.Entity.Administrador;
import com.example.examen.Entity.Docente;
import com.example.examen.Entity.Tecnico;
import com.example.examen.Repository.DocenteRepository;
import com.example.examen.Repository.TecnicoRepository;
import com.example.examen.request.ResourceNotFoundException;
import com.example.examen.service.RegistroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.Doc;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @GetMapping("")
    public List<Docente> getDocentes() {
        return docenteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Docente getDocente(@PathVariable Long id) {
        return docenteRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public Docente addDocente(@RequestBody Docente docente) {
        return docenteRepository.save(docente);
    }

    @PutMapping("/{id}")
    public Docente updateDocente(@PathVariable Long id, @RequestBody Docente docente) {
        Docente existingDocente = docenteRepository.findById(id).orElse(null);
        if (existingDocente != null) {
            existingDocente.setNombres(docente.getNombres());
            existingDocente.setApellidos(docente.getApellidos());
            existingDocente.setEmail(docente.getEmail());
            // Actualizar más campos aquí si es necesario
            return docenteRepository.save(existingDocente);
        }
        return null;
    }
    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("imagen") MultipartFile file) {
        try {
            Docente docente = docenteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado con ID " + id));

            docente.setImagen(file.getBytes());
            docenteRepository.save(docente);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("No se pudo cargar la imagen");
        }
    }
    @DeleteMapping("/{id}")
    public void deleteDocente(@PathVariable Long id) {
        docenteRepository.deleteById(id);
    }

}