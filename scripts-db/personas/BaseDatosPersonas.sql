-- Tabla Personas
CREATE TABLE public.personas (
	persona_id int8 NOT NULL,
	direccion varchar(255) NULL,
	edad int4 NOT NULL,
	fecha_actualizacion timestamp(6) NULL,
	fecha_creacion timestamp(6) NULL,
	genero varchar(255) NULL,
	identificacion varchar(10) NOT NULL,
	nombre varchar(255) NULL,
	telefono varchar(255) NULL,
	CONSTRAINT personas_pkey PRIMARY KEY (persona_id),
	CONSTRAINT personas_identificacion_key UNIQUE (identificacion)
);

-- Tabla Clientes
CREATE TABLE public.clientes (
	cliente_id int8 NOT NULL,
	contrasenia varchar(255) NULL,
	estado boolean NOT NULL,
	fecha_actualizacion timestamp(6) NULL,
	fecha_creacion timestamp(6) NULL,
	usuario varchar(255) NULL,
	fk_persona_id int8 NULL,
	CONSTRAINT clientes_pkey PRIMARY KEY (cliente_id),
	CONSTRAINT uk_clientes_fk_persona_id UNIQUE (fk_persona_id)
);

-- FK persona-cliente
ALTER TABLE public.clientes ADD CONSTRAINT fk_clientes_persona FOREIGN KEY (fk_persona_id) REFERENCES public.personas(persona_id);

-- Creacion de personas
INSERT INTO public.personas (persona_id,direccion,edad,fecha_actualizacion,fecha_creacion,genero,identificacion,nombre,telefono) VALUES
	 (1,'Otavalo sn y principal',30,'2026-07-06 03:43:44.901393','2026-07-06 03:43:44.901393','M','1234567891','Jose Lema','098254785'),
	 (3,'13 junio y Equinoccial',40,'2026-07-06 05:40:52.213044','2026-07-06 05:40:52.213044','M','1234567893','Juan Osorio','098874587'),
	 (2,'Amazonas y NNUU',30,'2026-07-06 05:40:03.92517','2026-07-06 05:40:03.92517','F','1234567892','Marianela Montalvo','097548965');

-- Creacion de clientes
INSERT INTO public.clientes (cliente_id,contrasenia,estado,fecha_actualizacion,fecha_creacion,usuario,fk_persona_id) VALUES
	 (1,'1234',true,'2026-07-06 03:43:44.899216','2026-07-06 03:43:44.899216','jlema',1),
	 (2,'5678',true,'2026-07-06 05:40:03.91752','2026-07-06 05:40:03.91752','mmontalvo',2),
	 (3,'1245',true,'2026-07-06 05:40:52.212847','2026-07-06 05:40:52.212847','josorio',3);

-- Secuencias: Hibernate (GenerationType.AUTO sobre PostgreSQL) genera por defecto
-- una secuencia por tabla llamada "<tabla>_seq". Arrancan justo después del último id
-- sembrado arriba, ya que esos inserts no pasan por la secuencia.
CREATE SEQUENCE IF NOT EXISTS public.personas_seq START WITH 4;
CREATE SEQUENCE IF NOT EXISTS public.clientes_seq START WITH 4;
