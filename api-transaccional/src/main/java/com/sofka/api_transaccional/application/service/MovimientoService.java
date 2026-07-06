package com.sofka.api_transaccional.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.port.in.MovimientoPortIn;
import com.sofka.api_transaccional.domain.port.out.CuentaRepositoryPortOut;
import com.sofka.api_transaccional.domain.port.out.MovimientoRepositoryPortOut;

public class MovimientoService implements MovimientoPortIn {

    private final MovimientoRepositoryPortOut movimientoRepositoryPortOut;

    private final CuentaRepositoryPortOut cuentaRepositoryPortOut;

    public MovimientoService(MovimientoRepositoryPortOut movimientoRepositoryPortOut, CuentaRepositoryPortOut cuentaRepositoryPortOut) {
        this.movimientoRepositoryPortOut = movimientoRepositoryPortOut;
        this.cuentaRepositoryPortOut = cuentaRepositoryPortOut;
    }

    @Override
    public Movimiento crearMovimiento(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepositoryPortOut.buscarCuenta(movimiento.getCuentaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (!cuenta.isEstado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cuenta no está activa");
        }

        BigDecimal saldoBase = movimientoRepositoryPortOut.buscarUltimoMovimientoPorCuenta(movimiento.getCuentaId())
                .map(ultimoMovimiento -> ultimoMovimiento.getSaldoDisponible())
                .orElse(cuenta.getSaldoInicial());

        BigDecimal nuevoSaldoDisponible = saldoBase.add(movimiento.getValor());

        if (nuevoSaldoDisponible.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo no disponible");
        }

        movimiento.setSaldoDisponible(nuevoSaldoDisponible);
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
