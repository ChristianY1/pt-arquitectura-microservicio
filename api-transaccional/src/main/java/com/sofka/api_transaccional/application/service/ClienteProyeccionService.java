package com.sofka.api_transaccional.application.service;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.port.in.ClienteProyeccionPortIn;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;

public class ClienteProyeccionService implements ClienteProyeccionPortIn {

    private final ClienteRepositoryPortOut clienteRepositoryPortOut;

    public ClienteProyeccionService(ClienteRepositoryPortOut clienteRepositoryPortOut) {
        this.clienteRepositoryPortOut = clienteRepositoryPortOut;
    }

    /**
     * Actualiza la copia local del cliente con los datos recibidos desde api-personas por Kafka,
     * para poder validar clientes al crear una cuenta sin depender de una llamada síncrona a otro servicio.
     *
     * @param cliente datos del cliente recibidos en el evento
     */
    @Override
    public void actualizarProyeccion(Cliente cliente) {
        clienteRepositoryPortOut.guardarCliente(cliente);
    }

}
