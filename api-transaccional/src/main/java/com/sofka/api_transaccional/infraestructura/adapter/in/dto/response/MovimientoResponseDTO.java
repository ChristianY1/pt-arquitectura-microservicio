package com.sofka.api_transaccional.infraestructura.adapter.in.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponseDTO(
                LocalDateTime fecha,
                String tipoMovimiento,
                BigDecimal valor,
                BigDecimal saldo) {
}
