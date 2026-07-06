package com.sofka.api_transaccional.infraestructura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.sofka.api_transaccional.application.service.ClienteService;
import com.sofka.api_transaccional.application.service.CuentaService;
import com.sofka.api_transaccional.application.service.MovimientoService;
import com.sofka.api_transaccional.domain.port.in.ClientePortIn;
import com.sofka.api_transaccional.domain.port.in.CuentaPortIn;
import com.sofka.api_transaccional.domain.port.in.MovimientoPortIn;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;
import com.sofka.api_transaccional.domain.port.out.CuentaRepositoryPortOut;
import com.sofka.api_transaccional.domain.port.out.MovimientoRepositoryPortOut;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.ClienteWebMapper;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.CuentaWebMapper;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.MovimientoWebMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.ClienteMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.CuentaMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.MovimientoMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.PersonaMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.persistence.ClienteRepositoryAdapter;
import com.sofka.api_transaccional.infraestructura.adapter.out.persistence.CuentaRepositoryAdapter;
import com.sofka.api_transaccional.infraestructura.adapter.out.persistence.MovimientoRepositoryAdapter;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.ClienteJpaRepository;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.CuentaJpaRepository;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.MovimientoJpaRepository;


@Configuration
@EnableJpaAuditing
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
    CuentaRepositoryPortOut cuentaRepositoryPortOut(CuentaJpaRepository cuentaJpaRepository, ClienteJpaRepository clienteJpaRepository, CuentaMapper cuentaMapper){
        return new CuentaRepositoryAdapter(cuentaJpaRepository, clienteJpaRepository, cuentaMapper);
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
