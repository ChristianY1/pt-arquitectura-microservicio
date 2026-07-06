package com.sofka.api_transaccional.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;

/**
 * Pruebas unitarias de {@link ClienteService}, enfocadas en las excepciones de dominio
 * que se lanzan al crear un cliente (sin tocar la base de datos: el puerto de salida se simula con Mockito).
 */
class ClienteServiceTest {

    private final ClienteRepositoryPortOut clienteRepositoryPortOut = mock(ClienteRepositoryPortOut.class);

    private final ClienteService clienteService = new ClienteService(clienteRepositoryPortOut);

    /**
     * Si ya existe un cliente registrado con la misma cédula, debe
     * lanzar Exception con estado 400 antes de intentar persistir nada.
     */
    @Test
    void crearCliente_conIdentificacionYaRegistrada_lanzaResponseStatusException() {
        Cliente cliente = Cliente.builder()
                .usuario("jperez")
                .contrasenia("clave123")
                .estado(true)
                .identificacion("1234567890")
                .build();

        when(clienteRepositoryPortOut.buscarClientePorIdentificacion("1234567890")).thenReturn(Optional.of(cliente));

        ResponseStatusException excepcion = assertThrows(ResponseStatusException.class, () -> clienteService.crearCliente(cliente));

        assertEquals(HttpStatus.BAD_REQUEST, excepcion.getStatusCode());
        assertEquals("Ya existe un cliente con esta cédula", excepcion.getReason());
    }

}
