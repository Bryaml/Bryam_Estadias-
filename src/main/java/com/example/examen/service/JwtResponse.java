package com.example.examen.service;

import java.util.List;

public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private String rol;

    public JwtResponse(String token, Long id, String username, String email, String rol) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return rol;
    }

    public void setRoles(String roles) {
        this.rol = roles;
    }
}
