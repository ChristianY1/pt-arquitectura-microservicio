package com.sofka.api_transaccional.infraestructura.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.exception.ClienteNoEncontradoException;
import com.sofka.api_transaccional.domain.exception.CuentaNoEncontradaException;
import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.port.out.CuentaRepositoryPortOut;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.CuentaEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.CuentaMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.ClienteJpaRepository;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.CuentaJpaRepository;

public class CuentaRepositoryAdapter implements CuentaRepositoryPortOut {

    private final CuentaJpaRepository cuentaJpaRepository;

    private final ClienteJpaRepository clienteJpaRepository;

    private final CuentaMapper cuentaMapper;

    public CuentaRepositoryAdapter(CuentaJpaRepository cuentaJpaRepository, ClienteJpaRepository clienteJpaRepository, CuentaMapper cuentaMapper) {
        this.cuentaJpaRepository = cuentaJpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
        this.cuentaMapper = cuentaMapper;
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        if (!clienteJpaRepository.existsById(cuenta.getClienteId())) {
            throw new ClienteNoEncontradoException("Cliente no encontrado");
        }
        CuentaEntity cuentaEntity = cuentaMapper.toEntityCuenta(cuenta);
        return cuentaMapper.toDomainCuenta(cuentaJpaRepository.save(cuentaEntity));
    }

    @Override
    public Optional<Cuenta> buscarCuenta(Long cuentaId) {
        Optional<CuentaEntity> cuentaEntityOptional = cuentaJpaRepository.findById(cuentaId);
        return cuentaEntityOptional.map(cuentaEntity -> cuentaMapper.toDomainCuenta(cuentaEntity));
    }

    @Override
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        Cuenta cuentaExistente = buscarCuenta(cuenta.getCuentaId())
                .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada"));
        cuenta.setClienteId(cuentaExistente.getClienteId());
        CuentaEntity cuentaEntity = cuentaMapper.toEntityCuenta(cuenta);
        return cuentaMapper.toDomainCuenta(cuentaJpaRepository.save(cuentaEntity));
    }

    @Override
    public void eliminarCuenta(Long cuentaId) {
        if (buscarCuenta(cuentaId).isEmpty()) {
            throw new CuentaNoEncontradaException("Cuenta no encontrada");
        }
        cuentaJpaRepository.deleteById(cuentaId);
    }

    @Override
    public List<Cuenta> listarCuentasPorCliente(Long clienteId) {
        return cuentaJpaRepository.findByClienteEntity_ClienteId(clienteId)
                .stream()
                .map(cuentaEntity -> cuentaMapper.toDomainCuenta(cuentaEntity))
                .toList();
    }

}
