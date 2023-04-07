package com.example.examen.Entity;

import com.example.examen.dto.DocenteSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "incidencias")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorio_id", nullable = true)
    @JsonIgnore
    private Laboratorio laboratorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id", nullable = true)
    @JsonIgnore
    private Aula aula;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente_id")
    @JsonSerialize(using = DocenteSerializer.class)
    private Docente docente;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_activa")
    private LocalDateTime fechaActiva;

    @Column(name = "fecha_completada")
    private LocalDateTime fechaCompletada;

    @Enumerated(EnumType.STRING)
    private EstadoIncidencia estado;

    @Column(columnDefinition="BLOB")
    private byte[] imagen;
    //constructors, getters and setters


    public Incidencia() {
    }

    public Incidencia(Long id, Aula aula, Laboratorio laboratorio, Docente docente, Administrador administrador, Tecnico tecnico, String descripcion, LocalDateTime fechaCreacion, LocalDateTime fechaActiva, LocalDateTime fechaCompletada, EstadoIncidencia estado, byte[] imagen) {
        this.id = id;
        this.aula = aula;
        this.laboratorio = laboratorio;
        this.docente = docente;
        this.administrador = administrador;
        this.tecnico = tecnico;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActiva = fechaActiva;
        this.fechaCompletada = fechaCompletada;
        this.estado = estado;
        this.imagen = imagen;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActiva() {
        return fechaActiva;
    }

    public void setFechaActiva(LocalDateTime fechaActiva) {
        this.fechaActiva = fechaActiva;
    }

    public LocalDateTime getFechaCompletada() {
        return fechaCompletada;
    }

    public void setFechaCompletada(LocalDateTime fechaCompletada) {
        this.fechaCompletada = fechaCompletada;
    }
}



