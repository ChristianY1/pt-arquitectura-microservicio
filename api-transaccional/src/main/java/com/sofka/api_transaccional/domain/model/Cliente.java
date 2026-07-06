package com.sofka.api_transaccional.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {

    private Long clienteId;
    private String usuario;
    private String contrasenia;
    private boolean estado;


}
