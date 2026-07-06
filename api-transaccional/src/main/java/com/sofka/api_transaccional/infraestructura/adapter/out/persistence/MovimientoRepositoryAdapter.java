package com.sofka.api_transaccional.infraestructura.adapter.out.persistence;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado"));
        movimiento.setCuentaId(movimientoExistente.getCuentaId());
        movimiento.setSaldoDisponible(movimientoExistente.getSaldoDisponible());
        MovimientoEntity movimientoEntity = movimientoMapper.toEntityMovimiento(movimiento);
        return movimientoMapper.toDomainMovimiento(movimientoJpaRepository.save(movimientoEntity));
    }

    @Override
    public void eliminarMovimiento(Long movimientoId) {
        if (buscarMovimiento(movimientoId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado");
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

    @Override
    public Optional<Movimiento> buscarUltimoMovimientoPorCuenta(Long cuentaId) {
        Optional<MovimientoEntity> movimientoEntityOptional = movimientoJpaRepository.buscarUltimoMovimientoPorCuenta(cuentaId);
        return movimientoEntityOptional.map(movimientoEntity -> movimientoMapper.toDomainMovimiento(movimientoEntity));
    }

}
