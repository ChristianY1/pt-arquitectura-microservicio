package com.sofka.api_transaccional.infraestructura.adapter.out.persistence;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.port.out.CuentaRepositoryPortOut;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.CuentaEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.CuentaMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.CuentaJpaRepository;

public class CuentaRepositoryAdapter implements CuentaRepositoryPortOut {

    private final CuentaJpaRepository cuentaJpaRepository;

    private final CuentaMapper cuentaMapper;

    public CuentaRepositoryAdapter(CuentaJpaRepository cuentaJpaRepository, CuentaMapper cuentaMapper) {
        this.cuentaJpaRepository = cuentaJpaRepository;
        this.cuentaMapper = cuentaMapper;
    }

    /**
     * Persiste una nueva cuenta en la base de datos, asociándola al cliente indicado.
     *
     * @param cuenta datos de la cuenta a guardar
     * @return la cuenta guardada, con el identificador generado por la base de datos
     */
    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        CuentaEntity cuentaEntity = cuentaMapper.toEntityCuenta(cuenta);
        return cuentaMapper.toDomainCuenta(cuentaJpaRepository.save(cuentaEntity));
    }

    /**
     * Consulta en la base de datos una cuenta por su identificador interno.
     *
     * @param cuentaId id de la cuenta
     * @return la cuenta encontrada, o null si no existe
     */
    @Override
    public Optional<Cuenta> buscarCuenta(Long cuentaId) {
        Optional<CuentaEntity> cuentaEntityOptional = cuentaJpaRepository.findById(cuentaId);
        return cuentaEntityOptional.map(cuentaEntity -> cuentaMapper.toDomainCuenta(cuentaEntity));
    }

    /**
     * Consulta en la base de datos una cuenta por su número de cuenta.
     *
     * @param numeroCuenta número de la cuenta
     * @return la cuenta encontrada, o null si no existe
     */
    @Override
    public Optional<Cuenta> buscarCuentaPorNumero(String numeroCuenta) {
        Optional<CuentaEntity> cuentaEntityOptional = cuentaJpaRepository.findByNumeroCuenta(numeroCuenta);
        return cuentaEntityOptional.map(cuentaEntity -> cuentaMapper.toDomainCuenta(cuentaEntity));
    }

    /**
     * Actualiza en la base de datos los datos de una cuenta existente, conservando
     * el id del cliente asociado (no se puede reasignar en una actualización).
     *
     * @param cuenta datos actualizados de la cuenta
     * @return la cuenta actualizada
     * @throws ResponseStatusException 404 si la cuenta no existe
     */
    @Override
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        Cuenta cuentaExistente = buscarCuenta(cuenta.getCuentaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
        cuenta.setClienteId(cuentaExistente.getClienteId());
        CuentaEntity cuentaEntity = cuentaMapper.toEntityCuenta(cuenta);
        return cuentaMapper.toDomainCuenta(cuentaJpaRepository.save(cuentaEntity));
    }

    /**
     * Elimina de la base de datos una cuenta por su identificador.
     *
     * @param cuentaId id de la cuenta a eliminar
     * @throws ResponseStatusException 404 si la cuenta no existe
     */
    @Override
    public void eliminarCuenta(Long cuentaId) {
        if (buscarCuenta(cuentaId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }
        cuentaJpaRepository.deleteById(cuentaId);
    }

    /**
     * Consulta en la base de datos todas las cuentas asociadas a un cliente.
     *
     * @param clienteId id del cliente
     * @return las cuentas del cliente; una lista vacía si no tiene ninguna
     */
    @Override
    public List<Cuenta> listarCuentasPorCliente(Long clienteId) {
        return cuentaJpaRepository.findByClienteId(clienteId)
                .stream()
                .map(cuentaEntity -> cuentaMapper.toDomainCuenta(cuentaEntity))
                .toList();
    }

}
