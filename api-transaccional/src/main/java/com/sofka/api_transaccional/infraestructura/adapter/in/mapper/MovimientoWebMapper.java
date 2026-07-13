package com.sofka.api_transaccional.infraestructura.adapter.in.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.sofka.api_transaccional.domain.exception.ReglaNegocioException;
import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.model.ReporteMovimiento;
import com.sofka.api_transaccional.domain.model.TipoMovimiento;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.MovimientoRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.MovimientoResponseDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.ReportesMovimientosDto;

public class MovimientoWebMapper {

    public Movimiento toDomainMovimiento(MovimientoRequestDTO movimientoRequestDTO) {
        if (movimientoRequestDTO.valor() == null || movimientoRequestDTO.valor().compareTo(BigDecimal.ZERO) == 0) {
            throw new ReglaNegocioException("El valor debe ser distinto de cero");
        }
        BigDecimal valor = movimientoRequestDTO.valor().setScale(2, RoundingMode.HALF_UP);
        TipoMovimiento tipoMovimiento = valor.compareTo(BigDecimal.ZERO) >= 0 ? TipoMovimiento.DEPOSITO : TipoMovimiento.RETIRO;
        return Movimiento.builder()
                .fechaMovimiento(LocalDateTime.now())
                .tipoMovimiento(tipoMovimiento)
                .valor(valor)
                .build();
    }

    public MovimientoResponseDTO toResponseMovimiento(Movimiento movimiento) {
        return new MovimientoResponseDTO(
                movimiento.getFechaMovimiento(),
                movimiento.getTipoMovimiento().getDescripcion(),
                movimiento.getValor(),
                movimiento.getSaldoDisponible());
    }

    public ReportesMovimientosDto toResponseReporteMovimiento(ReporteMovimiento reporteMovimiento) {
        return new ReportesMovimientosDto(
                reporteMovimiento.getFechaMovimiento(),
                reporteMovimiento.getNombre(),
                reporteMovimiento.getNumeroCuenta(),
                reporteMovimiento.getTipoCuenta(),
                reporteMovimiento.getSaldoInicial(),
                reporteMovimiento.isEstado(),
                reporteMovimiento.getValor(),
                reporteMovimiento.getSaldoDisponible());
    }

}
