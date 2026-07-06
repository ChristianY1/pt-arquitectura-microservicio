package com.sofka.api_transaccional.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cuenta;

public interface CuentaRepositoryPortOut {

    Cuenta crearCuenta(Cuenta cuenta);

    Optional<Cuenta> buscarCuenta(Long cuentaId);

    Optional<Cuenta> buscarCuentaPorNumero(String numeroCuenta);

    Cuenta actualizarCuenta(Cuenta cuenta);

    void eliminarCuenta(Long cuentaId);

    List<Cuenta> listarCuentasPorCliente(Long clienteId);

}
