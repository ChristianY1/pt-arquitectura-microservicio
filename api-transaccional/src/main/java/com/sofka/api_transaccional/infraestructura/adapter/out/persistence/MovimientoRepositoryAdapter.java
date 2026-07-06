package com.sofka.api_transaccional.infraestructura.adapter.out.persistence;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.model.ReporteMovimiento;
import com.sofka.api_transaccional.domain.port.out.MovimientoRepositoryPortOut;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.MovimientoEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.MovimientoMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.CuentaJpaRepository;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.MovimientoJpaRepository;

public class MovimientoRepositoryAdapter implements MovimientoRepositoryPortOut {

    private final MovimientoJpaRepository movimientoJpaRepository;

    private final CuentaJpaRepository cuentaJpaRepository;

    private final MovimientoMapper movimientoMapper;

    public MovimientoRepositoryAdapter(MovimientoJpaRepository movimientoJpaRepository, CuentaJpaRepository cuentaJpaRepository, MovimientoMapper movimientoMapper) {
        this.movimientoJpaRepository = movimientoJpaRepository;
        this.cuentaJpaRepository = cuentaJpaRepository;
        this.movimientoMapper = movimientoMapper;
    }

    /**
     * Persiste un nuevo movimiento en la base de datos, asociándolo a la cuenta indicada.
     *
     * @param movimiento datos del movimiento a guardar
     * @return el movimiento guardado, con el identificador generado por la base de datos
     * @throws ResponseStatusException 404 si la cuenta asociada no existe
     */
    @Override
    public Movimiento crearMovimiento(Movimiento movimiento) {
        if (!cuentaJpaRepository.existsById(movimiento.getCuentaId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }
        MovimientoEntity movimientoEntity = movimientoMapper.toEntityMovimiento(movimiento);
        return movimientoMapper.toDomainMovimiento(movimientoJpaRepository.save(movimientoEntity));
    }

    /**
     * Consulta en la base de datos un movimiento por su identificador interno.
     *
     * @param movimientoId id del movimiento
     * @return el movimiento encontrado, null si no existe
     */
    @Override
    public Optional<Movimiento> buscarMovimiento(Long movimientoId) {
        Optional<MovimientoEntity> movimientoEntityOptional = movimientoJpaRepository.findById(movimientoId);
        return movimientoEntityOptional.map(movimientoEntity -> movimientoMapper.toDomainMovimiento(movimientoEntity));
    }

    /**
     * Actualiza en la base de datos los datos de un movimiento existente, conservando
     * el id de la cuenta y el saldo disponible ya calculado (no se recalculan en una actualización).
     *
     * @param movimiento datos actualizados del movimiento
     * @return el movimiento actualizado
     * @throws ResponseStatusException 404 si el movimiento no existe
     */
    @Override
    public Movimiento actualizarMovimiento(Movimiento movimiento) {
        Movimiento movimientoExistente = buscarMovimiento(movimiento.getMovimientoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado"));
        movimiento.setCuentaId(movimientoExistente.getCuentaId());
        movimiento.setSaldoDisponible(movimientoExistente.getSaldoDisponible());
        MovimientoEntity movimientoEntity = movimientoMapper.toEntityMovimiento(movimiento);
        return movimientoMapper.toDomainMovimiento(movimientoJpaRepository.save(movimientoEntity));
    }

    /**
     * Elimina de la base de datos un movimiento por su identificador.
     *
     * @param movimientoId id del movimiento a eliminar
     * @throws ResponseStatusException 404 si el movimiento no existe
     */
    @Override
    public void eliminarMovimiento(Long movimientoId) {
        if (buscarMovimiento(movimientoId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado");
        }
        movimientoJpaRepository.deleteById(movimientoId);
    }

    /**
     * Consulta en la base de datos todos los movimientos asociados a una cuenta.
     *
     * @param cuentaId id de la cuenta
     * @return los movimientos de la cuenta; una lista vacía si no tiene ninguno
     */
    @Override
    public List<Movimiento> listarMovimientosPorCuenta(Long cuentaId) {
        return movimientoJpaRepository.findByCuentaEntity_CuentaId(cuentaId)
                .stream()
                .map(movimientoEntity -> movimientoMapper.toDomainMovimiento(movimientoEntity))
                .toList();
    }

    /**
     * Consulta en la base de datos el último movimiento registrado de una cuenta,
     * usado como base para calcular el saldo
     * disponible del siguiente movimiento.
     *
     * @param cuentaId id de la cuenta
     * @return el último movimiento de la cuenta, o {@link Optional#empty()} si no tiene ninguno
     */
    @Override
    public Optional<Movimiento> buscarUltimoMovimientoPorCuenta(Long cuentaId) {
        Optional<MovimientoEntity> movimientoEntityOptional = movimientoJpaRepository.buscarUltimoMovimientoPorCuenta(cuentaId);
        return movimientoEntityOptional.map(movimientoEntity -> movimientoMapper.toDomainMovimiento(movimientoEntity));
    }

    /**
     * Ejecuta en la base de datos el reporte de movimientos de un cliente (por cédula)
     * dentro de un rango de fechas, uniendo las tablas de personas, clientes, cuentas y movimientos,
     * dando como resultado el estado de cuenta
     *
     * @param identificacion cédula del cliente
     * @param desde fecha y hora de inicio del rango (inclusive)
     * @param hasta fecha y hora de fin del rango (inclusive)
     * @return los movimientos encontrados en el rango; una lista vacía si no hay ninguno
     */
    @Override
    public List<ReporteMovimiento> buscarReporteMovimientos(String identificacion, LocalDateTime desde, LocalDateTime hasta) {
        return movimientoJpaRepository.buscarReporteMovimientos(identificacion, desde, hasta)
                .stream()
                .map(reporteMovimientoDTO -> movimientoMapper.toDomainReporteMovimiento(reporteMovimientoDTO))
                .toList();
    }

}
