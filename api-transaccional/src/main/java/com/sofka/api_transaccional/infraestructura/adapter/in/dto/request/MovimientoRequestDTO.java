package com.sofka.api_transaccional.infraestructura.adapter.in.dto.request;

import java.math.BigDecimal;

public record MovimientoRequestDTO(
        String tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldo,
        String numeroCuenta) {
}
