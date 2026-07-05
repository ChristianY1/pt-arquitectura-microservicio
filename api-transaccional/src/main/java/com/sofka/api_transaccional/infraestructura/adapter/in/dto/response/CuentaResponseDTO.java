package com.sofka.api_transaccional.infraestructura.adapter.in.dto.response;

import java.math.BigDecimal;

public record CuentaResponseDTO(
        Long cuentaId,
        String numeroCuenta,
        int tipoCuenta,
        BigDecimal saldoInicial,
        String estado,
        Long clienteId) {
}
