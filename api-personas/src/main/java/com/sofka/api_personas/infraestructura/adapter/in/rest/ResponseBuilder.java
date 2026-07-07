package com.sofka.api_personas.infraestructura.adapter.in.rest;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

final class ResponseBuilder {

    private ResponseBuilder() {
    }

    static ResponseEntity<Map<String, Object>> build(HttpStatus status, String mensaje, Object data) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("mensaje", mensaje);
        body.put("data", data);
        return ResponseEntity.status(status).body(body);
    }

}
