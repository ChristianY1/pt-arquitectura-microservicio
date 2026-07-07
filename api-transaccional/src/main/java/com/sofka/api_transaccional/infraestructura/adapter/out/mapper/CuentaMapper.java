package com.sofka.api_transaccional.infraestructura.adapter.out.mapper;

import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.CuentaEntity;

public class CuentaMapper {

    public Cuenta toDomainCuenta(CuentaEntity cuentaEntity) {
        return Cuenta.builder()
                .cuentaId(cuentaEntity.getCuentaId())
                .numeroCuenta(cuentaEntity.getNumeroCuenta())
                .tipoCuenta(cuentaEntity.getTipoCuenta())
                .saldoInicial(cuentaEntity.getSaldoInicial())
                .estado(cuentaEntity.isEstado())
                .clienteId(cuentaEntity.getClienteId())
                .build();
    }

    public CuentaEntity toEntityCuenta(Cuenta cuenta) {
        return CuentaEntity.builder()
                .cuentaId(cuenta.getCuentaId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.isEstado())
                .clienteId(cuenta.getClienteId())
                .build();
    }

}
