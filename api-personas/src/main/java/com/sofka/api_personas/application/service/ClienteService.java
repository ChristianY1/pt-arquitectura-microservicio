package com.sofka.api_personas.application.service;

import java.util.Optional;

import com.sofka.api_personas.domain.exception.ReglaNegocioException;
import com.sofka.api_personas.domain.model.Cliente;
import com.sofka.api_personas.domain.port.in.ClientePortIn;
import com.sofka.api_personas.domain.port.out.ClienteEventoPublisherPortOut;
import com.sofka.api_personas.domain.port.out.ClienteRepositoryPortOut;

public class ClienteService implements ClientePortIn {

    private static final int LONGITUD_IDENTIFICACION = 10;

    ClienteRepositoryPortOut clienteRepositoryPortOut;

    ClienteEventoPublisherPortOut clienteEventoPublisherPortOut;

    public ClienteService(ClienteRepositoryPortOut clienteRepositoryPortOut, ClienteEventoPublisherPortOut clienteEventoPublisherPortOut) {
        this.clienteRepositoryPortOut = clienteRepositoryPortOut;
        this.clienteEventoPublisherPortOut = clienteEventoPublisherPortOut;
    }

    /**
     * Crea un nuevo cliente, validando previamente que la cédula tenga el formato correcto
     * y que no exista ya un cliente registrado con la misma cédula o el mismo usuario.
     *
     * @param cliente datos del cliente a crear
     * @return el cliente creado, con los identificadores generados
     * @throws ReglaNegocioException si la cédula no es válida, si ya existe un cliente
     *                                con esa cédula o si el usuario ya está en uso
     */
    @Override
    public Cliente crearCliente(Cliente cliente) {
        validarIdentificacion(cliente.getIdentificacion());
        if (clienteRepositoryPortOut.buscarClientePorIdentificacion(cliente.getIdentificacion()).isPresent()) {
            throw new ReglaNegocioException("Ya existe un cliente con esta cédula");
        }
        if (clienteRepositoryPortOut.existeUsuario(cliente.getUsuario())) {
            throw new ReglaNegocioException("El usuario ya está en uso");
        }
        Cliente clienteCreado = clienteRepositoryPortOut.crearCliente(cliente);
        clienteEventoPublisherPortOut.publicarCliente(clienteCreado);
        return clienteCreado;
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
     * @throws ReglaNegocioException si la cédula no es válida
     */
    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        validarIdentificacion(cliente.getIdentificacion());
        Cliente clienteActualizado = clienteRepositoryPortOut.actualizarCliente(cliente);
        clienteEventoPublisherPortOut.publicarCliente(clienteActualizado);
        return clienteActualizado;
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
     * @throws ReglaNegocioException si la cédula es nula o no tiene la longitud esperada
     */
    private void validarIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.length() != LONGITUD_IDENTIFICACION) {
            throw new ReglaNegocioException("Cédula no válida");
        }
    }

}
