package com.example.examen.service;

import com.example.examen.Entity.Administrador;
import com.example.examen.Entity.Docente;
import com.example.examen.Entity.Tecnico;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class AuthService {


    private final String JWT_SECRET = "mi_secreto_secreto";

    public String generateToken(Object principal) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 86400000); // Expira en 24 horas
        String email = null;
        Long id = null;
        String username = null;
        String rol = null;

        if (principal instanceof Docente) {
            Docente docente = (Docente) principal;
            if (docente.getEmail() != null) {
                email = docente.getEmail();
            }
            id = docente.getId();
            username = docente.getNombres();
            rol = "docente";
        } else if (principal instanceof Tecnico) {
            Tecnico tecnico = (Tecnico) principal;
            if (tecnico.getEmail() != null) {
                email = tecnico.getEmail();
            }
            id = tecnico.getId();
            username = tecnico.getNombres();
            rol = "tecnico";
        } else if (principal instanceof Administrador) {
            Administrador admin = (Administrador) principal;
            if (admin.getEmail() != null) {
                email = admin.getEmail();
            }
            id = admin.getId();
            username = admin.getNombres();
            rol = "admin";
        }

        return Jwts.builder()
                .claim("id", id)
                .claim("username", username)
                .claim("email", email)
                .claim("rol", rol)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
    public String generatePasswordResetToken(String email) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1800000); // Expira en 30 minutos
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }



}
