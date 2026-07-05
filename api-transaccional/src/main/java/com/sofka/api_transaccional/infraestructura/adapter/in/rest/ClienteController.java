package com.sofka.api_transaccional.infraestructura.adapter.in.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.api_transaccional.domain.exception.ClienteNoEncontradoException;
import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.port.in.ClientePortIn;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.ClienteRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.ApiResponse;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.ClienteResponseDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.ClienteWebMapper;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    ClientePortIn clientePortIn;

    ClienteWebMapper clienteWebMapper;

    public ClienteController(ClientePortIn clientePortIn, ClienteWebMapper clienteWebMapper){
        this.clientePortIn = clientePortIn;
        this.clienteWebMapper = clienteWebMapper;
    }

    @PostMapping("/crearCliente")
    ApiResponse<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clientePortIn.crearCliente(clienteWebMapper.toDomainCliente(clienteRequestDTO));
        ClienteResponseDTO clienteResponse = clienteWebMapper.toResponseCliente(cliente);
        return new ApiResponse<>("Cliente creado exitosamente", clienteResponse);
    }

    @GetMapping("/{clienteId}")
    ApiResponse<ClienteResponseDTO> buscarCliente(@PathVariable Long clienteId){
        Cliente cliente = clientePortIn.buscarCliente(clienteId)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado"));
        ClienteResponseDTO clienteResponse = clienteWebMapper.toResponseCliente(cliente);
        return new ApiResponse<>("Cliente encontrado", clienteResponse);
    }

    @PutMapping("/{clienteId}")
    ApiResponse<ClienteResponseDTO> actualizarCliente(@PathVariable Long clienteId, @RequestBody ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clienteWebMapper.toDomainCliente(clienteRequestDTO);
        cliente.setClienteId(clienteId);
        Cliente actualizado = clientePortIn.actualizarCliente(cliente);
        ClienteResponseDTO clienteResponse = clienteWebMapper.toResponseCliente(actualizado);
        return new ApiResponse<>("Cliente actualizado exitosamente", clienteResponse);
    }

    @DeleteMapping("/{clienteId}")
    ApiResponse<Void> eliminarCliente(@PathVariable Long clienteId){
        clientePortIn.eliminarCliente(clienteId);
        return new ApiResponse<>("Cliente eliminado exitosamente", null);
    }

}
