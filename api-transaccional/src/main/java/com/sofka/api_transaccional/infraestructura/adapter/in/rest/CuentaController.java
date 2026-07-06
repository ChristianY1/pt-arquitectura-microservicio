package com.sofka.api_transaccional.infraestructura.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.port.in.ClientePortIn;
import com.sofka.api_transaccional.domain.port.in.CuentaPortIn;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.CuentaRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.ApiResponse;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.CuentaResponseDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.CuentaWebMapper;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaPortIn cuentaPortIn;

    private final ClientePortIn clientePortIn;

    private final CuentaWebMapper cuentaWebMapper;

    public CuentaController(CuentaPortIn cuentaPortIn, ClientePortIn clientePortIn, CuentaWebMapper cuentaWebMapper) {
        this.cuentaPortIn = cuentaPortIn;
        this.clientePortIn = clientePortIn;
        this.cuentaWebMapper = cuentaWebMapper;
    }

    @PostMapping
    ApiResponse<CuentaResponseDTO> crearCuenta(@RequestBody CuentaRequestDTO cuentaRequestDTO) {
        Cliente cliente = clientePortIn.buscarClientePorIdentificacion(cuentaRequestDTO.identificacion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        Cuenta cuenta = cuentaWebMapper.toDomainCuenta(cuentaRequestDTO);
        cuenta.setClienteId(cliente.getClienteId());
        Cuenta cuentaCreada = cuentaPortIn.crearCuenta(cuenta);
        CuentaResponseDTO cuentaResponse = cuentaWebMapper.toResponseCuenta(cuentaCreada);
        return new ApiResponse<>("Cuenta creada exitosamente", cuentaResponse);
    }

    @GetMapping("/{cuentaId}")
    ApiResponse<CuentaResponseDTO> buscarCuenta(@PathVariable Long cuentaId) {
        Cuenta cuenta = cuentaPortIn.buscarCuenta(cuentaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
        CuentaResponseDTO cuentaResponse = cuentaWebMapper.toResponseCuenta(cuenta);
        return new ApiResponse<>("Cuenta encontrada", cuentaResponse);
    }

    @PutMapping("/{cuentaId}")
    ApiResponse<CuentaResponseDTO> actualizarCuenta(@PathVariable Long cuentaId, @RequestBody CuentaRequestDTO cuentaRequestDTO) {
        Cuenta cuenta = cuentaWebMapper.toDomainCuenta(cuentaRequestDTO);
        cuenta.setCuentaId(cuentaId);
        Cuenta actualizada = cuentaPortIn.actualizarCuenta(cuenta);
        CuentaResponseDTO cuentaResponse = cuentaWebMapper.toResponseCuenta(actualizada);
        return new ApiResponse<>("Cuenta actualizada exitosamente", cuentaResponse);
    }

    @DeleteMapping("/{cuentaId}")
    ApiResponse<Void> eliminarCuenta(@PathVariable Long cuentaId) {
        cuentaPortIn.eliminarCuenta(cuentaId);
        return new ApiResponse<>("Cuenta eliminada exitosamente", null);
    }

    @GetMapping("/cliente/{clienteId}")
    ApiResponse<List<CuentaResponseDTO>> listarCuentasPorCliente(@PathVariable Long clienteId) {
        List<CuentaResponseDTO> cuentas = cuentaPortIn.listarCuentasPorCliente(clienteId)
                .stream()
                .map(cuenta -> cuentaWebMapper.toResponseCuenta(cuenta))
                .toList();
        return new ApiResponse<>("Cuentas encontradas", cuentas);
    }

}
