package com.example.examen.dao;

import com.example.examen.Entity.Area;
import com.example.examen.Entity.Aula;
import com.example.examen.Entity.Laboratorio;
import com.example.examen.Repository.AreaRepository;
import com.example.examen.Repository.AulaRepository;
import com.example.examen.Repository.LaboratorioRepository;
import com.example.examen.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
@RestController
@RequestMapping("/areas")
public class AreaController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private LaboratorioRepository laboratorioRepository;


    public static class RegistroAreaRequest {
        private Area area;
        private List<Aula> aulas;
        private List<Laboratorio> laboratorios;

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public List<Aula> getAulas() {
            return aulas;
        }

        public void setAulas(List<Aula> aulas) {
            this.aulas = aulas;
        }

        public List<Laboratorio> getLaboratorios() {
            return laboratorios;
        }

        public void setLaboratorios(List<Laboratorio> laboratorios) {
            this.laboratorios = laboratorios;
        }
    }
    @PostMapping("/registro")
    @Transactional
    public void registrarAreaConAulasYLaboratorios(@RequestBody RegistroAreaRequest request) {
        Area area = request.getArea();
        List<Aula> aulas = request.getAulas();
        List<Laboratorio> laboratorios = request.getLaboratorios();
        if (area == null) {
            area = new Area(); // Crear un nuevo objeto Area si es nulo
        }
        areaRepository.save(area);
        if (aulas != null) {
            for (Aula aula : aulas) {
                aula.setArea(area); // Asignar el objeto Area a cada aula
            }
            aulaRepository.saveAll(aulas);
        }
        if (laboratorios != null) {
            for (Laboratorio lab : laboratorios) {
                lab.setArea(area); // Asignar el objeto Area a cada laboratorio
            }
            laboratorioRepository.saveAll(laboratorios);
        }
    }


    // Otros métodos
}
