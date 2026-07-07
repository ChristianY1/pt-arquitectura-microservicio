package com.sofka.api_transaccional.infraestructura.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import com.sofka.api_transaccional.domain.model.Movimiento;
import com.sofka.api_transaccional.domain.model.TipoMovimiento;
import com.sofka.api_transaccional.infraestructura.adapter.out.entity.MovimientoEntity;
import com.sofka.api_transaccional.infraestructura.adapter.out.mapper.MovimientoMapper;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.CuentaJpaRepository;
import com.sofka.api_transaccional.infraestructura.adapter.out.repository.MovimientoJpaRepository;

/**
 * Test de integración : Insertar un movimiento de valor 0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class MovimientoRepositoryAdapterTest {

    private static final Long CUENTA_ID_SEMBRADA = 1L;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withCopyFileToContainer(
                    MountableFile.forHostPath(Path.of("..", "scripts-db", "transaccional", "BaseDatosTransaccional.sql")),
                    "/docker-entrypoint-initdb.d/01-base-datos-transaccional.sql");

    @Autowired
    private MovimientoJpaRepository movimientoJpaRepository;

    @Autowired
    private CuentaJpaRepository cuentaJpaRepository;

    private MovimientoRepositoryAdapter movimientoRepositoryAdapter;

    @BeforeEach
    void setUp() {
        movimientoRepositoryAdapter = new MovimientoRepositoryAdapter(movimientoJpaRepository, cuentaJpaRepository, new MovimientoMapper());
    }

    @Test
    void crearMovimiento_conCuentaExistente_loPersisteEnLaBaseDeDatos() {
        Movimiento movimiento = Movimiento.builder()
                .fechaMovimiento(LocalDateTime.now())
                .tipoMovimiento(TipoMovimiento.DEPOSITO)
                .valor(BigDecimal.valueOf(0))
                .saldoDisponible(BigDecimal.valueOf(2100))
                .cuentaId(CUENTA_ID_SEMBRADA)
                .build();

        Movimiento movimientoCreado = movimientoRepositoryAdapter.crearMovimiento(movimiento);

        assertThat(movimientoCreado.getMovimientoId()).isNotNull();

        MovimientoEntity movimientoPersistido = movimientoJpaRepository.findById(movimientoCreado.getMovimientoId())
                .orElseThrow(() -> new AssertionError("El movimiento no quedó persistido en la base de datos"));
        assertThat(movimientoPersistido.getValor()).isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(movimientoPersistido.getTipoMovimiento()).isEqualTo(TipoMovimiento.DEPOSITO);
        assertThat(movimientoPersistido.getCuentaEntity().getCuentaId()).isEqualTo(CUENTA_ID_SEMBRADA);
    }

}
