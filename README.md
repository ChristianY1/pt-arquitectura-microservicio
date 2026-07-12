# PT Arquitectura Microservicio

Microservicios api-personas (Gestion de Clientes) y api-transaccional(Transaccionabilidad) con arquitectura hexagonal comunicados asincronamente usando Kafka

- **`api-personas`**: `Persona`  `Cliente`.
- **`api-transaccional`**: `Cuenta` `Movimiento`.


## Dominio

**API-Personas**
- Persona
- Cliente 

**API-Transacciones**
- Cuenta
- Movimiento

## Cómo se comunican los dos servicios

1. Cuando se crea o actualiza un `Cliente` en `api-personas`, se publica un evento (`clienteId`, `identificacion`, `nombre`, `estado`) al tópico Kafka **`clientes-eventos`**.
2. `api-transaccional` consume ese tópico y guarda una copia local en su propia tabla `clientes_referencia` (solo lo que necesita: id, cédula, nombre y estado — nunca usuario/contraseña).
3. Cuando se crea una `Cuenta`, `CuentaService` valida que el cliente exista y esté activo consultando **esa copia local**, no llamando en vivo a `api-personas`.


`api-transaccional` tiene su **propia clase `Cliente`** (dominio local, solo `clienteId`/`identificacion`/`nombre`/`estado`) — no importa ni depende de la clase `Cliente` de `api-personas` (son módulos Maven separados, sin librería compartida). Son dos clases con el mismo nombre y propósitos distintos, a propósito.

## Bases de datos

Cada servicio tiene **su propia base de datos Postgres**, sin relaciones (FK) entre ellas:

- **`db-personas`**: tablas `personas`, `clientes`.
- **`db-transaccional`**: tablas `clientes_referencia` (la copia local descrita arriba), `cuentas`, `movimientos`.


## Stack técnico

- Java 17, Spring Boot 4.1.0 (Jackson 3 por defecto — ver nota abajo)
- Spring Data JPA + PostgreSQL (una instancia por servicio)
- Spring Kafka (`spring-kafka`) — productor en `api-personas`, consumidor en `api-transaccional`
- Lombok
- Maven (con wrapper `mvnw`)
- Docker Compose para levantar el entorno completo


## Reglas de negocio principales

**`api-personas`**
- La identificación (cédula) debe tener exactamente 10 caracteres y ser única por persona.
- El usuario del cliente debe ser único.

**`api-transaccional`**
- El número de cuenta debe ser único y obligatorio.
- Solo se puede crear una cuenta para un cliente que exista y tenga `estado = true` (validado contra `clientes_referencia`, la copia local).
- Solo se puede registrar un movimiento sobre una cuenta con `estado = true`.
- Un movimiento no puede dejar el saldo disponible en negativo.
- El reporte de movimientos no admite un rango de fechas mayor a 30 días.



## Ejecución del proyecto

```bash
docker compose up --build
```

## Colecciones de Postman

Hay una colección por servicio:
- `api-personas.postman_collection.json`: CRUD de Cliente (`http://localhost:9998/api-personas/...`).
- `api-transaccional.postman_collection.json`: CRUD de Cuenta y Movimiento (`http://localhost:9999/api-transaccional/...`).


## Implementacion de Pipelines en AzureDevos
Se crea el archivo azure-pipelines.yml en donde estan los pasos que siguen los pipelines para la 
compilacion y ejecucion del proyecto
