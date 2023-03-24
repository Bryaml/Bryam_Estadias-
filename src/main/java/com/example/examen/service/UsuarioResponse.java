package com.example.examen.service;

public class UsuarioResponse {
    private Long id;
    private String nombres;
    private String email;
    private String rol;

    public UsuarioResponse(Long id, String nombres, String email, String rol) {
        this.id = id;
        this.nombres = nombres;
        this.email = email;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
