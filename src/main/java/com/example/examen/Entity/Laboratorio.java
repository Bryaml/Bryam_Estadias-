package com.example.examen.Entity;

import com.example.examen.dto.AreaSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Laboratorio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int capacidad;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    @JsonSerialize(using = AreaSerializer.class)
    @JsonIgnore
    private Area area;

    @OneToMany(mappedBy = "laboratorio", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Incidencia> incidencias;

    // Constructor por defecto
    public Laboratorio() {}

    // Constructor con parámetros sin lista de incidencias
    public Laboratorio(String nombre, int capacidad, Area area) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.area = area;
    }

    // Constructor con parámetros con lista de incidencias
    public Laboratorio(String nombre, int capacidad, Area area, List<Incidencia> incidencias) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
}