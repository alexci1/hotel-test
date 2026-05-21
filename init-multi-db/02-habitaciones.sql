-- ============================================================
-- 02-habitaciones.sql
-- Microservicio: Habitaciones
-- Base de datos: habitaciones
-- Tablas maestras : tipo_habitacion, habitacion, estado_habitacion
-- Tablas proyección (recibidas vía Kafka):
--   · proj_tarifa  ← publicada por microservicio tarifas
-- ============================================================

\c habitaciones

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS estado_habitacion CASCADE;
DROP TABLE IF EXISTS habitacion         CASCADE;
DROP TABLE IF EXISTS tipo_habitacion    CASCADE;
DROP TABLE IF EXISTS proj_tarifa        CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Proyección de tarifas base por tipo de habitación
-- ============================================================
-- TABLA: PROYECCION TARIFA
-- ============================================================

CREATE TABLE proj_tarifa (
    tipo_habitacion   VARCHAR(40) PRIMARY KEY,
    precio_base_usd   NUMERIC(10,2) NOT NULL
        CHECK (precio_base_usd > 0),
    actualizado_en    DATE NOT NULL DEFAULT CURRENT_DATE
);

-- ============================================================
-- TABLA: TIPO HABITACION
-- ============================================================

CREATE TABLE tipo_habitacion (
    id                SERIAL PRIMARY KEY,

    codigo            VARCHAR(40) NOT NULL UNIQUE,

    descripcion       VARCHAR(200),

    capacidad_max     INTEGER NOT NULL DEFAULT 2
        CHECK (capacidad_max BETWEEN 1 AND 10),

    activo            BOOLEAN NOT NULL DEFAULT TRUE
);

-- ============================================================
-- TABLA: HABITACION
-- ============================================================

CREATE TABLE habitacion (
    id                    SERIAL PRIMARY KEY,

    numero_habitacion     VARCHAR(10) NOT NULL UNIQUE,

    piso                  INTEGER NOT NULL
        CHECK (piso >= 0),

    codigo_tipo           VARCHAR(40) NOT NULL,

    activa                BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_habitacion_tipo
    FOREIGN KEY (codigo_tipo)
    REFERENCES tipo_habitacion(codigo)
    ON UPDATE CASCADE
);

-- ============================================================
-- TABLA: ESTADO HABITACION
-- ============================================================

CREATE TABLE estado_habitacion (
    id                    SERIAL PRIMARY KEY,

    numero_habitacion     VARCHAR(10) NOT NULL UNIQUE,

    estado                VARCHAR(30) NOT NULL DEFAULT 'LIMPIA'
        CHECK (
            estado IN (
                'LIMPIA',
                'SUCIA',
                'EN_MANTENIMIENTO',
                'OCUPADA',
                'BLOQUEADA'
            )
        ),

    observacion           VARCHAR(200),

    actualizado_en        DATE NOT NULL DEFAULT CURRENT_DATE,

    CONSTRAINT fk_estado_habitacion
    FOREIGN KEY (numero_habitacion)
    REFERENCES habitacion(numero_habitacion)
    ON UPDATE CASCADE
);

-- ============================================================
-- INSERTS: PROJ_TARIFA
-- ============================================================

INSERT INTO proj_tarifa
(tipo_habitacion, precio_base_usd)
VALUES

('SIMPLE', 80.00),

('DOBLE', 120.00),

('SUITE', 250.00);

-- ============================================================
-- INSERTS: TIPO_HABITACION
-- ============================================================

INSERT INTO tipo_habitacion
(codigo, descripcion, capacidad_max, activo)
VALUES

('SIMPLE',
'Habitacion individual con cama matrimonial',
2,
TRUE),

('DOBLE',
'Habitacion con dos camas individuales',
4,
TRUE),

('SUITE',
'Suite ejecutiva con sala de estar y jacuzzi',
2,
TRUE),

('FAMILIAR',
'Habitacion amplia con litera y cama matrimonial',
6,
TRUE),

('BORDE',
'Tipo sin precio asignado prueba borde',
1,
FALSE);

-- ============================================================
-- INSERTS: HABITACION
-- ============================================================

INSERT INTO habitacion
(numero_habitacion, piso, codigo_tipo, activa)
VALUES

('101',
1,
'SIMPLE',
TRUE),

('102',
1,
'SIMPLE',
TRUE),

('201',
2,
'DOBLE',
TRUE),

('202',
2,
'DOBLE',
TRUE),

('303',
3,
'SUITE',
TRUE),

('404',
4,
'SIMPLE',
FALSE),

('PH1',
5,
'SUITE',
TRUE);

-- ============================================================
-- INSERTS: ESTADO_HABITACION
-- ============================================================

INSERT INTO estado_habitacion
(numero_habitacion, estado, observacion)
VALUES

('101',
'LIMPIA',
NULL),

('102',
'SUCIA',
'Pendiente limpieza post checkout'),

('201',
'OCUPADA',
NULL),

('202',
'EN_MANTENIMIENTO',
'Fuga en bano, plomero programado'),

('303',
'LIMPIA',
NULL),

('404',
'BLOQUEADA',
'Habitacion fuera de servicio indefinido'),

('PH1',
'LIMPIA',
NULL);
