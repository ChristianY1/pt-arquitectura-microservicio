package com.sofka.api_personas.infraestructura.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.sofka.api_personas.application.service.ClienteService;
import com.sofka.api_personas.domain.port.in.ClientePortIn;
import com.sofka.api_personas.domain.port.out.ClienteEventoPublisherPortOut;
import com.sofka.api_personas.domain.port.out.ClienteRepositoryPortOut;
import com.sofka.api_personas.infraestructura.adapter.in.mapper.ClienteWebMapper;
import com.sofka.api_personas.infraestructura.adapter.out.mapper.ClienteMapper;
import com.sofka.api_personas.infraestructura.adapter.out.mapper.PersonaMapper;
import com.sofka.api_personas.infraestructura.adapter.out.messaging.ClienteEventoPublisherAdapter;
import com.sofka.api_personas.infraestructura.adapter.out.persistence.ClienteRepositoryAdapter;
import com.sofka.api_personas.infraestructura.adapter.out.repository.ClienteJpaRepository;

@Configuration
@EnableJpaAuditing
@EnableKafka
public class BeanConfig {

    @Bean
    ClientePortIn cliente(ClienteRepositoryPortOut clienteRepositoryPortOut, ClienteEventoPublisherPortOut clienteEventoPublisherPortOut){
        return new ClienteService(clienteRepositoryPortOut, clienteEventoPublisherPortOut);
    }

    @Bean
    ProducerFactory<String, Object> producerFactory(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    ClienteEventoPublisherPortOut clienteEventoPublisherPortOut(KafkaTemplate<String, Object> kafkaTemplate, @Value("${app.kafka.topic.clientes}") String topicClientes){
        return new ClienteEventoPublisherAdapter(kafkaTemplate, topicClientes);
    }

    @Bean
    PersonaMapper personaMapper(){
        return new PersonaMapper();
    }

    @Bean
    ClienteMapper clienteMapper(PersonaMapper personaMapper){
        return new ClienteMapper(personaMapper);
    }

    @Bean
    ClienteWebMapper clienteWebMapper(){
        return new ClienteWebMapper();
    }

    @Bean
    ClienteRepositoryPortOut clienteRepositoryPortOut(ClienteJpaRepository clienteJpaRepository, ClienteMapper clienteMapper){
        return new ClienteRepositoryAdapter(clienteJpaRepository, clienteMapper);
    }

}
