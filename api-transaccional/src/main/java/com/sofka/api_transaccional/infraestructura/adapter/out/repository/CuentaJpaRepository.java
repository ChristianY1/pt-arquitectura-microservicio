package com.sofka.api_transaccional.infraestructura.adapter.out.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofka.api_transaccional.infraestructura.adapter.out.entity.CuentaEntity;

public interface CuentaJpaRepository extends JpaRepository<CuentaEntity, Long> {

    List<CuentaEntity> findByClienteId(Long clienteId);

    Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);

}
