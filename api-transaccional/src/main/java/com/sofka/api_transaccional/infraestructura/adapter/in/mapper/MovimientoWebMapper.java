package com.sofka.api_transaccional.infraestructura.adapter.in.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.model.TipoMovimiento;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.MovimientoRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.MovimientoResponseDTO;

public class MovimientoWebMapper {

    public Movimiento toDomainMovimiento(MovimientoRequestDTO movimientoRequestDTO) {
        BigDecimal valor = movimientoRequestDTO.valor().setScale(2, RoundingMode.HALF_UP);
        TipoMovimiento tipoMovimiento = valor.compareTo(BigDecimal.ZERO) >= 0 ? TipoMovimiento.DEPOSITO : TipoMovimiento.RETIRO;
        return Movimiento.builder()
                .fecha(LocalDateTime.now())
                .tipoMovimiento(tipoMovimiento)
                .valor(valor)
                .build();
    }

    public MovimientoResponseDTO toResponseMovimiento(Movimiento movimiento) {
        return new MovimientoResponseDTO(
                movimiento.getFecha(),
                movimiento.getTipoMovimiento().getDescripcion(),
                movimiento.getValor(),
                movimiento.getSaldo());
    }

}
