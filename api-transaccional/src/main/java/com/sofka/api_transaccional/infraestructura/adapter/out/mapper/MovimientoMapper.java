package com.sofka.api_transaccional.infraestructura.adapter.out.mapper;

import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.CuentaEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.MovimientoEntity;

public class MovimientoMapper {

    public Movimiento toDomainMovimiento(MovimientoEntity movimientoEntity) {
        return Movimiento.builder()
                .movimientoId(movimientoEntity.getMovimientoId())
                .fecha(movimientoEntity.getFecha())
                .tipoMovimiento(movimientoEntity.getTipoMovimiento())
                .valor(movimientoEntity.getValor())
                .saldo(movimientoEntity.getSaldo())
                .cuentaId(movimientoEntity.getCuentaEntity() != null ? movimientoEntity.getCuentaEntity().getCuentaId() : null)
                .build();
    }

    public MovimientoEntity toEntityMovimiento(Movimiento movimiento) {
        CuentaEntity cuentaEntity = CuentaEntity.builder()
                .cuentaId(movimiento.getCuentaId())
                .build();
        return MovimientoEntity.builder()
                .movimientoId(movimiento.getMovimientoId())
                .fecha(movimiento.getFecha())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor())
                .saldo(movimiento.getSaldo())
                .cuentaEntity(cuentaEntity)
                .build();
    }

}
