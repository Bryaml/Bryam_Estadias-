package com.example.examen.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;


@Entity
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int capacidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    @JsonIgnoreProperties("aulas")
    private Area area;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL)
    private List<Incidencia> incidencias;

    // Constructor por defecto
    public Aula() {}

    // Constructor con parámetros sin lista de incidencias
    public Aula(String nombre, int capacidad, Area area) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.area = area;
    }

    // Constructor con parámetros con lista de incidencias
    public Aula(String nombre, int capacidad, Area area, List<Incidencia> incidencias) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.area = area;
        this.incidencias = incidencias;
    }



    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public int getNumeroIncidencias() {
        return incidencias.size();
    }
}