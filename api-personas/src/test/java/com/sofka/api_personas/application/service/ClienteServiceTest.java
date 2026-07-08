package com.sofka.api_personas.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.sofka.api_personas.domain.exception.ReglaNegocioException;
import com.sofka.api_personas.domain.model.Cliente;
import com.sofka.api_personas.domain.port.out.ClienteEventoPublisherPortOut;
import com.sofka.api_personas.domain.port.out.ClienteRepositoryPortOut;

/**
 * Pruebas unitarias de {@link ClienteService}, enfocadas en las excepciones de dominio
 * que se lanzan al crear un cliente (sin tocar la base de datos: el puerto de salida se simula con Mockito).
 */
class ClienteServiceTest {

    private final ClienteRepositoryPortOut clienteRepositoryPortOut = mock(ClienteRepositoryPortOut.class);

    private final ClienteEventoPublisherPortOut clienteEventoPublisherPortOut = mock(ClienteEventoPublisherPortOut.class);

    private final ClienteService clienteService = new ClienteService(clienteRepositoryPortOut, clienteEventoPublisherPortOut);

    /**
     * Si ya existe un cliente registrado con la misma cédula, debe
     * lanzar Exception con estado 400 antes de intentar persistir nada.
     */
    @Test
    void crearCliente_conIdentificacionYaRegistrada_lanzaReglaNegocioException() {
        Cliente cliente = Cliente.builder()
                .usuario("jperez")
                .contrasenia("clave123")
                .estado(true)
                .identificacion("1234567890")
                .build();

        when(clienteRepositoryPortOut.buscarClientePorIdentificacion("1234567890")).thenReturn(Optional.of(cliente));

        ReglaNegocioException excepcion = assertThrows(ReglaNegocioException.class, () -> clienteService.crearCliente(cliente));

        assertEquals("Ya existe un cliente con esta cédula", excepcion.getMessage());
    }

}
