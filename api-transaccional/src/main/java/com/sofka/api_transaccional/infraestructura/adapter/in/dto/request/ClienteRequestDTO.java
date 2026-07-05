package com.sofka.api_transaccional.infraestructura.adapter.in.dto.request;

public record ClienteRequestDTO(
        String contrasenia,
        String usuario,
        String estado,
        PersonaRequestDTO persona) {
}