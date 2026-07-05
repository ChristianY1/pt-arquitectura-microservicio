package com.sofka.api_transaccional.infraestructura.adapter.out.mapper;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.model.Persona;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.ClienteEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.PersonaEntity;

public class ClienteMapper {

    private PersonaMapper personaMapper;

    public ClienteMapper(PersonaMapper personaMapper) {
        this.personaMapper = personaMapper;
    }

    public Cliente toDomainCliente(ClienteEntity clienteEntity) {
        Persona persona = personaMapper.toDomainPersona(clienteEntity.getPersonaEntity());
        return Cliente.builder()
                .clienteId(clienteEntity.getClienteId())
                .usuario(clienteEntity.getUsuario())
                .contrasenia(clienteEntity.getContrasenia())
                .estado(clienteEntity.getEstado())
                .personaId(persona.getPersonaId())
                .nombre(persona.getNombre())
                .genero(persona.getGenero())
                .edad(persona.getEdad())
                .identificacion(persona.getIdentificacion())
                .direccion(persona.getDireccion())
                .telefono(persona.getTelefono())
                .build();
    }

    public ClienteEntity toEntityCliente(Cliente cliente) {
        PersonaEntity personaEntity = PersonaEntity.builder()
                .personaId(cliente.getPersonaId())
                .nombre(cliente.getNombre())
                .genero(cliente.getGenero())
                .edad(cliente.getEdad())
                .identificacion(cliente.getIdentificacion())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .build();
        return ClienteEntity.builder()
                .clienteId(cliente.getClienteId())
                .usuario(cliente.getUsuario())
                .contrasenia(cliente.getContrasenia())
                .estado(cliente.getEstado())
                .personaEntity(personaEntity)
                .build();

    }

}
