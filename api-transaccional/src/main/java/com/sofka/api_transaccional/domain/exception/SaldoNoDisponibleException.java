package com.sofka.api_transaccional.domain.exception;

public class SaldoNoDisponibleException extends RuntimeException {

    public SaldoNoDisponibleException(String mensaje) {
        super(mensaje);
    }

}
