package com.example.examen.dto;

import com.example.examen.Entity.Docente;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DocenteSerializer extends JsonSerializer<Docente> {
    @Override
    public void serialize(Docente docente, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (docente == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", docente.getId());
            jsonGenerator.writeStringField("nombres", docente.getNombres());
            jsonGenerator.writeStringField("apellidos", docente.getApellidos());
            jsonGenerator.writeStringField("email", docente.getEmail());
            // Agrega los demás campos de Docente que quieras serializar
            jsonGenerator.writeEndObject();
        }
    }
}
