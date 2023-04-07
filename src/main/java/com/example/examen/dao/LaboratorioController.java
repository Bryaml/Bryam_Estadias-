package com.example.examen.dao;

import com.example.examen.Entity.Area;
import com.example.examen.Entity.Laboratorio;
import com.example.examen.Repository.AreaRepository;
import com.example.examen.Repository.LaboratorioRepository;
import com.example.examen.dto.LaboratorioDTO;
import com.example.examen.request.LaboratorioRequest;
import com.example.examen.request.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Autowired
    private AreaRepository areaRepository;

    @PostMapping
    public ResponseEntity<LaboratorioDTO> registrarLaboratorio(@RequestBody LaboratorioRequest laboratorioRequest) {
        Area area = areaRepository.findById(laboratorioRequest.getAreaId())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada"));

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNombre(laboratorioRequest.getNombre());
        laboratorio.setArea(area);
        laboratorioRepository.save(laboratorio);

        LaboratorioDTO laboratorioDTO = toDTO(laboratorio);
        return ResponseEntity.ok(laboratorioDTO);
    }

    @GetMapping
    public ResponseEntity<List<LaboratorioDTO>> obtenerLaboratorios() {
        List<Laboratorio> laboratorios = laboratorioRepository.findAll();
        List<LaboratorioDTO> laboratorioDTOs = laboratorios.stream().map(LaboratorioController::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(laboratorioDTOs);
    }

    public static LaboratorioDTO toDTO(Laboratorio laboratorio) {
        LaboratorioDTO dto = new LaboratorioDTO();
        dto.setId(laboratorio.getId());
        dto.setNombre(laboratorio.getNombre());
        dto.setArea(AreaController.toDTO(laboratorio.getArea())); // Agrega esta línea
        // ...otros campos...
        return dto;
    }

}