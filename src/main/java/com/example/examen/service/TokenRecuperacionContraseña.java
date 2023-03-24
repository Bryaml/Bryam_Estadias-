package com.example.examen.service;

import java.time.LocalDateTime;

public class TokenRecuperacionContraseña {
    private String token;
    private LocalDateTime fechaExpiracion;

    public TokenRecuperacionContraseña(String token, int minutosExpiracion) {
        this.token = token;
        this.fechaExpiracion = LocalDateTime.now().plusMinutes(minutosExpiracion);
    }

    public boolean isExpirado() {
        return LocalDateTime.now().isAfter(this.fechaExpiracion);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}
