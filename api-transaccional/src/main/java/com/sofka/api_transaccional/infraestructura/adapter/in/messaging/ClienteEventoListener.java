package com.sofka.api_transaccional.infraestructura.adapter.in.messaging;

import org.springframework.kafka.annotation.KafkaListener;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.port.in.ClienteProyeccionPortIn;
import com.sofka.api_transaccional.infraestructura.adapter.in.messaging.dto.ClienteEventoDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.messaging.mapper.ClienteEventoMapper;

public class ClienteEventoListener {

    private final ClienteProyeccionPortIn clienteProyeccionPortIn;

    private final ClienteEventoMapper clienteEventoMapper;

    public ClienteEventoListener(ClienteProyeccionPortIn clienteProyeccionPortIn, ClienteEventoMapper clienteEventoMapper) {
        this.clienteProyeccionPortIn = clienteProyeccionPortIn;
        this.clienteEventoMapper = clienteEventoMapper;
    }

    @KafkaListener(topics = "${app.kafka.topic.clientes}", groupId = "${spring.application.name}")
    public void escuchar(ClienteEventoDTO clienteEventoDTO) {
        Cliente cliente = clienteEventoMapper.toDomainCliente(clienteEventoDTO);
        clienteProyeccionPortIn.actualizarProyeccion(cliente);
    }

}
