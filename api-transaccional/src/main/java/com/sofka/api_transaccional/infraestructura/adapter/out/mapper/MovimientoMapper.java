package com.sofka.api_transaccional.infraestructura.adapter.out.mapper;

import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.model.ReporteMovimiento;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.CuentaEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.MovimientoEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.dto.ReporteMovimientoDTO;

public class MovimientoMapper {

    public Movimiento toDomainMovimiento(MovimientoEntity movimientoEntity) {
        return Movimiento.builder()
                .movimientoId(movimientoEntity.getMovimientoId())
                .fechaMovimiento(movimientoEntity.getFechaMovimiento())
                .tipoMovimiento(movimientoEntity.getTipoMovimiento())
                .valor(movimientoEntity.getValor())
                .saldoDisponible(movimientoEntity.getSaldoDisponible())
                .cuentaId(movimientoEntity.getCuentaEntity() != null ? movimientoEntity.getCuentaEntity().getCuentaId() : null)
                .build();
    }

    public MovimientoEntity toEntityMovimiento(Movimiento movimiento) {
        CuentaEntity cuentaEntity = CuentaEntity.builder()
                .cuentaId(movimiento.getCuentaId())
                .build();
        return MovimientoEntity.builder()
                .movimientoId(movimiento.getMovimientoId())
                .fechaMovimiento(movimiento.getFechaMovimiento())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor())
                .saldoDisponible(movimiento.getSaldoDisponible())
                .cuentaEntity(cuentaEntity)
                .build();
    }

    public ReporteMovimiento toDomainReporteMovimiento(ReporteMovimientoDTO reporteMovimientoDTO) {
        return ReporteMovimiento.builder()
                .fechaMovimiento(reporteMovimientoDTO.getFechaMovimiento())
                .nombre(reporteMovimientoDTO.getNombre())
                .numeroCuenta(reporteMovimientoDTO.getNumeroCuenta())
                .tipoCuenta(reporteMovimientoDTO.getTipoCuenta())
                .saldoInicial(reporteMovimientoDTO.getSaldoInicial())
                .estado(reporteMovimientoDTO.isEstado())
                .valor(reporteMovimientoDTO.getValor())
                .saldoDisponible(reporteMovimientoDTO.getSaldoDisponible())
                .build();
    }

}
