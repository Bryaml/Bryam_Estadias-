package com.example.examen.dao;

import com.example.examen.Repository.AdministradorRepository;
import com.example.examen.Repository.DocenteRepository;
import com.example.examen.Repository.TecnicoRepository;
import com.example.examen.request.ResourceNotFoundException;
import com.example.examen.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import com.example.examen.Entity.Administrador;
import com.example.examen.Entity.Docente;
import com.example.examen.Entity.Tecnico;
import com.example.examen.Repository.DocenteRepository;
import io.jsonwebtoken.Jwts;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String EMAIL_SENDER;

    private static final String EMAIL_SUBJECT = "Nueva contraseña para acceso al sistema";


    @Autowired
    private AuthService authService; // Agregar la variable de instancia de AuthService

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        // Buscar usuario por email
        Docente docente = docenteRepository.findByEmail(loginForm.getEmail()).orElse(null);
        Tecnico tecnico = tecnicoRepository.findByEmail(loginForm.getEmail()).orElse(null);
        Administrador admin = administradorRepository.findByEmail(loginForm.getEmail()).orElse(null);
        // Comprobar si el usuario existe y la contraseña es correcta
        if (docente != null && docente.getPassword().equals(loginForm.getPassword())  && docente.getRol().equals("docente")) {
            // Generar token de acceso para el usuario y devolverlo en la respuesta
            String token = authService.generateToken(docente.getRol());
        // Crear instancia de AuthService y llamar al método generateToken()
            return ResponseEntity.ok().body(new JwtResponse(token, docente.getId(), docente.getNombres(), docente.getEmail(), docente.getRol()));
            // Comprobar si el usuario existe y la contraseña es correcta
        } else if (tecnico != null && tecnico.getPassword().equals(loginForm.getPassword()) && tecnico.getRol().equals("tecnico")) {
            // Generar token de acceso para el usuario y devolverlo en la respuesta
            String token = authService.generateToken(tecnico.getRol());
            // Crear instancia de AuthService y llamar al método generateToken()
            return ResponseEntity.ok().body(new JwtResponse(token, tecnico.getId(), tecnico.getNombres(), tecnico.getEmail(), tecnico.getRol()));
            // Comprobar si el usuario existe y la contraseña es correcta
        } else if (admin != null && admin.getPassword().equals(loginForm.getPassword()) && admin.getRol().equals("admin")) {
            // Generar token de acceso para el usuario y devolverlo en la respuesta
            String token = authService.generateToken(admin.getRol());
            // Crear instancia de AuthService y llamar...
            return ResponseEntity.ok().body(new JwtResponse(token, admin.getId(), admin.getNombres(), admin.getEmail(), admin.getRol()));
        }
        // Si no se encuentra el usuario o la contraseña es incorrecta, devolver una respuesta de error
        return ResponseEntity.badRequest().body("Usuario o contraseña incorrecta");
    }

    @PostMapping("/recuperar-contrasena")
    public void recuperarContraseña(@RequestBody Map<String, Object> body) throws MessagingException, ResourceNotFoundException {
        String email = (String) body.get("email");
        // Buscar el usuario por email
        System.out.println("entrando al metodo");
        System.out.println("Email: " + email);
        Docente docente = docenteRepository.findByEmail(email).orElse(null);
        Tecnico tecnico = tecnicoRepository.findByEmail(email).orElse(null);
        Administrador admin = administradorRepository.findByEmail(email).orElse(null);

        if (docente != null) {
            enviarCorreo(docente);
        } else if (tecnico != null) {
            enviarCorreo(tecnico);
        } else if (admin != null) {
            enviarCorreo(admin);
        } else {
            throw new ResourceNotFoundException("No se encontró el usuario con el email proporcionado.");
        }
    }

    private void enviarCorreo(Docente docente) throws MessagingException {
        // Generar contraseña temporal aleatoria
        String newPassword = generarContraseñaAleatoria();

        // Actualizar contraseña del usuario en la base de datos
        docente.setPassword(newPassword);
        docenteRepository.save(docente);

        // Enviar correo electrónico al usuario con la nueva contraseña
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(EMAIL_SENDER);
        mailMessage.setTo(docente.getEmail());
        mailMessage.setSubject(EMAIL_SUBJECT);
        mailMessage.setText("Su nueva contraseña es: " + newPassword);
        mailSender.send(mailMessage);
    }

    private void enviarCorreo(Tecnico tecnico) throws MessagingException {
        // Generar contraseña temporal aleatoria
        String newPassword = generarContraseñaAleatoria();

        // Actualizar contraseña del usuario en la base de datos
        tecnico.setPassword(newPassword);
        tecnicoRepository.save(tecnico);

        // Enviar correo electrónico al usuario con la nueva contraseña
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(EMAIL_SENDER);
        mailMessage.setTo(tecnico.getEmail());
        mailMessage.setSubject(EMAIL_SUBJECT);
        mailMessage.setText("Su nueva contraseña es: " + newPassword);
        mailSender.send(mailMessage);
    }

    private void enviarCorreo(Administrador admin) throws MessagingException {
        // Generar contraseña temporal aleatoria
        String newPassword = generarContraseñaAleatoria();

        // Actualizar contraseña del usuario en la base de datos
        admin.setPassword(newPassword);
        administradorRepository.save(admin);

        // Enviar correo electrónico al usuario con la nueva contraseña
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(EMAIL_SENDER);
        mailMessage.setTo(admin.getEmail());
        mailMessage.setSubject(EMAIL_SUBJECT);
        mailMessage.setText("Su nueva contraseña es: " + newPassword);
        mailSender.send(mailMessage);
    }

    private String generarContraseñaAleatoria() {
        // Generar una cadena aleatoria de 8 caracteres
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }



}

