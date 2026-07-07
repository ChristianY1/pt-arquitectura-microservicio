package com.sofka.api_transaccional.infraestructura.adapter.out.mapper;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.ClienteEntity;

public class ClienteMapper {

    public Cliente toDomainCliente(ClienteEntity clienteEntity) {
        return Cliente.builder()
                .clienteId(clienteEntity.getClienteId())
                .identificacion(clienteEntity.getIdentificacion())
                .nombre(clienteEntity.getNombre())
                .estado(clienteEntity.isEstado())
                .build();
    }

    public ClienteEntity toEntityCliente(Cliente cliente) {
        return ClienteEntity.builder()
                .clienteId(cliente.getClienteId())
                .identificacion(cliente.getIdentificacion())
                .nombre(cliente.getNombre())
                .estado(cliente.isEstado())
                .build();
    }

}
