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

    /**
     * Crea un nuevo cliente, validando previamente que la cédula tenga el formato correcto
     * y que no exista ya un cliente registrado con la misma cédula o el mismo usuario.
     *
     * @param cliente datos del cliente a crear
     * @return el cliente creado, con los identificadores generados
     * @throws ResponseStatusException 400 si la cédula no es válida, si ya existe un cliente
     *                                  con esa cédula o si el usuario ya está en uso
     */
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

    /**
     * Busca un cliente por su identificador interno.
     *
     * @param clienteId id del cliente
     * @return el cliente encontrado, o null si no existe
     */
    @Override
    public Optional<Cliente> buscarCliente(Long clienteId) {
        return clienteRepositoryPortOut.buscarCliente(clienteId);
    }

    /**
     * Busca un cliente por el número de cédula de la persona asociada.
     *
     * @param identificacion cédula de la persona
     * @return el cliente encontrado, o null si no existe
     */
    @Override
    public Optional<Cliente> buscarClientePorIdentificacion(String identificacion) {
        return clienteRepositoryPortOut.buscarClientePorIdentificacion(identificacion);
    }

    /**
     * Actualiza los datos de un cliente existente, validando previamente el formato de la cédula.
     *
     * @param cliente datos actualizados del cliente
     * @return el cliente actualizado
     * @throws ResponseStatusException 400 si la cédula no es válida
     */
    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        validarIdentificacion(cliente.getIdentificacion());
        return clienteRepositoryPortOut.actualizarCliente(cliente);
    }

    /**
     * Elimina un cliente por su identificador.
     *
     * @param clienteId id del cliente a eliminar
     */
    @Override
    public void eliminarCliente(Long clienteId) {
        clienteRepositoryPortOut.eliminarCliente(clienteId);
    }

    /**
     * Valida que la cédula no sea nula y tenga exactamente 10 caracteres.
     *
     * @param identificacion cédula a validar
     * @throws ResponseStatusException 400 si la cédula es nula o no tiene la longitud esperada
     */
    private void validarIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.length() != LONGITUD_IDENTIFICACION) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cédula no válida");
        }
    }

}
