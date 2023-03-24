package com.example.examen.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
@Component
public class IncidenciaNotificationHandler extends TextWebSocketHandler {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // suscribirse al canal de notificaciones de incidencias
        messagingTemplate.convertAndSendToUser(session.getPrincipal().getName(), "/queue/incidencia-notifications", "Conectado");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // no se implementa nada aqui ya que no se espera que el cliente envíe mensajes
    }
}



 */