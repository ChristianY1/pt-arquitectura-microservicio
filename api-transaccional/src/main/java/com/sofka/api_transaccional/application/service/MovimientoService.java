package com.sofka.api_transaccional.application.service;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.port.in.MovimientoPortIn;
import com.sofka.api_transaccional.domain.port.out.MovimientoRepositoryPortOut;

public class MovimientoService implements MovimientoPortIn {

    private final MovimientoRepositoryPortOut movimientoRepositoryPortOut;

    public MovimientoService(MovimientoRepositoryPortOut movimientoRepositoryPortOut) {
        this.movimientoRepositoryPortOut = movimientoRepositoryPortOut;
    }

    @Override
    public Movimiento crearMovimiento(Movimiento movimiento) {
        return movimientoRepositoryPortOut.crearMovimiento(movimiento);
    }

    @Override
    public Optional<Movimiento> buscarMovimiento(Long movimientoId) {
        return movimientoRepositoryPortOut.buscarMovimiento(movimientoId);
    }

    @Override
    public Movimiento actualizarMovimiento(Movimiento movimiento) {
        return movimientoRepositoryPortOut.actualizarMovimiento(movimiento);
    }

    @Override
    public void eliminarMovimiento(Long movimientoId) {
        movimientoRepositoryPortOut.eliminarMovimiento(movimientoId);
    }

    @Override
    public List<Movimiento> listarMovimientosPorCuenta(Long cuentaId) {
        return movimientoRepositoryPortOut.listarMovimientosPorCuenta(cuentaId);
    }

}
