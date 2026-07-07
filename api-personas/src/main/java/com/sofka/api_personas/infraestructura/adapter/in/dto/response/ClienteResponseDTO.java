package com.sofka.api_personas.infraestructura.adapter.in.dto.response;

public record ClienteResponseDTO(
        String usuario,
        boolean estado,
        String nombre,
        String genero,
        int edad,
        String identificacion,
        String direccion,
        String telefono) {
}
