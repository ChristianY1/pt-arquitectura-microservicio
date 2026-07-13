package com.sofka.api_transaccional.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.exception.ReglaNegocioException;
import com.sofka.api_transaccional.domain.exception.RecursoNoEncontradoException;
import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.model.ReporteMovimiento;
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

    /**
     * Crea un nuevo movimiento sobre una cuenta activa, calculando el saldo disponible
     * a partir del último movimiento registrado (o del saldo inicial si es el primero).
     *
     * @param movimiento datos del movimiento a crear (con el id de la cuenta ya asignado)
     * @return el movimiento creado, con el saldo disponible calculado
     * @throws RecursoNoEncontradoException si la cuenta no existe
     * @throws ReglaNegocioException si la cuenta no está activa o si el movimiento
     *                                deja el saldo en negativo
     */
    @Override
    public Movimiento crearMovimiento(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepositoryPortOut.buscarCuenta(movimiento.getCuentaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta no encontrada"));

        if (!cuenta.isEstado()) {
            throw new ReglaNegocioException("La cuenta no está activa");
        }

        BigDecimal saldoBase = movimientoRepositoryPortOut.buscarUltimoMovimientoPorCuenta(movimiento.getCuentaId())
                .map(ultimoMovimiento -> ultimoMovimiento.getSaldoDisponible())
                .orElse(cuenta.getSaldoInicial());

        BigDecimal nuevoSaldoDisponible = saldoBase.add(movimiento.getValor());

        if (nuevoSaldoDisponible.compareTo(BigDecimal.ZERO) < 0) {
            throw new ReglaNegocioException("Saldo no disponible");
        }

        movimiento.setSaldoDisponible(nuevoSaldoDisponible);
        return movimientoRepositoryPortOut.crearMovimiento(movimiento);
    }

    /**
     * Busca un movimiento por su identificador interno.
     *
     * @param movimientoId id del movimiento
     * @return el movimiento encontrado, o null si no existe
     */
    @Override
    public Optional<Movimiento> buscarMovimiento(Long movimientoId) {
        return movimientoRepositoryPortOut.buscarMovimiento(movimientoId);
    }

    /**
     * Actualiza los datos de un movimiento existente.
     *
     * @param movimiento datos actualizados del movimiento
     * @return el movimiento actualizado
     */
    @Override
    public Movimiento actualizarMovimiento(Movimiento movimiento) {
        return movimientoRepositoryPortOut.actualizarMovimiento(movimiento);
    }

    /**
     * Elimina un movimiento por su identificador.
     *
     * @param movimientoId id del movimiento a eliminar
     */
    @Override
    public void eliminarMovimiento(Long movimientoId) {
        movimientoRepositoryPortOut.eliminarMovimiento(movimientoId);
    }

    /**
     * Lista todos los movimientos asociados a una cuenta.
     *
     * @param cuentaId id de la cuenta
     * @return los movimientos de la cuenta; una lista vacía si no tiene ninguno
     */
    @Override
    public List<Movimiento> listarMovimientosPorCuenta(Long cuentaId) {
        return movimientoRepositoryPortOut.listarMovimientosPorCuenta(cuentaId);
    }

    /**
     * Genera el reporte de movimientos de un cliente (identificado por su cédula) dentro
     * de un rango de fechas, que no puede superar los 30 días.
     *
     * @param identificacion cédula del cliente
     * @param desde fecha y hora de inicio del rango (inclusive)
     * @param hasta fecha y hora de fin del rango (inclusive)
     * @return los movimientos encontrados en el rango; una lista vacía si no hay ninguno
     * @throws ReglaNegocioException si la diferencia entre la fecha inicio y fecha fin
     *                                supera los 30 días
     */
    @Override
    public List<ReporteMovimiento> buscarReporteMovimientos(String identificacion, LocalDateTime desde, LocalDateTime hasta) {
        if (ChronoUnit.DAYS.between(desde, hasta) > 30) {
            throw new ReglaNegocioException("El rango de fechas no puede ser mayor a 30 días");
        }
        return movimientoRepositoryPortOut.buscarReporteMovimientos(identificacion, desde, hasta);
    }

}
