package com.sofka.api_personas.domain.port.in;

import java.util.Optional;

import com.sofka.api_personas.domain.model.Cliente;

public interface ClientePortIn {

    Cliente crearCliente(Cliente cliente);

    Optional<Cliente> buscarCliente(Long clienteId);

    Optional<Cliente> buscarClientePorIdentificacion(String identificacion);

    Cliente actualizarCliente(Cliente cliente);

    void eliminarCliente(Long clienteId);

}
