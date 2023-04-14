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
import java.util.*;
import java.util.stream.Collectors;

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
            Optional<Aula> aulaOptional = aulaRepository.findByNombre(incidenciaRequest.getNombreAula());
            Optional<Laboratorio> laboratorioOptional = laboratorioRepository.findByNombre(incidenciaRequest.getNombreLaboratorio());
            // Crear la incidencia
            Incidencia incidencia = new Incidencia();
            incidencia.setDescripcion(incidenciaRequest.getDescripcion());
            incidencia.setEstado(EstadoIncidencia.PENDIENTE);
            incidencia.setFechaCreacion(LocalDateTime.now());
            incidencia.setDocente(docente);

            // Verificar si se encontró el aula
            if (aulaOptional.isPresent()) {
                incidencia.setAula(aulaOptional.get());
            } else if (laboratorioOptional.isPresent()) {
                incidencia.setLaboratorio(laboratorioOptional.get());
            } else {
                throw new ResourceNotFoundException("Aula o laboratorio no encontrado");
            }

            incidenciaRepository.save(incidencia);
            template.convertAndSend("/topic/notificaciones", "Se ha registrado una nueva incidencia");
            return ResponseEntity.ok(incidencia);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear incidencia: " + e.getMessage(), e);
        }


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
            incidencia.setFechaActiva(LocalDateTime.now());
            incidenciaRepository.save(incidencia);

            // Enviar notificación al docente
            // Enviar notificación al docente
            System.out.println("Enviando notificación al docente: " + incidencia.getDocente().getEmail()); // Agrega este registro
            template.convertAndSendToUser(incidencia.getDocente().getEmail(), "/queue/notifications", "Un técnico ha sido asignado a tu incidencia");



            System.out.println("Notificación enviada al docente: " + incidencia.getDocente().getEmail());
            return ResponseEntity.ok(incidencia);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enviar notificación al docente: " );
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

    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> actualizarIncidencia(@PathVariable Long id, @RequestBody IncidenciaRequest incidenciaRequest) {
        try {
            // Obtener la incidencia por ID
            Incidencia incidencia = incidenciaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró la incidencia con ID " + id));

            // Actualizar el estado de la incidencia si es necesario
            if (incidenciaRequest.getEstado() != null) {
                incidencia.setEstado(incidenciaRequest.getEstado());
            }



            // Guardar la incidencia actualizada
            incidenciaRepository.save(incidencia);
            return ResponseEntity.ok(incidencia);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar la incidencia");
        }
    }

    @GetMapping("/docente/{docenteId}/tecnicos")
    public ResponseEntity<List<Tecnico>> getTecnicosWithActiveIncidencia(@PathVariable Long docenteId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado"));

        List<Tecnico> tecnicos = incidenciaRepository.findDistinctTecnicosByDocenteIdAndEstado(docenteId, EstadoIncidencia.ACTIVA);

        return ResponseEntity.ok(tecnicos);
    }

    @GetMapping("/tecnico/{tecnicoId}/docentes")
    public ResponseEntity<List<Docente>> getDocentesWithActiveIncidencia(@PathVariable Long tecnicoId) {
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado"));

        List<Docente> docentes = incidenciaRepository.findDistinctDocentesByTecnicoIdAndEstado(tecnicoId, EstadoIncidencia.ACTIVA);

        return ResponseEntity.ok(docentes);
    }
    @GetMapping
    public ResponseEntity<List<Incidencia>> getAllIncidencias(
            @RequestParam(required = false) EstadoIncidencia estado,
            @RequestParam(required = false) String emailTecnico
    ) {
        List<Incidencia> incidencias;

        if (estado != null && emailTecnico != null) {
            // Filtrar por estado y técnico asignado
            incidencias = incidenciaRepository.findAllByEstadoAndTecnicoEmail(estado, emailTecnico);
        } else if (estado != null) {
            // Filtrar solo por estado
            incidencias = incidenciaRepository.findAllByEstado(estado);
        } else {
            // Si no se proporcionan filtros, devuelve todas las incidencias
            incidencias = incidenciaRepository.findAll();
        }

        return ResponseEntity.ok(incidencias);
    }
    @GetMapping("/docente")
    public ResponseEntity<List<Incidencia>> getIncidenciasDocente(
            @RequestParam(required = true) String emailDocente
    ) {
        List<Incidencia> incidencias = incidenciaRepository.findAllByDocenteEmail(emailDocente);

        return ResponseEntity.ok(incidencias);
    }

    // Método para obtener todas las incidencias para el administrador
    @GetMapping("/admin")
    public ResponseEntity<List<Incidencia>> getAllIncidenciasAdmin(
            @RequestParam(required = false) EstadoIncidencia estado,
            @RequestParam(required = false) String emailTecnico
    ) {
        List<Incidencia> incidencias;

        if (estado != null && emailTecnico != null) {
            // Filtrar por estado y técnico asignado
            incidencias = incidenciaRepository.findAllByEstadoAndTecnicoEmail(estado, emailTecnico);
        } else if (estado != null) {
            // Filtrar solo por estado
            incidencias = incidenciaRepository.findAllByEstado(estado);
        } else {
            // Si no se proporcionan filtros, devuelve todas las incidencias
            incidencias = incidenciaRepository.findAll();
        }

        return ResponseEntity.ok(incidencias);
    }



    @GetMapping("/incidencias-por-area")
    public ResponseEntity<Map<String, Long>> obtenerIncidenciasPorArea() {
        List<Area> areas = areaRepository.findAll();
        Map<String, Long> incidenciasPorArea = new HashMap<>();

        for (Area area : areas) {
            long numIncidencias = incidenciaRepository.countByAulaArea(area);
            incidenciasPorArea.put(area.getNombre(), numIncidencias);
        }

        return ResponseEntity.ok(incidenciasPorArea);
    }


    @GetMapping("/incidencias-por-docente")
    public ResponseEntity<Map<String, Long>> obtenerIncidenciasPorDocente() {
        List<Docente> docentes = docenteRepository.findAll();
        Map<String, Long> incidenciasPorDocente = new HashMap<>();

        for (Docente docente : docentes) {
            long numIncidencias = incidenciaRepository.countByDocente(docente);
            incidenciasPorDocente.put(docente.getNombres(), numIncidencias);
        }

        return ResponseEntity.ok(incidenciasPorDocente);
    }

    @GetMapping("/incidencias-por-division-academica")
    public ResponseEntity<Map<String, Long>> obtenerIncidenciasPorDivisionAcademica() {
        List<String> divisionesAcademicas = docenteRepository.findDistinctDivisionesAcademicas();
        Map<String, Long> incidenciasPorDivision = new HashMap<>();

        for (String division : divisionesAcademicas) {
            long numIncidencias = incidenciaRepository.countByDocenteDivisionAcademica(division);
            incidenciasPorDivision.put(division, numIncidencias);
        }

        return ResponseEntity.ok(incidenciasPorDivision);
    }



}