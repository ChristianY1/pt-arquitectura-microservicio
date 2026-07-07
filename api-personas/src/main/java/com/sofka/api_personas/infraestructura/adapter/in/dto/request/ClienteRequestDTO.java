package com.sofka.api_personas.infraestructura.adapter.in.dto.request;

public record ClienteRequestDTO(
        String contrasenia,
        String usuario,
        boolean estado,
        PersonaRequestDTO persona) {
}
