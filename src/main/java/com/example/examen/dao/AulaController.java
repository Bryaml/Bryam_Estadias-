package com.example.examen.dao;

import com.example.examen.Entity.Area;
import com.example.examen.Entity.Aula;
import com.example.examen.Entity.Laboratorio;
import com.example.examen.Repository.AreaRepository;
import com.example.examen.Repository.AulaRepository;
import com.example.examen.dto.AulaDTO;
import com.example.examen.dto.LaboratorioDTO;
import com.example.examen.request.AulaRequest;
import com.example.examen.request.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AreaRepository areaRepository;


    @PostMapping
    public ResponseEntity<AulaDTO> registrarAula(@RequestBody AulaRequest aulaRequest) {
        Area area = areaRepository.findById(aulaRequest.getAreaId())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada"));

        Aula aula = new Aula();
        aula.setNombre(aulaRequest.getNombre());
        aula.setArea(area);
        aulaRepository.save(aula);

        AulaDTO aulaDTO = toDTO(aula);
        return ResponseEntity.ok(aulaDTO);
    }

    @GetMapping
    public ResponseEntity<List<AulaDTO>> obtenerAulas() {
        List<Aula> aulas = aulaRepository.findAll();
        List<AulaDTO> aulaDTOs = aulas.stream().map(AulaController::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(aulaDTOs);
    }

    public static AulaDTO toDTO(Aula aula) {
        AulaDTO dto = new AulaDTO();
        dto.setId(aula.getId());
        dto.setNombre(aula.getNombre());
        dto.setArea(AreaController.toDTO(aula.getArea())); // Agrega esta línea
        // ...otros campos...
        return dto;
    }

}

