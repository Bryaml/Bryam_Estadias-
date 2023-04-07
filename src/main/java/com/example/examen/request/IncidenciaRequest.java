package com.example.examen.request;
import com.example.examen.Entity.EstadoIncidencia;

import java.lang.Long;

public class IncidenciaRequest {

    private String titulo;
    private String descripcion;
    private String prioridad;
    private String emailDocente;
    private String nombreEdificio;
    private String nombreAula;
    private String nombreLaboratorio;
    private Long areaId;
    // Agrega el campo espacioId
    private Long espacioId;
    // Otros campos existentes

    // Getter y setter para espacioId
    public Long getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(Long espacioId) {
        this.espacioId = espacioId;
    }

    public String getNombreAula() {
        return nombreAula;
    }

    public String getNombreLaboratorio() {
        return nombreLaboratorio;
    }
    private EstadoIncidencia estado;
    private Long aulaId;

    public IncidenciaRequest() {
    }

    public IncidenciaRequest(String titulo, String descripcion, String prioridad, String emailDocente, String nombreEdificio, String nombreAula, String nombreLaboratorio, Long areaId, Long aulaId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.emailDocente = emailDocente;
        this.nombreEdificio = nombreEdificio;
        this.nombreAula = nombreAula;
        this.nombreLaboratorio = nombreLaboratorio;
        this.areaId = areaId;
        this.aulaId = aulaId;
    }
    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public Long getAulaId() {
        return aulaId;
    }

    public void setAulaId(Long aulaId) {
        this.aulaId = aulaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEmailDocente() {
        return emailDocente;
    }

    public void setEmailDocente(String emailDocente) {
        this.emailDocente = emailDocente;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio;
    }

    public void setNombreAula(String nombreAula) {
        this.nombreAula = nombreAula;
    }

    public void setNombreLaboratorio(String nombreLaboratorio) {
        this.nombreLaboratorio = nombreLaboratorio;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    // resto del código
}
