package com.sofka.api_personas.infraestructura.adapter.out.messaging.dto;

public record ClienteEventoDTO(
        Long clienteId,
        String identificacion,
        String nombre,
        boolean estado) {
}
