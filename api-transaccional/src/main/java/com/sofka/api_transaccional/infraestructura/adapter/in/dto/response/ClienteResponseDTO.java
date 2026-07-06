package com.sofka.api_transaccional.infraestructura.adapter.in.dto.response;

public record ClienteResponseDTO(
        String usuario,
        String estado,
        String nombre,
        String genero,
        int edad,
        String identificacion,
        String direccion,
        String telefono) {
}
