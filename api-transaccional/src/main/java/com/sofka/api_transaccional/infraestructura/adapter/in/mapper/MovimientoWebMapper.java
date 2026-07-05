package com.sofka.api_transaccional.infraestructura.adapter.in.mapper;

import java.time.LocalDateTime;

import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.MovimientoRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.MovimientoResponseDTO;

public class MovimientoWebMapper {

    public Movimiento toDomainMovimiento(MovimientoRequestDTO movimientoRequestDTO) {
        return Movimiento.builder()
                .fecha(LocalDateTime.now())
                .tipoMovimiento(movimientoRequestDTO.tipoMovimiento())
                .valor(movimientoRequestDTO.valor())
                .saldo(movimientoRequestDTO.saldo())
                .build();
    }

    public MovimientoResponseDTO toResponseMovimiento(Movimiento movimiento) {
        return new MovimientoResponseDTO(
                movimiento.getMovimientoId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getSaldo(),
                movimiento.getCuentaId());
    }

}
