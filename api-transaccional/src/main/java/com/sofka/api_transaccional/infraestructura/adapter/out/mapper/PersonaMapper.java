package com.sofka.api_transaccional.infraestructura.adapter.out.mapper;

import com.sofka.api_transaccional.domain.model.Persona;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.PersonaEntity;

public class PersonaMapper {

    public Persona toDomainPersona(PersonaEntity personaEntity) {
        return Persona.builder()
                .personaId(personaEntity.getPersonaId())
                .nombre(personaEntity.getNombre())
                .genero(personaEntity.getGenero())
                .edad(personaEntity.getEdad())
                .identificacion(personaEntity.getIdentificacion())
                .direccion(personaEntity.getDireccion())
                .telefono(personaEntity.getTelefono())
                .build();
    }

    public PersonaEntity toEntityPersona(Persona persona) {
        return PersonaEntity.builder()
                .personaId(persona.getPersonaId())
                .nombre(persona.getNombre())
                .genero(persona.getGenero())
                .edad(persona.getEdad())
                .identificacion(persona.getIdentificacion())
                .direccion(persona.getDireccion())
                .telefono(persona.getTelefono())
                .build();
    }
}
