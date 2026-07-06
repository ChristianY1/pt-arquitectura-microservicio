package com.sofka.api_transaccional.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        validarNumeroCuenta(cuenta.getNumeroCuenta());
        return cuentaRepositoryPortOut.crearCuenta(cuenta);
    }

    @Override
    public Optional<Cuenta> buscarCuenta(Long cuentaId) {
        return cuentaRepositoryPortOut.buscarCuenta(cuentaId);
    }

    @Override
    public Optional<Cuenta> buscarCuentaPorNumero(String numeroCuenta) {
        return cuentaRepositoryPortOut.buscarCuentaPorNumero(numeroCuenta);
    }

    @Override
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        validarNumeroCuenta(cuenta.getNumeroCuenta());
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

    private void validarNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número de cuenta es obligatorio");
        }
    }

}
