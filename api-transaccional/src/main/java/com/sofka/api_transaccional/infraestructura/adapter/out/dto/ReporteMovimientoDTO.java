package com.sofka.api_transaccional.infraestructura.adapter.out.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMovimientoDTO {

    private LocalDateTime fechaMovimiento;
    private String nombre;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;

}
