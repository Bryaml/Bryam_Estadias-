package com.example.examen.dao;

import com.example.examen.Entity.Administrador;
import com.example.examen.Entity.Tecnico;
import com.example.examen.Repository.AdministradorRepository;
import com.example.examen.request.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("imagen") MultipartFile file) {
        try {
            Administrador administrador = administradorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado con ID " + id));

            administrador.setImagen(file.getBytes());
            administradorRepository.save(administrador);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("No se pudo cargar la imagen");
        }
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Administrador administrador = administradorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado con ID " + id));
        byte[] imagen = administrador.getImagen();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAdministrador(@PathVariable Long id) {
        administradorRepository.deleteById(id);
    }
}