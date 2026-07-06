package com.sofka.api_transaccional.domain.model;

public enum TipoCuenta {

    AHORROS(1),
    CORRIENTE(2);

    private final int codigo;

    TipoCuenta(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoCuenta fromCodigo(int codigo) {
        for (TipoCuenta tipoCuenta : values()) {
            if (tipoCuenta.codigo == codigo) {
                return tipoCuenta;
            }
        }
        throw new IllegalArgumentException("Código de tipo de cuenta inválido: " + codigo);
    }

}
