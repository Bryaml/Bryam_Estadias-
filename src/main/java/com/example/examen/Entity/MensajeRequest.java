package com.example.examen.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

public class MensajeRequest {
    private Long docenteId;
    private Long tecnicoId;
    private String contenido;

    // Getters y setters

    public MensajeRequest() {
    }

    public MensajeRequest(Long docenteId, Long tecnicoId, String contenido) {
        this.docenteId = docenteId;
        this.tecnicoId = tecnicoId;
        this.contenido = contenido;
    }



    // Getters y setters


    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
