package com.sofka.api_transaccional.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import com.sofka.api_transaccional.domain.model.Cliente;
import com.sofka.api_transaccional.domain.model.Cuenta;
import com.sofka.api_transaccional.domain.port.in.CuentaPortIn;
import com.sofka.api_transaccional.domain.port.out.ClienteRepositoryPortOut;
import com.sofka.api_transaccional.domain.port.out.CuentaRepositoryPortOut;

public class CuentaService implements CuentaPortIn {

    private final CuentaRepositoryPortOut cuentaRepositoryPortOut;

    private final ClienteRepositoryPortOut clienteRepositoryPortOut;

    public CuentaService(CuentaRepositoryPortOut cuentaRepositoryPortOut, ClienteRepositoryPortOut clienteRepositoryPortOut) {
        this.cuentaRepositoryPortOut = cuentaRepositoryPortOut;
        this.clienteRepositoryPortOut = clienteRepositoryPortOut;
    }

    /**
     * Crea una nueva cuenta, validando que el número de cuenta sea válido y único,
     * y que el cliente asociado exista y esté activo.
     *
     * @param cuenta datos de la cuenta a crear
     * @return la cuenta creada, con el identificador generado
     * @throws ResponseStatusException 400 si el número de cuenta es inválido, ya existe
     *                                  o el cliente no está activo; 404 si el cliente no existe
     */
    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        validarNumeroCuenta(cuenta.getNumeroCuenta());
        if (cuentaRepositoryPortOut.buscarCuentaPorNumero(cuenta.getNumeroCuenta()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una cuenta con este número");
        }
        Cliente cliente = clienteRepositoryPortOut.buscarCliente(cuenta.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        if (!cliente.isEstado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no está activo");
        }
        return cuentaRepositoryPortOut.crearCuenta(cuenta);
    }

    /**
     * Busca una cuenta por su identificador interno.
     *
     * @param cuentaId id de la cuenta
     * @return la cuenta encontrada, o null si no existe
     */
    @Override
    public Optional<Cuenta> buscarCuenta(Long cuentaId) {
        return cuentaRepositoryPortOut.buscarCuenta(cuentaId);
    }

    /**
     * Busca una cuenta por su número de cuenta.
     *
     * @param numeroCuenta número de la cuenta
     * @return la cuenta encontrada, o null si no existe
     */
    @Override
    public Optional<Cuenta> buscarCuentaPorNumero(String numeroCuenta) {
        return cuentaRepositoryPortOut.buscarCuentaPorNumero(numeroCuenta);
    }

    /**
     * Actualiza los datos de una cuenta existente, validando previamente el número de cuenta.
     *
     * @param cuenta datos actualizados de la cuenta
     * @return la cuenta actualizada
     * @throws ResponseStatusException 400 si el número de cuenta es inválido
     */
    @Override
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        validarNumeroCuenta(cuenta.getNumeroCuenta());
        return cuentaRepositoryPortOut.actualizarCuenta(cuenta);
    }

    /**
     * Elimina una cuenta por su identificador.
     *
     * @param cuentaId id de la cuenta a eliminar
     */
    @Override
    public void eliminarCuenta(Long cuentaId) {
        cuentaRepositoryPortOut.eliminarCuenta(cuentaId);
    }

    /**
     * Lista todas las cuentas asociadas a un cliente.
     *
     * @param clienteId id del cliente
     * @return las cuentas del cliente; una lista vacía si no tiene ninguna
     */
    @Override
    public List<Cuenta> listarCuentasPorCliente(Long clienteId) {
        return cuentaRepositoryPortOut.listarCuentasPorCliente(clienteId);
    }

    /**
     * Valida que el número de cuenta no sea nulo ni esté en blanco.
     *
     * @param numeroCuenta número de cuenta a validar
     * @throws ResponseStatusException 400 si el número de cuenta es nulo o está en blanco
     */
    private void validarNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número de cuenta es obligatorio");
        }
    }

}
