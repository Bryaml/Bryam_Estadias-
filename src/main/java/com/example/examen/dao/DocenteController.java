package com.example.examen.dao;

import com.example.examen.Entity.Docente;
import com.example.examen.Entity.Tecnico;
import com.example.examen.Repository.DocenteRepository;
import com.example.examen.Repository.TecnicoRepository;
import com.example.examen.service.RegistroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @DeleteMapping("/{id}")
    public void deleteDocente(@PathVariable Long id) {
        docenteRepository.deleteById(id);
    }
    @PostMapping("/registro")
    public String registrar(@RequestBody RegistroForm form) {
        System.out.println("entrando al metodo");
        String rol = form.getRol();
        System.out.println(form.toString());
        System.out.println("Nombres: " + form.getNombres());
        System.out.println("Apellidos: " + form.getApellidos());
        System.out.println("Email: " + form.getEmail());
        System.out.println("Password: " + form.getPassword());
        System.out.println("Teléfono: " + form.getTelefono());
        System.out.println("rol: " + form.getRol());


        try {
            if ("docente".equals(rol)) {
                Docente docente = new Docente();
                docente.setNombres(form.getNombres());
                docente.setApellidos(form.getApellidos());
                docente.setEmail(form.getEmail());
                docente.setPassword(form.getPassword());
                docente.setTelefono(form.getTelefono());

                docenteRepository.save(docente);
            } else if ("tecnico".equals(rol)) {
                Tecnico tecnico = new Tecnico();
                tecnico.setNombres(form.getNombres());
                tecnico.setApellidos(form.getApellidos());
                tecnico.setEmail(form.getEmail());
                tecnico.setPassword(form.getPassword());
                tecnico.setTelefono(form.getTelefono());

                tecnicoRepository.save(tecnico);
            }
            return "registro_exitoso";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }



}