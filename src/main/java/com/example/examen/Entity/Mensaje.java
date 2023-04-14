package com.example.examen.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensaje")
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = true)
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = true)
    private Tecnico tecnico;

    @Column
    private String contenido;

    @Column
    private LocalDateTime fechaEnvio;

    @Column
    private boolean esTecnico;

    public boolean getEsTecnico() {
        return esTecnico;
    }

    public void setEsTecnico(boolean esTecnico) {
        this.esTecnico = esTecnico;
    }
// Getters y setters


    public Mensaje() {
    }

    public Mensaje(Long id, Docente docente, Tecnico tecnico, String contenido, LocalDateTime fechaEnvio) {
        this.id = id;
        this.docente = docente;
        this.tecnico = tecnico;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setTecnicoId(Long id) {
    }
}
