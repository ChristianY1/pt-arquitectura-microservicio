package com.sofka.api_personas.domain.port.out;

import java.util.Optional;

import com.sofka.api_personas.domain.model.Cliente;

public interface ClienteRepositoryPortOut {

    Cliente crearCliente(Cliente cliente);

    Optional<Cliente> buscarCliente(Long clienteId);

    Optional<Cliente> buscarClientePorIdentificacion(String identificacion);

    boolean existeUsuario(String usuario);

    Cliente actualizarCliente(Cliente cliente);

    void eliminarCliente(Long clienteId);

}
