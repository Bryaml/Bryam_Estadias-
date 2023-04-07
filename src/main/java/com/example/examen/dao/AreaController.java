package com.example.examen.dao;

import com.example.examen.Entity.Area;
import com.example.examen.Entity.Aula;
import com.example.examen.Entity.Laboratorio;
import com.example.examen.Repository.AreaRepository;
import com.example.examen.Repository.AulaRepository;
import com.example.examen.Repository.LaboratorioRepository;
import com.example.examen.dto.AreaDTO;
import com.example.examen.service.AreaService;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/areas")
public class AreaController {


    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @PostMapping("/registro")
    @Transactional
    public void registrarAreaConAulasYLaboratorios(@RequestBody Area area) {
        List<Aula> aulas = area.getAulas();
        List<Laboratorio> laboratorios = area.getLaboratorios();

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

    public static AreaDTO toDTO(Area area) {
        AreaDTO dto = new AreaDTO();
        dto.setId(area.getId());
        dto.setNombre(area.getNombre());
        // ...otros campos...
        return dto;
    }

    @GetMapping
    public ResponseEntity<List<AreaDTO>> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        List<AreaDTO> areaDTOs = areas.stream().map(AreaController::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(areaDTOs);
    }
}
