package com.sofka.api_transaccional.infraestructura.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.exception.CuentaNoEncontradaException;
import com.sofka.api_transaccional.domain.exception.MovimientoNoEncontradoException;
import com.sofka.api_transaccional.domain.model.Movimiento;
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

    @Override
    public Movimiento crearMovimiento(Movimiento movimiento) {
        if (!cuentaJpaRepository.existsById(movimiento.getCuentaId())) {
            throw new CuentaNoEncontradaException("Cuenta no encontrada");
        }
        MovimientoEntity movimientoEntity = movimientoMapper.toEntityMovimiento(movimiento);
        return movimientoMapper.toDomainMovimiento(movimientoJpaRepository.save(movimientoEntity));
    }

    @Override
    public Optional<Movimiento> buscarMovimiento(Long movimientoId) {
        Optional<MovimientoEntity> movimientoEntityOptional = movimientoJpaRepository.findById(movimientoId);
        return movimientoEntityOptional.map(movimientoEntity -> movimientoMapper.toDomainMovimiento(movimientoEntity));
    }

    @Override
    public Movimiento actualizarMovimiento(Movimiento movimiento) {
        Movimiento movimientoExistente = buscarMovimiento(movimiento.getMovimientoId())
                .orElseThrow(() -> new MovimientoNoEncontradoException("Movimiento no encontrado"));
        movimiento.setCuentaId(movimientoExistente.getCuentaId());
        MovimientoEntity movimientoEntity = movimientoMapper.toEntityMovimiento(movimiento);
        return movimientoMapper.toDomainMovimiento(movimientoJpaRepository.save(movimientoEntity));
    }

    @Override
    public void eliminarMovimiento(Long movimientoId) {
        if (buscarMovimiento(movimientoId).isEmpty()) {
            throw new MovimientoNoEncontradoException("Movimiento no encontrado");
        }
        movimientoJpaRepository.deleteById(movimientoId);
    }

    @Override
    public List<Movimiento> listarMovimientosPorCuenta(Long cuentaId) {
        return movimientoJpaRepository.findByCuentaEntity_CuentaId(cuentaId)
                .stream()
                .map(movimientoEntity -> movimientoMapper.toDomainMovimiento(movimientoEntity))
                .toList();
    }

}
