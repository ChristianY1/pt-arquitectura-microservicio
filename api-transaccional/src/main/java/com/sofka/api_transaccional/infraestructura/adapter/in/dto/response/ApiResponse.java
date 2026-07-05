package com.sofka.api_transaccional.infraestructura.adapter.in.dto.response;

public record ApiResponse<T>(
        String mensaje,
        T data) {
}
