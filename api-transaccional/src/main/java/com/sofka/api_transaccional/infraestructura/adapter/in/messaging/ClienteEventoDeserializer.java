package com.sofka.api_transaccional.infraestructura.adapter.in.messaging;

import org.apache.kafka.common.serialization.Deserializer;

import com.sofka.api_transaccional.infraestructura.adapter.in.messaging.dto.ClienteEventoDTO;

import tools.jackson.databind.ObjectMapper;

public class ClienteEventoDeserializer implements Deserializer<ClienteEventoDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ClienteEventoDTO deserialize(String topic, byte[] data) {
        return data == null ? null : objectMapper.readValue(data, ClienteEventoDTO.class);
    }

}
