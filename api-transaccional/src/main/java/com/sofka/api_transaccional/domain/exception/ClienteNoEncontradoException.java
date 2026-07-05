package com.sofka.api_transaccional.domain.exception;

public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(String mensaje){
        super(mensaje);
    }
    
}
