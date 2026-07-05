package com.sofka.api_transaccional.infraestructura.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofka.api_transaccional.infraestructura.adapter.out.entity.MovimientoEntity;

public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {

    List<MovimientoEntity> findByCuentaEntity_CuentaId(Long cuentaId);

}
