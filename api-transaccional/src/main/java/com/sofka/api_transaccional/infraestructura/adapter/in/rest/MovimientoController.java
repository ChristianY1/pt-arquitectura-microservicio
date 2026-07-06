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
import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.port.in.CuentaPortIn;
import com.sofka.api_transaccional.domain.port.in.MovimientoPortIn;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.MovimientoRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.MovimientoResponseDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.MovimientoWebMapper;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoPortIn movimientoPortIn;

    private final CuentaPortIn cuentaPortIn;

    private final MovimientoWebMapper movimientoWebMapper;

    public MovimientoController(MovimientoPortIn movimientoPortIn, CuentaPortIn cuentaPortIn, MovimientoWebMapper movimientoWebMapper) {
        this.movimientoPortIn = movimientoPortIn;
        this.cuentaPortIn = cuentaPortIn;
        this.movimientoWebMapper = movimientoWebMapper;
    }

    @PostMapping
    ResponseEntity<Map<String, Object>> crearMovimiento(@RequestBody MovimientoRequestDTO movimientoRequestDTO) {
        Cuenta cuenta = cuentaPortIn.buscarCuentaPorNumero(movimientoRequestDTO.numeroCuenta())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
        Movimiento movimiento = movimientoWebMapper.toDomainMovimiento(movimientoRequestDTO);
        movimiento.setCuentaId(cuenta.getCuentaId());
        Movimiento movimientoCreado = movimientoPortIn.crearMovimiento(movimiento);
        MovimientoResponseDTO movimientoResponse = movimientoWebMapper.toResponseMovimiento(movimientoCreado);
        return ResponseBuilder.build(HttpStatus.OK, "Movimiento creado exitosamente", movimientoResponse);
    }

    @GetMapping("/{movimientoId}")
    ResponseEntity<Map<String, Object>> buscarMovimiento(@PathVariable Long movimientoId) {
        Movimiento movimiento = movimientoPortIn.buscarMovimiento(movimientoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado"));
        MovimientoResponseDTO movimientoResponse = movimientoWebMapper.toResponseMovimiento(movimiento);
        return ResponseBuilder.build(HttpStatus.OK, "Movimiento encontrado", movimientoResponse);
    }

    @PutMapping("/{movimientoId}")
    ResponseEntity<Map<String, Object>> actualizarMovimiento(@PathVariable Long movimientoId, @RequestBody MovimientoRequestDTO movimientoRequestDTO) {
        Movimiento movimiento = movimientoWebMapper.toDomainMovimiento(movimientoRequestDTO);
        movimiento.setMovimientoId(movimientoId);
        Movimiento actualizado = movimientoPortIn.actualizarMovimiento(movimiento);
        MovimientoResponseDTO movimientoResponse = movimientoWebMapper.toResponseMovimiento(actualizado);
        return ResponseBuilder.build(HttpStatus.OK, "Movimiento actualizado exitosamente", movimientoResponse);
    }

    @DeleteMapping("/{movimientoId}")
    ResponseEntity<Map<String, Object>> eliminarMovimiento(@PathVariable Long movimientoId) {
        movimientoPortIn.eliminarMovimiento(movimientoId);
        return ResponseBuilder.build(HttpStatus.OK, "Movimiento eliminado exitosamente", null);
    }

    @GetMapping("/cuenta/{cuentaId}")
    ResponseEntity<Map<String, Object>> listarMovimientosPorCuenta(@PathVariable Long cuentaId) {
        List<MovimientoResponseDTO> movimientos = movimientoPortIn.listarMovimientosPorCuenta(cuentaId)
                .stream()
                .map(movimiento -> movimientoWebMapper.toResponseMovimiento(movimiento))
                .toList();
        return ResponseBuilder.build(HttpStatus.OK, "Movimientos encontrados", movimientos);
    }

}
