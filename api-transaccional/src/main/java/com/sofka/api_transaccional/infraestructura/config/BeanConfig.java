package com.sofka.api_transaccional.infraestructura.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.sofka.api_transaccional.application.service.ClienteProyeccionService;
import com.sofka.api_transaccional.application.service.CuentaService;
import com.sofka.api_transaccional.application.service.MovimientoService;
import com.sofka.api_transaccional.domain.port.in.ClienteProyeccionPortIn;
import com.sofka.api_transaccional.domain.port.in.CuentaPortIn;
import com.sofka.api_transaccional.domain.port.in.MovimientoPortIn;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;
import com.sofka.api_transaccional.domain.port.out.CuentaRepositoryPortOut;
import com.sofka.api_transaccional.domain.port.out.MovimientoRepositoryPortOut;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.CuentaWebMapper;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.MovimientoWebMapper;
import com.sofka.api_transaccional.infraestructura.adapter.in.messaging.ClienteEventoListener;
import com.sofka.api_transaccional.infraestructura.adapter.in.messaging.dto.ClienteEventoDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.messaging.mapper.ClienteEventoMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.ClienteMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.CuentaMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.MovimientoMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.persistence.ClienteRepositoryAdapter;
import com.sofka.api_transaccional.infraestructura.adapter.out.persistence.CuentaRepositoryAdapter;
import com.sofka.api_transaccional.infraestructura.adapter.out.persistence.MovimientoRepositoryAdapter;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.ClienteJpaRepository;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.CuentaJpaRepository;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.MovimientoJpaRepository;


@Configuration
@EnableJpaAuditing
@EnableKafka
public class BeanConfig {

    @Bean
    CuentaPortIn cuenta(CuentaRepositoryPortOut cuentaRepositoryPortOut, ClienteRepositoryPortOut clienteRepositoryPortOut){
        return new CuentaService(cuentaRepositoryPortOut, clienteRepositoryPortOut);
    }

    @Bean
    CuentaMapper cuentaMapper(){
        return new CuentaMapper();
    }

    @Bean
    CuentaWebMapper cuentaWebMapper(){
        return new CuentaWebMapper();
    }

    @Bean
    CuentaRepositoryPortOut cuentaRepositoryPortOut(CuentaJpaRepository cuentaJpaRepository, CuentaMapper cuentaMapper){
        return new CuentaRepositoryAdapter(cuentaJpaRepository, cuentaMapper);
    }

    @Bean
    ClienteMapper clienteMapper(){
        return new ClienteMapper();
    }

    @Bean
    ClienteRepositoryPortOut clienteRepositoryPortOut(ClienteJpaRepository clienteJpaRepository, ClienteMapper clienteMapper){
        return new ClienteRepositoryAdapter(clienteJpaRepository, clienteMapper);
    }

    @Bean
    ClienteProyeccionPortIn clienteProyeccion(ClienteRepositoryPortOut clienteRepositoryPortOut){
        return new ClienteProyeccionService(clienteRepositoryPortOut);
    }

    @Bean
    ClienteEventoMapper clienteEventoMapper(){
        return new ClienteEventoMapper();
    }

    @Bean
    ClienteEventoListener clienteEventoListener(ClienteProyeccionPortIn clienteProyeccionPortIn, ClienteEventoMapper clienteEventoMapper){
        return new ClienteEventoListener(clienteProyeccionPortIn, clienteEventoMapper);
    }

    @Bean
    ConsumerFactory<String, Object> consumerFactory(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
                                                     @Value("${spring.kafka.consumer.group-id}") String groupId){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, ClienteEventoDTO.class.getPackageName());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, ClienteEventoDTO.class.getName());
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    MovimientoPortIn movimiento(MovimientoRepositoryPortOut movimientoRepositoryPortOut, CuentaRepositoryPortOut cuentaRepositoryPortOut){
        return new MovimientoService(movimientoRepositoryPortOut, cuentaRepositoryPortOut);
    }

    @Bean
    MovimientoMapper movimientoMapper(){
        return new MovimientoMapper();
    }

    @Bean
    MovimientoWebMapper movimientoWebMapper(){
        return new MovimientoWebMapper();
    }

    @Bean
    MovimientoRepositoryPortOut movimientoRepositoryPortOut(MovimientoJpaRepository movimientoJpaRepository, CuentaJpaRepository cuentaJpaRepository, MovimientoMapper movimientoMapper){
        return new MovimientoRepositoryAdapter(movimientoJpaRepository, cuentaJpaRepository, movimientoMapper);
    }

}
