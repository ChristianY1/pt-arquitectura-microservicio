package com.sofka.api_transaccional.infraestructura.adapter.in.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sofka.api_transaccional.domain.exception.ReglaNegocioException;
import com.sofka.api_transaccional.domain.exception.RecursoNoEncontradoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return ResponseBuilder.build(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Map<String, Object>> handleReglaNegocio(ReglaNegocioException ex) {
        return ResponseBuilder.build(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseBuilder.build(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenerico(Exception ex) {
        return ResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado", null);
    }

}
