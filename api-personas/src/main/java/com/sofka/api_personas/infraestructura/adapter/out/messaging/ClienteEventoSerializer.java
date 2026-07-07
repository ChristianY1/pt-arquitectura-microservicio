package com.sofka.api_personas.infraestructura.adapter.out.messaging;

import org.apache.kafka.common.serialization.Serializer;

import tools.jackson.databind.ObjectMapper;

public class ClienteEventoSerializer implements Serializer<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Object data) {
        return data == null ? null : objectMapper.writeValueAsBytes(data);
    }

}
