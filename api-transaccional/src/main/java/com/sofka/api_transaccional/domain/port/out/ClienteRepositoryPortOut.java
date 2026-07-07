package com.sofka.api_transaccional.domain.port.out;

import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cliente;

public interface ClienteRepositoryPortOut {

    Optional<Cliente> buscarCliente(Long clienteId);

    Optional<Cliente> buscarClientePorIdentificacion(String identificacion);

    void guardarCliente(Cliente cliente);

}
