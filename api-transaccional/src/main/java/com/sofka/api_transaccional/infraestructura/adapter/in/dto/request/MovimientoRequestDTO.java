package com.sofka.api_transaccional.infraestructura.adapter.in.dto.request;

import java.math.BigDecimal;

public record MovimientoRequestDTO(
        BigDecimal valor,
        String numeroCuenta) {
}
