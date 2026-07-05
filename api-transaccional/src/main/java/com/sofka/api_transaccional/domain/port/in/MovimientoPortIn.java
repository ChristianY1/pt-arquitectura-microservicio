package com.sofka.api_transaccional.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Movimiento;

public interface MovimientoPortIn {

    Movimiento crearMovimiento(Movimiento movimiento);

    Optional<Movimiento> buscarMovimiento(Long movimientoId);

    Movimiento actualizarMovimiento(Movimiento movimiento);

    void eliminarMovimiento(Long movimientoId);

    List<Movimiento> listarMovimientosPorCuenta(Long cuentaId);

}
