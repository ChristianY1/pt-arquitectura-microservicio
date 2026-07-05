package com.sofka.api_transaccional.infraestructura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sofka.api_transaccional.application.service.ClienteService;
import com.sofka.api_transaccional.domain.port.in.ClientePortIn;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.ClienteWebMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.ClienteMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.PersonaMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.persistence.ClienteRepositoryAdapter;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.ClienteJpaRepository;


@Configuration
public class BeanConfig {

    @Bean
    ClientePortIn cliente(ClienteRepositoryPortOut clienteRepositoryPortOut){
        return new ClienteService(clienteRepositoryPortOut);
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
