package com.example.examen.Entity;

public class EscribiendoRequest {
    private Long docenteId;
    private Long tecnicoId;
    private String chatId;
    private boolean escribiendo;

    // Getters y setters

    public EscribiendoRequest() {
    }

    public EscribiendoRequest(Long docenteId, Long tecnicoId, String chatId, boolean escribiendo) {
        this.docenteId = docenteId;
        this.tecnicoId = tecnicoId;
        this.chatId = chatId;
        this.escribiendo = escribiendo;
    }

    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public boolean isEscribiendo() {
        return escribiendo;
    }

    public void setEscribiendo(boolean escribiendo) {
        this.escribiendo = escribiendo;
    }
}
