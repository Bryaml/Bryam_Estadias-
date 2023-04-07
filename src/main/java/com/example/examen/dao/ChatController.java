package com.example.examen.dao;

import com.example.examen.Entity.*;
import com.example.examen.Repository.DocenteRepository;
import com.example.examen.Repository.IncidenciaRepository;
import com.example.examen.Repository.MensajeRepository;
import com.example.examen.Repository.TecnicoRepository;
import com.example.examen.request.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private IncidenciaRepository incidenciaRepository;
    @MessageMapping("/chat/enviar-mensaje")
    public void enviarMensaje(MensajeRequest mensajeRequest) {
        try {
            System.out.println("MensajeRequest: " + mensajeRequest); // Añade esta línea

            Docente docente = docenteRepository.findById(mensajeRequest.getDocenteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado"));

            Tecnico tecnico = tecnicoRepository.findById(mensajeRequest.getTecnicoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado"));

            // Verificar si existe una incidencia activa entre el docente y el técnico
            List<Incidencia> incidenciasActivas = incidenciaRepository.findByDocenteAndTecnicoAndEstado(docente, tecnico, EstadoIncidencia.ACTIVA);

            if (incidenciasActivas.isEmpty()) {
                throw new IllegalStateException("No hay una incidencia activa entre el docente y el técnico");
            }

            Incidencia incidenciaActiva = incidenciasActivas.get(0); // Utiliza la primera incidencia activa en la lista

            Mensaje mensaje = new Mensaje();
            mensaje.setDocente(docente);
            mensaje.setTecnico(tecnico);
            mensaje.setContenido(mensajeRequest.getContenido());
            mensaje.setFechaEnvio(LocalDateTime.now());

            Mensaje mensajeGuardado = mensajeRepository.save(mensaje);

            String chatId = String.format("chat_%d_%d", docente.getId(), tecnico.getId());
            template.convertAndSend("/topic/chat/" + chatId, mensajeGuardado);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excepción: " + e.getMessage()); // Añade esta línea
            throw new RuntimeException("Error al enviar el mensaje");
        }
    }


    @MessageMapping("/chat/usuario-escribiendo")
    public void usuarioEscribiendo(EscribiendoRequest escribiendoRequest) {
        // Envía un mensaje a través del WebSocket utilizando el siguiente código:
        template.convertAndSend("/topic/escribiendo/" + escribiendoRequest.getChatId(), escribiendoRequest);
    }

    // ConversationController.java

    @GetMapping("/api/conversations/{docenteId}/{tecnicoId}")
    public ResponseEntity<List<Mensaje>> getConversationsByDocenteIdAndTecnicoId(
            @PathVariable Long docenteId,
            @PathVariable Long tecnicoId) {
        List<Mensaje> conversations = mensajeRepository.findMensajesByDocenteIdAndTecnicoId(docenteId, tecnicoId);
        return ResponseEntity.ok(conversations);
    }


}
