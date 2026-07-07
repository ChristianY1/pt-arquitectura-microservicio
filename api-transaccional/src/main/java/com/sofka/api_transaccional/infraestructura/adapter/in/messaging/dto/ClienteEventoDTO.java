package com.sofka.api_transaccional.infraestructura.adapter.in.messaging.dto;

public record ClienteEventoDTO(
        Long clienteId,
        String identificacion,
        String nombre,
        boolean estado) {
}
