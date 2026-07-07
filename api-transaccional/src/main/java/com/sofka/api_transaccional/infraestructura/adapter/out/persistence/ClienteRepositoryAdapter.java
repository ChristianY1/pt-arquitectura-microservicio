package com.sofka.api_transaccional.infraestructura.adapter.out.persistence;

import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.ClienteEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.ClienteMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.ClienteJpaRepository;

public class ClienteRepositoryAdapter implements ClienteRepositoryPortOut {

    private final ClienteJpaRepository clienteJpaRepository;

    private final ClienteMapper clienteMapper;

    public ClienteRepositoryAdapter(ClienteJpaRepository clienteJpaRepository, ClienteMapper clienteMapper) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Optional<Cliente> buscarCliente(Long clienteId) {
        return clienteJpaRepository.findById(clienteId)
                .map(clienteMapper::toDomainCliente);
    }

    /**
     * Guarda o actualiza la copia local del cliente (upsert por id, tal como llega en el evento de Kafka).
     *
     * @param cliente datos del cliente a guardar
     */
    @Override
    public void guardarCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteMapper.toEntityCliente(cliente);
        clienteJpaRepository.save(clienteEntity);
    }

}
