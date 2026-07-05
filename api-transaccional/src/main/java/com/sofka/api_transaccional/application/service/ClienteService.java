package com.sofka.api_transaccional.application.service;

import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.port.in.ClientePortIn;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;

public class ClienteService implements ClientePortIn {

    ClienteRepositoryPortOut clienteRepositoryPortOut;

    public ClienteService(ClienteRepositoryPortOut clienteRepositoryPortOut) {
        this.clienteRepositoryPortOut = clienteRepositoryPortOut;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepositoryPortOut.crearCliente(cliente);
    }

    @Override
    public Optional<Cliente> buscarCliente(Long clienteId) {
        return clienteRepositoryPortOut.buscarCliente(clienteId);
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepositoryPortOut.actualizarCliente(cliente);
    }

    @Override
    public void eliminarCliente(Long clienteId) {
        clienteRepositoryPortOut.eliminarCliente(clienteId);
    }

}
