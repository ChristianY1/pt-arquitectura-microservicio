package com.sofka.api_transaccional.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
        if (clienteRepositoryPortOut.buscarClientePorIdentificacion(cliente.getIdentificacion()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un cliente con esta cédula");
        }
        if (clienteRepositoryPortOut.existeUsuario(cliente.getUsuario())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya está en uso");
        }
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cédula no válida");
        }
    }

}
