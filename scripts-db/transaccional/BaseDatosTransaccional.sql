-- Tabla de referencia local de Clientes en api-transaccional (proyección alimentada por
-- los eventos de Kafka publicados por api-personas; no tiene FK hacia una tabla "clientes"
-- porque esa tabla vive en la base de datos de api-personas, no en esta).
CREATE TABLE public.clientes_referencia (
	cliente_id int8 NOT NULL,
	identificacion varchar(10) NULL,
	nombre varchar(255) NULL,
	estado boolean NOT NULL,
	CONSTRAINT clientes_referencia_pkey PRIMARY KEY (cliente_id)
);

-- Tabla Cuentas
CREATE TABLE public.cuentas (
	cuenta_id int8 NOT NULL,
	estado boolean NOT NULL,
	fecha_actualizacion timestamp(6) NULL,
	fecha_creacion timestamp(6) NULL,
	numero_cuenta varchar(255) NOT NULL,
	saldo_inicial numeric(38, 2) NULL,
	tipo_cuenta varchar(255) NULL,
	fk_cliente_id int8 NULL,
	CONSTRAINT cuentas_pkey PRIMARY KEY (cuenta_id),
	CONSTRAINT cuentas_tipo_cuenta_check CHECK (tipo_cuenta IN ('AHORROS','CORRIENTE')),
	CONSTRAINT uk_cuentas_numero_cuenta UNIQUE (numero_cuenta)
);

-- Tabla de movimientos
CREATE TABLE public.movimientos (
	movimiento_id int8 NOT NULL,
	fecha_actualizacion timestamp(6) NULL,
	fecha_creacion timestamp(6) NULL,
	fecha_movimiento timestamp(6) NULL,
	saldo_disponible numeric(38, 2) NULL,
	tipo_movimiento varchar(255) NULL,
	valor numeric(38, 2) NULL,
	fk_cuenta_id int8 NULL,
	CONSTRAINT movimientos_pkey PRIMARY KEY (movimiento_id),
	CONSTRAINT movimientos_tipo_movimiento_check CHECK (tipo_movimiento IN ('DEPOSITO','RETIRO'))
);

-- fk movimiento-cuenta
ALTER TABLE public.movimientos ADD CONSTRAINT fk_movimientos_cuenta FOREIGN KEY (fk_cuenta_id) REFERENCES public.cuentas(cuenta_id);

-- Creacion de la copia local de clientes en api-transaccional (normalmente la alimentan
-- los eventos de Kafka, pero se siembra aquí para que las cuentas de ejemplo validen)
INSERT INTO public.clientes_referencia (cliente_id,identificacion,nombre,estado) VALUES
	 (1,'1234567891','Jose Lema',true),
	 (2,'1234567892','Marianela Montalvo',true),
	 (3,'1234567893','Juan Osorio',true);

-- Creacion de cuentas
INSERT INTO public.cuentas (cuenta_id,estado,fecha_actualizacion,fecha_creacion,numero_cuenta,saldo_inicial,tipo_cuenta,fk_cliente_id) VALUES
	 (1,true,'2026-07-06 03:44:16.440271','2026-07-06 03:44:16.440271','478758',2000.00,'AHORROS',1),
	 (2,true,'2026-07-06 03:44:35.809551','2026-07-06 03:44:35.809551','585545',1000.00,'CORRIENTE',1),
	 (3,true,'2026-07-06 05:43:42.396312','2026-07-06 05:43:42.396312','495878',0.00,'AHORROS',3),
	 (4,true,'2026-07-06 05:44:15.068228','2026-07-06 05:44:15.068228','496825',540.00,'AHORROS',2),
	 (5,true,'2026-07-06 05:43:12.126364','2026-07-06 05:43:12.126364','225487',100.00,'CORRIENTE',2);

-- Creacion de movimientos
INSERT INTO public.movimientos (movimiento_id,fecha_actualizacion,fecha_creacion,fecha_movimiento,saldo_disponible,tipo_movimiento,valor,fk_cuenta_id) VALUES
	 (1,'2026-07-06 05:48:42.922123','2026-07-06 05:48:42.922123','2026-07-06 05:48:42.918863',1425.00,'RETIRO',-575.00,1),
	 (2,'2026-07-06 05:48:59.134807','2026-07-06 05:48:59.134807','2026-07-06 05:48:59.131692',700.00,'DEPOSITO',600.00,5),
	 (3,'2026-07-06 05:49:13.965517','2026-07-06 05:49:13.965517','2026-07-06 05:49:13.961858',150.00,'DEPOSITO',150.00,3),
	 (4,'2026-07-06 05:49:28.737092','2026-07-06 05:49:28.737092','2026-07-06 05:49:28.734026',0.00,'RETIRO',-540.00,4);

-- Secuencias: Hibernate (GenerationType.AUTO sobre PostgreSQL) genera por defecto
-- una secuencia por tabla llamada "<tabla>_seq". Arrancan justo después del último id
-- sembrado arriba, ya que esos inserts no pasan por la secuencia.
CREATE SEQUENCE IF NOT EXISTS public.cuentas_seq START WITH 6;
CREATE SEQUENCE IF NOT EXISTS public.movimientos_seq START WITH 5;
