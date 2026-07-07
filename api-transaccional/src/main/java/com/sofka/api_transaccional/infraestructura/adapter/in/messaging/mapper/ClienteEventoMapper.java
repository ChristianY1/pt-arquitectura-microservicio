package com.sofka.api_transaccional.infraestructura.adapter.in.messaging.mapper;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.infraestructura.adapter.in.messaging.dto.ClienteEventoDTO;

public class ClienteEventoMapper {

    public Cliente toDomainCliente(ClienteEventoDTO clienteEventoDTO) {
        return Cliente.builder()
                .clienteId(clienteEventoDTO.clienteId())
                .identificacion(clienteEventoDTO.identificacion())
                .nombre(clienteEventoDTO.nombre())
                .estado(clienteEventoDTO.estado())
                .build();
    }

}
