package com.example.examen.dto;

import com.example.examen.Entity.Area;

public class AreaDTO {
    private Long id;
    private String nombre;

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
    public static AreaDTO toDTO(Area area) {
        AreaDTO dto = new AreaDTO();
        dto.setId(area.getId());
        dto.setNombre(area.getNombre());
        // ...otros campos...
        return dto;
    }

}