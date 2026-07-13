package com.sofka.api_personas.infraestructura.adapter.out.persistence;

import java.util.Optional;

import com.sofka.api_personas.domain.exception.RecursoNoEncontradoException;
import com.sofka.api_personas.domain.model.Cliente;
import com.sofka.api_personas.domain.port.out.ClienteRepositoryPortOut;
import com.sofka.api_personas.infraestructura.adapter.out.entity.ClienteEntity;
import com.sofka.api_personas.infraestructura.adapter.out.mapper.ClienteMapper;
import com.sofka.api_personas.infraestructura.adapter.out.repository.ClienteJpaRepository;

public class ClienteRepositoryAdapter implements ClienteRepositoryPortOut {

    ClienteJpaRepository clienteJpaRepository;

    ClienteMapper clienteMapper;

    public ClienteRepositoryAdapter(ClienteJpaRepository clienteJpaRepository, ClienteMapper clienteMapper) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.clienteMapper = clienteMapper;
    }

    /**
     * Persiste un nuevo cliente (junto con su persona asociada, por cascada) en la base de datos.
     *
     * @param cliente datos del cliente a guardar
     * @return el cliente guardado, con los identificadores generados por la base de datos
     */
    @Override
    public Cliente crearCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteMapper.toEntityCliente(cliente);
        return clienteMapper.toDomainCliente(clienteJpaRepository.save(clienteEntity));
    }

    /**
     * Consulta en la base de datos un cliente por su identificador interno.
     *
     * @param clienteId id del cliente
     * @return el cliente encontrado, o null si no existe
     */
    @Override
    public Optional<Cliente> buscarCliente(Long clienteId) {
        Optional<ClienteEntity> clienteEntityOptional =  clienteJpaRepository.findById(clienteId);
        return clienteEntityOptional.map(cliOp -> clienteMapper.toDomainCliente(cliOp));
    }

    /**
     * Consulta en la base de datos un cliente por la cédula de la persona asociada.
     *
     * @param identificacion cédula de la persona
     * @return el cliente encontrado, o null si no existe
     */
    @Override
    public Optional<Cliente> buscarClientePorIdentificacion(String identificacion) {
        Optional<ClienteEntity> clienteEntityOptional = clienteJpaRepository.findByPersonaEntity_Identificacion(identificacion);
        return clienteEntityOptional.map(clienteEntity -> clienteMapper.toDomainCliente(clienteEntity));
    }

    /**
     * Verifica en la base de datos si ya existe un cliente con el usuario indicado.
     *
     * @param usuario nombre de usuario a verificar
     * @return {@code true} si ya existe un cliente con ese usuario
     */
    @Override
    public boolean existeUsuario(String usuario) {
        return clienteJpaRepository.existsByUsuario(usuario);
    }

    /**
     * Actualiza en la base de datos los datos de un cliente existente, conservando
     * el id de la persona asociada (no se puede reasignar en una actualización).
     *
     * @param cliente datos actualizados del cliente
     * @return el cliente actualizado
     * @throws RecursoNoEncontradoException si el cliente no existe
     */
    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        Cliente clienteExistente = buscarCliente(cliente.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
        cliente.setPersonaId(clienteExistente.getPersonaId());
        ClienteEntity clienteEntity = clienteMapper.toEntityCliente(cliente);
        return clienteMapper.toDomainCliente(clienteJpaRepository.save(clienteEntity));
    }

    /**
     * Elimina de la base de datos un cliente por su identificador.
     *
     * @param clienteId id del cliente a eliminar
     * @throws RecursoNoEncontradoException si el cliente no existe
     */
    @Override
    public void eliminarCliente(Long clienteId) {
        if (buscarCliente(clienteId).isEmpty()) {
            throw new RecursoNoEncontradoException("Cliente no encontrado");
        }

        clienteJpaRepository.deleteById(clienteId);
    }

}
