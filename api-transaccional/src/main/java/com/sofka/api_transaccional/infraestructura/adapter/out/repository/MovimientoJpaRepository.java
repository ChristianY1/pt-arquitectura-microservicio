package com.sofka.api_transaccional.infraestructura.adapter.out.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sofka.api_transaccional.infraestructura.adapter.out.dto.ReporteMovimientoDTO;
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

    @Query(value = """
            SELECT m.fecha_movimiento AS fechaMovimiento,
                   cr.nombre AS nombre,
                   c2.numero_cuenta AS numeroCuenta,
                   c2.tipo_cuenta AS tipoCuenta,
                   c2.saldo_inicial AS saldoInicial,
                   c2.estado AS estado,
                   m.valor AS valor,
                   m.saldo_disponible AS saldoDisponible
            FROM clientes_referencia cr
            JOIN cuentas c2 ON c2.fk_cliente_id = cr.cliente_id
            JOIN movimientos m ON m.fk_cuenta_id = c2.cuenta_id
            WHERE cr.identificacion = :identificacion
            AND m.fecha_movimiento BETWEEN :desde AND :hasta
            """, nativeQuery = true)
    List<ReporteMovimientoDTO> buscarReporteMovimientos(@Param("identificacion") String identificacion, 
                                                               @Param("desde") LocalDateTime desde, 
                                                               @Param("hasta") LocalDateTime hasta);

}
