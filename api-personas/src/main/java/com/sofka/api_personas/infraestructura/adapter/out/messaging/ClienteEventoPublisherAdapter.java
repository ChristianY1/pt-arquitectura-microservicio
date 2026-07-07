package com.sofka.api_personas.infraestructura.adapter.out.messaging;

import org.springframework.kafka.core.KafkaTemplate;

import com.sofka.api_personas.domain.model.Cliente;
import com.sofka.api_personas.domain.port.out.ClienteEventoPublisherPortOut;
import com.sofka.api_personas.infraestructura.adapter.out.messaging.dto.ClienteEventoDTO;

public class ClienteEventoPublisherAdapter implements ClienteEventoPublisherPortOut {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final String topicClientes;

    public ClienteEventoPublisherAdapter(KafkaTemplate<String, Object> kafkaTemplate, String topicClientes) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicClientes = topicClientes;
    }

    @Override
    public void publicarCliente(Cliente cliente) {
        ClienteEventoDTO evento = new ClienteEventoDTO(cliente.getClienteId(), cliente.isEstado());
        kafkaTemplate.send(topicClientes, String.valueOf(cliente.getClienteId()), evento);
    }

}
