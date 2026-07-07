package com.sofka.api_transaccional.infraestructura.adapter.in.rest;

import java.util.List;
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

import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.port.in.CuentaPortIn;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.CuentaRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.CuentaResponseDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.CuentaWebMapper;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaPortIn cuentaPortIn;

    private final CuentaWebMapper cuentaWebMapper;

    public CuentaController(CuentaPortIn cuentaPortIn, CuentaWebMapper cuentaWebMapper) {
        this.cuentaPortIn = cuentaPortIn;
        this.cuentaWebMapper = cuentaWebMapper;
    }

    @PostMapping
    ResponseEntity<Map<String, Object>> crearCuenta(@RequestBody CuentaRequestDTO cuentaRequestDTO) {
        Cuenta cuenta = cuentaWebMapper.toDomainCuenta(cuentaRequestDTO);
        Cuenta cuentaCreada = cuentaPortIn.crearCuenta(cuenta, cuentaRequestDTO.identificacion());
        CuentaResponseDTO cuentaResponse = cuentaWebMapper.toResponseCuenta(cuentaCreada);
        return ResponseBuilder.build(HttpStatus.OK, "Cuenta creada exitosamente", cuentaResponse);
    }

    @GetMapping("/{cuentaId}")
    ResponseEntity<Map<String, Object>> buscarCuenta(@PathVariable Long cuentaId) {
        Cuenta cuenta = cuentaPortIn.buscarCuenta(cuentaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
        CuentaResponseDTO cuentaResponse = cuentaWebMapper.toResponseCuenta(cuenta);
        return ResponseBuilder.build(HttpStatus.OK, "Cuenta encontrada", cuentaResponse);
    }

    @PutMapping("/{cuentaId}")
    ResponseEntity<Map<String, Object>> actualizarCuenta(@PathVariable Long cuentaId, @RequestBody CuentaRequestDTO cuentaRequestDTO) {
        Cuenta cuenta = cuentaWebMapper.toDomainCuenta(cuentaRequestDTO);
        cuenta.setCuentaId(cuentaId);
        Cuenta actualizada = cuentaPortIn.actualizarCuenta(cuenta);
        CuentaResponseDTO cuentaResponse = cuentaWebMapper.toResponseCuenta(actualizada);
        return ResponseBuilder.build(HttpStatus.OK, "Cuenta actualizada exitosamente", cuentaResponse);
    }

    @DeleteMapping("/{cuentaId}")
    ResponseEntity<Map<String, Object>> eliminarCuenta(@PathVariable Long cuentaId) {
        cuentaPortIn.eliminarCuenta(cuentaId);
        return ResponseBuilder.build(HttpStatus.OK, "Cuenta eliminada exitosamente", null);
    }

    @GetMapping("/cliente/{clienteId}")
    ResponseEntity<Map<String, Object>> listarCuentasPorCliente(@PathVariable Long clienteId) {
        List<CuentaResponseDTO> cuentas = cuentaPortIn.listarCuentasPorCliente(clienteId)
                .stream()
                .map(cuenta -> cuentaWebMapper.toResponseCuenta(cuenta))
                .toList();
        return ResponseBuilder.build(HttpStatus.OK, "Cuentas encontradas", cuentas);
    }

}
