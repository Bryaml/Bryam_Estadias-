package com.example.examen.dto;

import com.example.examen.Entity.Aula;

public class AulaDTO {
    private Long id;
    private String nombre;
    private int capacidad;
    private AreaDTO area;

    public AreaDTO getArea() {
        return area;
    }

    public void setArea(AreaDTO area) {
        this.area = area;
    }

    public static AulaDTO toDTO(Aula aula) {
        AulaDTO dto = new AulaDTO();
        dto.setId(aula.getId());
        dto.setNombre(aula.getNombre());
        dto.setCapacidad(aula.getCapacidad());
        // ...otros campos...
        return dto;
    }
    // ...getters y setters...

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

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}