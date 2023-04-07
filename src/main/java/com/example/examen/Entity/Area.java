package com.example.examen.Entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Area implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;


    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Aula> aulas;

    @OneToMany(mappedBy = "area")
    @JsonManagedReference
    @JsonIgnore
    private List<Laboratorio> laboratorios;

    // Constructor por defecto
    public Area() {}

    // Constructor con parámetros sin lista de aulas y laboratorios
    public Area(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Constructor con parámetros con listas de aulas y laboratorios
    public Area(Long id, String nombre, List<Aula> aulas, List<Laboratorio> laboratorios) {
        this.id = id;
        this.nombre = nombre;
        this.aulas = aulas;
        this.laboratorios = laboratorios;
    }

    public List<Laboratorio> getLaboratorios() {
        return laboratorios;
    }

    public void setLaboratorios(List<Laboratorio> laboratorios) {
        this.laboratorios = laboratorios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }


}