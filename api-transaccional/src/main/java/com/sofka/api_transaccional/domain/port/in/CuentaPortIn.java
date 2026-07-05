package com.sofka.api_transaccional.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cuenta;

public interface CuentaPortIn {

    Cuenta crearCuenta(Cuenta cuenta);

    Optional<Cuenta> buscarCuenta(Long cuentaId);

    Cuenta actualizarCuenta(Cuenta cuenta);

    void eliminarCuenta(Long cuentaId);

    List<Cuenta> listarCuentasPorCliente(Long clienteId);

}
