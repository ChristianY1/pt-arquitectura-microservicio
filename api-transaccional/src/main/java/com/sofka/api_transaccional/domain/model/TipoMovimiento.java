package com.sofka.api_transaccional.domain.model;

public enum TipoMovimiento {

    DEPOSITO(1, "Deposito"),
    RETIRO(2, "Retiro");

    private final int codigo;
    private final String descripcion;

    TipoMovimiento(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoMovimiento fromCodigo(int codigo) {
        for (TipoMovimiento tipoMovimiento : values()) {
            if (tipoMovimiento.codigo == codigo) {
                return tipoMovimiento;
            }
        }
        throw new IllegalArgumentException("Código de tipo de movimiento inválido: " + codigo);
    }

}
