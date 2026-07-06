package com.sofka.api_transaccional.domain.port.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.model.ReporteMovimiento;

public interface MovimientoPortIn {

    Movimiento crearMovimiento(Movimiento movimiento);

    Optional<Movimiento> buscarMovimiento(Long movimientoId);

    Movimiento actualizarMovimiento(Movimiento movimiento);

    void eliminarMovimiento(Long movimientoId);

    List<Movimiento> listarMovimientosPorCuenta(Long cuentaId);

    List<ReporteMovimiento> buscarReporteMovimientos(String identificacion, LocalDateTime desde, LocalDateTime hasta);

}
