package com.sofka.api_transaccional.infraestructura.adapter.in.mapper;

import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.model.TipoCuenta;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.CuentaRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.CuentaResponseDTO;

public class CuentaWebMapper {

    public Cuenta toDomainCuenta(CuentaRequestDTO cuentaRequestDTO) {
        return Cuenta.builder()
                .numeroCuenta(cuentaRequestDTO.numeroCuenta())
                .tipoCuenta(TipoCuenta.fromCodigo(cuentaRequestDTO.tipoCuenta()))
                .saldoInicial(cuentaRequestDTO.saldoInicial())
                .estado(cuentaRequestDTO.estado())
                .build();
    }

    public CuentaResponseDTO toResponseCuenta(Cuenta cuenta) {
        return new CuentaResponseDTO(
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta().getCodigo(),
                cuenta.getSaldoInicial(),
                cuenta.getEstado());
    }

}
