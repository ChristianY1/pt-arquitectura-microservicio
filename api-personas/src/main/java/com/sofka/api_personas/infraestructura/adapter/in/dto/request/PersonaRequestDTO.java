package com.sofka.api_personas.infraestructura.adapter.in.dto.request;

public record PersonaRequestDTO(
        String nombre,
        String genero,
        int edad,
        String identificacion,
        String direccion,
        String telefono) {

}
