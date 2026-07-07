package com.sofka.api_transaccional.infraestructura.adapter.in.messaging.dto;

public record ClienteEventoDTO(
        Long clienteId,
        boolean estado) {
}
