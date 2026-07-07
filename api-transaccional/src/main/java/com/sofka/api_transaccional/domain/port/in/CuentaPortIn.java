package com.sofka.api_transaccional.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cuenta;

public interface CuentaPortIn {

    Cuenta crearCuenta(Cuenta cuenta, String identificacionCliente);

    Optional<Cuenta> buscarCuenta(Long cuentaId);

    Optional<Cuenta> buscarCuentaPorNumero(String numeroCuenta);

    Cuenta actualizarCuenta(Cuenta cuenta);

    void eliminarCuenta(Long cuentaId);

    List<Cuenta> listarCuentasPorCliente(Long clienteId);

}
