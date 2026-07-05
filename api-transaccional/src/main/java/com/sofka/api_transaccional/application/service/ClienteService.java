package com.sofka.api_transaccional.application.service;

import java.util.Optional;

import com.sofka.api_transaccional.domain.exception.IdentificacionNoValidaException;
import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.port.in.ClientePortIn;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;

public class ClienteService implements ClientePortIn {

    private static final int LONGITUD_IDENTIFICACION = 10;

    ClienteRepositoryPortOut clienteRepositoryPortOut;

    public ClienteService(ClienteRepositoryPortOut clienteRepositoryPortOut) {
        this.clienteRepositoryPortOut = clienteRepositoryPortOut;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        validarIdentificacion(cliente.getIdentificacion());
        return clienteRepositoryPortOut.crearCliente(cliente);
    }

    @Override
    public Optional<Cliente> buscarCliente(Long clienteId) {
        return clienteRepositoryPortOut.buscarCliente(clienteId);
    }

    @Override
    public Optional<Cliente> buscarClientePorIdentificacion(String identificacion) {
        return clienteRepositoryPortOut.buscarClientePorIdentificacion(identificacion);
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        validarIdentificacion(cliente.getIdentificacion());
        return clienteRepositoryPortOut.actualizarCliente(cliente);
    }

    @Override
    public void eliminarCliente(Long clienteId) {
        clienteRepositoryPortOut.eliminarCliente(clienteId);
    }

    private void validarIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.length() != LONGITUD_IDENTIFICACION) {
            throw new IdentificacionNoValidaException("Cédula no válida");
        }
    }

}
