package com.sofka.api_transaccional.infraestructura.adapter.in.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponseDTO(
                LocalDateTime fechaMovimiento,
                String tipoMovimiento,
                BigDecimal valor,
                BigDecimal saldoDisponible) {
}
