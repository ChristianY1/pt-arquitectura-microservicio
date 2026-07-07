package com.sofka.api_personas.infraestructura.adapter.in.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sofka.api_personas.domain.model.Cliente;
import com.sofka.api_personas.domain.port.in.ClientePortIn;
import com.sofka.api_personas.infraestructura.adapter.in.dto.request.ClienteRequestDTO;
import com.sofka.api_personas.infraestructura.adapter.in.dto.response.ClienteResponseDTO;
import com.sofka.api_personas.infraestructura.adapter.in.mapper.ClienteWebMapper;

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
    ResponseEntity<Map<String, Object>> crearCliente(@RequestBody ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clientePortIn.crearCliente(clienteWebMapper.toDomainCliente(clienteRequestDTO));
        ClienteResponseDTO clienteResponse = clienteWebMapper.toResponseCliente(cliente);
        return ResponseBuilder.build(HttpStatus.OK, "Cliente creado exitosamente", clienteResponse);
    }

    @GetMapping("/{clienteId}")
    ResponseEntity<Map<String, Object>> buscarCliente(@PathVariable Long clienteId){
        Cliente cliente = clientePortIn.buscarCliente(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        ClienteResponseDTO clienteResponse = clienteWebMapper.toResponseCliente(cliente);
        return ResponseBuilder.build(HttpStatus.OK, "Cliente encontrado", clienteResponse);
    }

    @PutMapping("/actualizar/{clienteId}")
    ResponseEntity<Map<String, Object>> actualizarCliente(@PathVariable Long clienteId, @RequestBody ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = clienteWebMapper.toDomainCliente(clienteRequestDTO);
        cliente.setClienteId(clienteId);
        Cliente actualizado = clientePortIn.actualizarCliente(cliente);
        ClienteResponseDTO clienteResponse = clienteWebMapper.toResponseCliente(actualizado);
        return ResponseBuilder.build(HttpStatus.OK, "Cliente actualizado exitosamente", clienteResponse);
    }

    @DeleteMapping("/eliminar/{clienteId}")
    ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable Long clienteId){
        clientePortIn.eliminarCliente(clienteId);
        return ResponseBuilder.build(HttpStatus.OK, "Cliente eliminado exitosamente", null);
    }

}
