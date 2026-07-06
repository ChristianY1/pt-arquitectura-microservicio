# PT-API-TRANSACCIONAL

Microservicio transaccional para el sector bancario para la generacion de movimientos, construido con **arquitectura hexagonal (puertos y adaptadores)** sobre Spring Boot.

## Dominio

- **Persona**: datos personales (nombre, género, edad, identificación, dirección, teléfono).
- **Cliente**: extiende a Persona con datos de acceso (usuario, contraseña, estado activo/inactivo).
- **Cuenta**: pertenece a un cliente; tiene número de cuenta único, tipo (`AHORROS`/`CORRIENTE`), saldo inicial y estado.
- **Movimiento**: pertenece a una cuenta; registra depósitos/retiros con su fecha, valor y el saldo disponible resultante (estilo *ledger*: cada movimiento guarda su propio snapshot de saldo, en vez de mutar el saldo de la cuenta).

## Stack técnico

- Java 17, Spring Boot 4.1.0
- Spring Data JPA + PostgreSQL
- Lombok
- Maven (con wrapper `mvnw`)
- Docker/Podman Compose para levantar el entorno completo

## Arquitectura

Estructura de paquetes bajo `com.sofka.api_transaccional`:

```
domain/
  model/        -> entidades de dominio puras (sin anotaciones de Spring/JPA)
  port/in/      -> puertos de entrada (casos de uso, uno por agregado)
  port/out/     -> puertos de salida (contratos de persistencia)
application/
  service/      -> implementación de los puertos de entrada; reglas de negocio
infraestructura/
  adapter/in/rest/      -> controllers REST
  adapter/in/dto/       -> DTOs de request/response (records)
  adapter/in/mapper/    -> DTO <-> dominio
  adapter/out/entity/   -> entidades JPA
  adapter/out/repository/ -> interfaces Spring Data JPA (incluye native queries/projections)
  adapter/out/mapper/   -> entidad JPA <-> dominio
  adapter/out/persistence/ -> adapters que implementan los puertos de salida
  config/               -> configuración de Spring (inyección manual de beans, JPA auditing)
```


## Reglas de negocio principales

- La identificación (cédula) debe tener exactamente 10 caracteres y ser única por persona.
- El usuario del cliente debe ser único.
- El número de cuenta debe ser único y obligatorio.
- Solo se puede crear una cuenta para un cliente con `estado = true`.
- Solo se puede registrar un movimiento sobre una cuenta con `estado = true`.
- Un movimiento no puede dejar el saldo disponible en negativo.
- El reporte de movimientos no admite un rango de fechas mayor a 30 días.

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/clientes/crearCliente` | Crea un cliente (y su persona asociada) |
| GET | `/clientes/{clienteId}` | Busca un cliente por id |
| PUT | `/clientes/actualizar/{clienteId}` | Actualiza un cliente |
| DELETE | `/clientes/eliminar/{clienteId}` | Elimina un cliente |
| POST | `/cuentas` | Crea una cuenta |
| GET | `/cuentas/{cuentaId}` | Busca una cuenta por id |
| PUT | `/cuentas/{cuentaId}` | Actualiza una cuenta |
| DELETE | `/cuentas/{cuentaId}` | Elimina una cuenta |
| GET | `/cuentas/cliente/{clienteId}` | Lista las cuentas de un cliente |
| POST | `/movimientos` | Crea un movimiento (depósito o retiro, según el signo del valor) |
| GET | `/movimientos/{movimientoId}` | Busca un movimiento por id |
| PUT | `/movimientos/{movimientoId}` | Actualiza un movimiento |
| DELETE | `/movimientos/{movimientoId}` | Elimina un movimiento |
| GET | `/movimientos/cuenta/{cuentaId}` | Lista los movimientos de una cuenta |
| GET | `/movimientos/reportes?identificacion=&desde=&hasta=` | Reporte de movimientos de un cliente por rango de fechas (máx. 30 días, `desde`/`hasta` en formato `yyyy-MM-dd`) |

## Cómo levantar el proyecto

### Con Docker/Podman Compose

```bash
docker compose up --build
```

Esto levanta dos contenedores:
- **`db`**: PostgreSQL 16, construido con una imagen propia (`scripts-db/Dockerfile`) que ya trae precargados el esquema y datos de ejemplo (`scripts-db/BaseDatos.sql`) — no requiere ningún paso manual adicional.
- **`api-transaccional`**: la API, compilada y ejecutada sobre GraalVM (`ghcr.io/graalvm/jdk-community:17`), expuesta en `http://localhost:9999`.

Si ya existe un volumen de datos previo de otra corrida, hay que resetearlo para que el seed se vuelva a aplicar: `docker compose down -v`.


## Colección de Postman

`api-transaccional-collection.json` contiene peticiones por cada endpoint (crear/buscar/actualizar/eliminar de Cliente, Cuenta y Movimiento, más el reporte de movimientos), organizadas en carpetas por agregado. Los `Content-Type` y bodies ya reflejan la forma actual de los DTOs (booleans para `estado`, códigos numéricos para `tipoCuenta`, etc.), y usan los datos de ejemplo que trae precargados el contenedor de la base (cédula `1234567891`, cuenta `478758`, etc.).

la raíz es:
`http://localhost:9999/api-transaccional/`