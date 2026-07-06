package com.sofka.api_transaccional.infraestructura.adapter.in.dto.response;

import java.math.BigDecimal;

public record CuentaResponseDTO(
        String numeroCuenta,
        int tipoCuenta,
        BigDecimal saldoInicial,
        boolean estado) {
}
