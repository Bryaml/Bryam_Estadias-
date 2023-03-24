package com.example.examen.request;

import com.example.examen.Entity.Area;
import com.example.examen.Entity.Aula;
import com.example.examen.Entity.Laboratorio;

public class RegistroAreaRequest {
    private Area area;
    private Aula aula;
    private Laboratorio laboratorio;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
}
