package com.sofka.api_transaccional.infraestructura.adapter.in.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReportesMovimientosDto(
        LocalDateTime fechaMovimiento,
        String nombre,
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        boolean estado,
        BigDecimal valor,
        BigDecimal saldoDisponible) {
}
