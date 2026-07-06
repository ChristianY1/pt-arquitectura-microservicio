package com.sofka.api_transaccional.infraestructura.adapter.in.mapper;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.ClienteRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.ClienteResponseDTO;

public class ClienteWebMapper {

    public Cliente toDomainCliente(ClienteRequestDTO clienteRequestDTO) {
        return Cliente.builder()
                .usuario(clienteRequestDTO.usuario())
                .contrasenia(clienteRequestDTO.contrasenia())
                .estado(clienteRequestDTO.estado())
                .nombre(clienteRequestDTO.persona().nombre())
                .genero(clienteRequestDTO.persona().genero())
                .edad(clienteRequestDTO.persona().edad())
                .identificacion(clienteRequestDTO.persona().identificacion())
                .direccion(clienteRequestDTO.persona().direccion())
                .telefono(clienteRequestDTO.persona().telefono())
                .build();

    }

    public ClienteResponseDTO toResponseCliente(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getUsuario(),
                cliente.isEstado(),
                cliente.getNombre(),
                cliente.getGenero(),
                cliente.getEdad(),
                cliente.getIdentificacion(),
                cliente.getDireccion(),
                cliente.getTelefono());
    }
}
