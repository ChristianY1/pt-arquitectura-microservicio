package com.sofka.api_transaccional.application.service;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.port.in.CuentaPortIn;
import com.sofka.api_transaccional.domain.port.out.CuentaRepositoryPortOut;

public class CuentaService implements CuentaPortIn {

    private final CuentaRepositoryPortOut cuentaRepositoryPortOut;

    public CuentaService(CuentaRepositoryPortOut cuentaRepositoryPortOut) {
        this.cuentaRepositoryPortOut = cuentaRepositoryPortOut;
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepositoryPortOut.crearCuenta(cuenta);
    }

    @Override
    public Optional<Cuenta> buscarCuenta(Long cuentaId) {
        return cuentaRepositoryPortOut.buscarCuenta(cuentaId);
    }

    @Override
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        return cuentaRepositoryPortOut.actualizarCuenta(cuenta);
    }

    @Override
    public void eliminarCuenta(Long cuentaId) {
        cuentaRepositoryPortOut.eliminarCuenta(cuentaId);
    }

    @Override
    public List<Cuenta> listarCuentasPorCliente(Long clienteId) {
        return cuentaRepositoryPortOut.listarCuentasPorCliente(clienteId);
    }

}
