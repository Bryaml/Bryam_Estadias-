package com.example.examen.dao;

import com.example.examen.Entity.Administrador;
import com.example.examen.Repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorRepository administradorRepository;

    @GetMapping("")
    public List<Administrador> getAdministradores() {
        return administradorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Administrador getAdministrador(@PathVariable Long id) {
        return administradorRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public Administrador createAdministrador(@RequestBody Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @PutMapping("/{id}")
    public Administrador updateAdministrador(@PathVariable Long id, @RequestBody Administrador administrador) {
        Administrador admin = administradorRepository.findById(id).orElse(null);
        if (admin != null) {
            admin.setNombres(administrador.getNombres());
            admin.setApellidos(administrador.getApellidos());
            admin.setEmail(administrador.getEmail());
            // setear otros atributos si se requiere
            return administradorRepository.save(admin);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteAdministrador(@PathVariable Long id) {
        administradorRepository.deleteById(id);
    }
}