package com.sofka.api_transaccional.domain.exception;

public class MovimientoNoEncontradoException extends RuntimeException {

    public MovimientoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

}
