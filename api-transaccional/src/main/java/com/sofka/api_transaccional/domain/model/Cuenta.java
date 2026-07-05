package com.sofka.api_transaccional.domain.model;

import java.math.BigDecimal;

public class Cuenta {
    
    private String cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private String estado;
    
    public String getCuentaId() {
        return cuentaId;
    }
    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
    }
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    public String getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }
    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    

}
