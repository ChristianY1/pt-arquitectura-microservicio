package com.sofka.api_transaccional.infraestructura.adapter.out.persistence;

import java.util.Optional;

import com.sofka.api_transaccional.domain.exception.ClienteNoEncontradoException;
import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.ClienteEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.ClienteMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.ClienteJpaRepository;

public class ClienteRepositoryAdapter implements ClienteRepositoryPortOut {

    ClienteJpaRepository clienteJpaRepository;

    ClienteMapper clienteMapper;

    public ClienteRepositoryAdapter(ClienteJpaRepository clienteJpaRepository, ClienteMapper clienteMapper) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteMapper.toEntityCliente(cliente);
        return clienteMapper.toDomainCliente(clienteJpaRepository.save(clienteEntity));
    }

    @Override
    public Optional<Cliente> buscarCliente(Long clienteId) {
        Optional<ClienteEntity> clienteEntityOptional =  clienteJpaRepository.findById(clienteId);
        return clienteEntityOptional.map(cliOp -> clienteMapper.toDomainCliente(cliOp));
    }

    @Override
    public Optional<Cliente> buscarClientePorIdentificacion(String identificacion) {
        Optional<ClienteEntity> clienteEntityOptional = clienteJpaRepository.findByPersonaEntity_Identificacion(identificacion);
        return clienteEntityOptional.map(clienteEntity -> clienteMapper.toDomainCliente(clienteEntity));
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        Cliente clienteExistente = buscarCliente(cliente.getClienteId())
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado"));
        cliente.setPersonaId(clienteExistente.getPersonaId());
        ClienteEntity clienteEntity = clienteMapper.toEntityCliente(cliente);
        return clienteMapper.toDomainCliente(clienteJpaRepository.save(clienteEntity));
    }

    @Override
    public void eliminarCliente(Long clienteId) {
        if (buscarCliente(clienteId).isEmpty()) {
            throw new ClienteNoEncontradoException("Cliente no encontrado");
        }
        
        clienteJpaRepository.deleteById(clienteId);
    }

}
