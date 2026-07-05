package com.sofka.api_transaccional.infraestructura.adapter.in.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.api_transaccional.domain.exception.MovimientoNoEncontradoException;
import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.port.in.MovimientoPortIn;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.request.MovimientoRequestDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.ApiResponse;
import com.sofka.api_transaccional.infraestructura.adapter.in.dto.response.MovimientoResponseDTO;
import com.sofka.api_transaccional.infraestructura.adapter.in.mapper.MovimientoWebMapper;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoPortIn movimientoPortIn;

    private final MovimientoWebMapper movimientoWebMapper;

    public MovimientoController(MovimientoPortIn movimientoPortIn, MovimientoWebMapper movimientoWebMapper) {
        this.movimientoPortIn = movimientoPortIn;
        this.movimientoWebMapper = movimientoWebMapper;
    }

    @PostMapping
    ApiResponse<MovimientoResponseDTO> crearMovimiento(@RequestBody MovimientoRequestDTO movimientoRequestDTO) {
        Movimiento movimiento = movimientoPortIn.crearMovimiento(movimientoWebMapper.toDomainMovimiento(movimientoRequestDTO));
        MovimientoResponseDTO movimientoResponse = movimientoWebMapper.toResponseMovimiento(movimiento);
        return new ApiResponse<>("Movimiento creado exitosamente", movimientoResponse);
    }

    @GetMapping("/{movimientoId}")
    ApiResponse<MovimientoResponseDTO> buscarMovimiento(@PathVariable Long movimientoId) {
        Movimiento movimiento = movimientoPortIn.buscarMovimiento(movimientoId)
                .orElseThrow(() -> new MovimientoNoEncontradoException("Movimiento no encontrado"));
        MovimientoResponseDTO movimientoResponse = movimientoWebMapper.toResponseMovimiento(movimiento);
        return new ApiResponse<>("Movimiento encontrado", movimientoResponse);
    }

    @PutMapping("/{movimientoId}")
    ApiResponse<MovimientoResponseDTO> actualizarMovimiento(@PathVariable Long movimientoId, @RequestBody MovimientoRequestDTO movimientoRequestDTO) {
        Movimiento movimiento = movimientoWebMapper.toDomainMovimiento(movimientoRequestDTO);
        movimiento.setMovimientoId(movimientoId);
        Movimiento actualizado = movimientoPortIn.actualizarMovimiento(movimiento);
        MovimientoResponseDTO movimientoResponse = movimientoWebMapper.toResponseMovimiento(actualizado);
        return new ApiResponse<>("Movimiento actualizado exitosamente", movimientoResponse);
    }

    @DeleteMapping("/{movimientoId}")
    ApiResponse<Void> eliminarMovimiento(@PathVariable Long movimientoId) {
        movimientoPortIn.eliminarMovimiento(movimientoId);
        return new ApiResponse<>("Movimiento eliminado exitosamente", null);
    }

    @GetMapping("/cuenta/{cuentaId}")
    ApiResponse<List<MovimientoResponseDTO>> listarMovimientosPorCuenta(@PathVariable Long cuentaId) {
        List<MovimientoResponseDTO> movimientos = movimientoPortIn.listarMovimientosPorCuenta(cuentaId)
                .stream()
                .map(movimiento -> movimientoWebMapper.toResponseMovimiento(movimiento))
                .toList();
        return new ApiResponse<>("Movimientos encontrados", movimientos);
    }

}
