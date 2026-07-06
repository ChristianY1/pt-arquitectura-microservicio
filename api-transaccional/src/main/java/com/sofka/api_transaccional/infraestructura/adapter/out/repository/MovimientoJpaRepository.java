package com.sofka.api_transaccional.infraestructura.adapter.out.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sofka.api_transaccional.infraestructura.adapter.out.entity.MovimientoEntity;

public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {

    List<MovimientoEntity> findByCuentaEntity_CuentaId(Long cuentaId);

    @Query(value = """
            SELECT *
            FROM movimientos
            WHERE fk_cuenta_id = :cuentaId
            ORDER BY movimiento_id DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<MovimientoEntity> buscarUltimoMovimientoPorCuenta(@Param("cuentaId") Long cuentaId);

}
