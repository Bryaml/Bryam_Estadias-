package com.example.examen.dto;

import com.example.examen.Entity.Laboratorio;

public class LaboratorioDTO {
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

    public static LaboratorioDTO toDTO(Laboratorio laboratorio) {
        LaboratorioDTO dto = new LaboratorioDTO();
        dto.setId(laboratorio.getId());
        dto.setNombre(laboratorio.getNombre());
        dto.setCapacidad(laboratorio.getCapacidad());
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