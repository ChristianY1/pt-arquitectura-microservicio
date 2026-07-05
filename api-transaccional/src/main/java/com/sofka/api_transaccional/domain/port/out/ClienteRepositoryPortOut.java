package com.sofka.api_transaccional.domain.port.out;

import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cliente;

public interface ClienteRepositoryPortOut {

    Cliente crearCliente(Cliente cliente);

    Optional<Cliente> buscarCliente(Long clienteId);

    Cliente actualizarCliente(Cliente cliente);

    void eliminarCliente(Long clienteId);

}
