package com.example.examen.dao;
import com.example.examen.Entity.*;
import com.example.examen.Repository.*;
import com.example.examen.request.IncidenciaRequest;
import com.example.examen.request.ResourceNotFoundException;
import com.example.examen.service.AulaService;
import com.example.examen.service.IncidenciaService;
import com.example.examen.service.LaboratorioService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @Autowired
    private AulaService aulaService;


    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private LaboratorioService laboratorioService;
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Autowired
    private AulaRepository aulaRepository;


    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/crear-incidencia")
    public ResponseEntity<Incidencia> crearIncidencia(@RequestBody IncidenciaRequest incidenciaRequest) {

        try {
            // Obtener el usuario docente
            Docente docente = docenteRepository.findByEmail(incidenciaRequest.getEmailDocente())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el docente con el email " + incidenciaRequest.getEmailDocente()));

            // Verificar si el área de la incidencia existe
            Area area = areaRepository.findById(incidenciaRequest.getAreaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Area no encontrada"));

            // Verificar si el aula pertenece al área seleccionada
            Optional<Aula> aulaOptional = aulaRepository.findByNombreAndAreaId(incidenciaRequest.getNombreAula(), incidenciaRequest.getAreaId());

            // Verificar si el laboratorio pertenece al área seleccionada
            Optional<Laboratorio> laboratorioOptional = laboratorioRepository.findByNombreAndAreaId(incidenciaRequest.getNombreLaboratorio(), incidenciaRequest.getAreaId());

            // Verificar si se encontró el aula o el laboratorio
            if (aulaOptional.isPresent() && laboratorioOptional.isPresent()) {
                throw new IllegalArgumentException("No se puede registrar incidencia en ambos aula y laboratorio al mismo tiempo");
            } else if (aulaOptional.isPresent()) {
                // Crear la incidencia con el aula
                System.out.println("Aula encontrada: " + aulaOptional.get().getNombre());
                Incidencia incidencia = new Incidencia();
                incidencia.setDescripcion(incidenciaRequest.getDescripcion());
                incidencia.setEstado(EstadoIncidencia.PENDIENTE);
                incidencia.setFechaCreacion(LocalDateTime.now());
                incidencia.setDocente(docente);
                incidencia.setAula(aulaOptional.get());
                incidenciaRepository.save(incidencia);
                template.convertAndSend("/topic/notificaciones", "Se ha registrado una nueva incidencia");
                return ResponseEntity.ok(incidencia);
            } else if (laboratorioOptional.isPresent()) {
                // Crear la incidencia con el laboratorio
                System.out.println("Laboratorio encontrado: " + laboratorioOptional.get().getNombre());
                Incidencia incidencia = new Incidencia();
                incidencia.setDescripcion(incidenciaRequest.getDescripcion());
                incidencia.setEstado(EstadoIncidencia.PENDIENTE);
                incidencia.setFechaCreacion(LocalDateTime.now());
                incidencia.setDocente(docente);
                incidencia.setLaboratorio(laboratorioOptional.get());
                incidenciaRepository.save(incidencia);
                template.convertAndSend("/topic/notificaciones", "Se ha registrado una nueva incidencia");
                return ResponseEntity.ok(incidencia);
            } else {
                throw new ResourceNotFoundException("Aula o laboratorio no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear incidencia");
        }

        // Enviar notificación a través del WebSocket
       // template.convertAndSend("/topic/incidencias", "Nueva incidencia creada");

     //   return ResponseEntity.ok(incidencia);
// Enviar notificación a través de WebSocket


    }

    @PutMapping("/{id}/asignar-tecnico")
    public ResponseEntity<Incidencia> asignarTecnico(@PathVariable Long id, @RequestParam String emailTecnico) {
        try {
            // Obtener la incidencia por ID
            Incidencia incidencia = incidenciaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró la incidencia con ID " + id));

            // Verificar si la incidencia ya ha sido aceptada por un técnico
            if (incidencia.getEstado() != EstadoIncidencia.PENDIENTE) {
                throw new IllegalStateException("La incidencia ya ha sido aceptada o resuelta");
            }

            // Obtener el técnico por email
            Tecnico tecnico = tecnicoRepository.findByEmail(emailTecnico)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el técnico con el email " + emailTecnico));

            // Asignar el técnico a la incidencia
            incidencia.setTecnico(tecnico);
            incidencia.setEstado(EstadoIncidencia.ACTIVA);
            incidencia.setFechaActiva(LocalDateTime.now()); // <-- Agregar fecha
            incidenciaRepository.save(incidencia);

            return ResponseEntity.ok(incidencia);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al asignar técnico a incidencia");
        }
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<Incidencia> resolverIncidencia(@PathVariable Long id) {
        try {
            // Obtener la incidencia por ID
            Incidencia incidencia = incidenciaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró la incidencia con ID " + id));

            // Verificar si la incidencia ya ha sido resuelta
            if (incidencia.getEstado() == EstadoIncidencia.COMPLETADA) {
                throw new IllegalStateException("La incidencia ya ha sido resuelta");
            }

            // Marcar la incidencia como resuelta
            incidencia.setEstado(EstadoIncidencia.COMPLETADA);
            incidencia.setFechaCompletada(LocalDateTime.now());
            incidenciaRepository.save(incidencia);

            return ResponseEntity.ok(incidencia);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al resolver incidencia");
        }
    }


}