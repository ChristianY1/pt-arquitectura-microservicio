package com.sofka.api_personas.domain.port.out;

import com.sofka.api_personas.domain.model.Cliente;

public interface ClienteEventoPublisherPortOut {

    void publicarCliente(Cliente cliente);

}
