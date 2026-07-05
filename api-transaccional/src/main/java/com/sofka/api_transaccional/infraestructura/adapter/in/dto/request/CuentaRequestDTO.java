package com.sofka.api_transaccional.infraestructura.adapter.in.dto.request;

import java.math.BigDecimal;

public record CuentaRequestDTO(
        String numeroCuenta,
        int tipoCuenta,
        BigDecimal saldoInicial,
        String estado,
        String identificacion) {
}
