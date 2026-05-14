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
CREATE TABLE proj_tarifa (
    tipo_habitacion  VARCHAR(40)    PRIMARY KEY,
    precio_base_usd  NUMERIC(10,2)  NOT NULL CHECK (precio_base_usd > 0),
    actualizado_en   TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE proj_tarifa IS 'Réplica simplificada de tarifas recibida vía Kafka. Solo lectura.';

-- Catálogo de tipos de habitación (SIMPLE, DOBLE, SUITE, etc.)
CREATE TABLE tipo_habitacion (
    id               SERIAL        PRIMARY KEY,
    codigo           VARCHAR(40)   NOT NULL UNIQUE,   -- clave de negocio
    descripcion      TEXT,
    capacidad_max    SMALLINT      NOT NULL DEFAULT 2 CHECK (capacidad_max BETWEEN 1 AND 10),
    activo           BOOLEAN       NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE tipo_habitacion IS 'Catálogo de tipos de habitación. Publicado en Kafka topic: habitacion.events';

-- Habitaciones físicas del hotel
CREATE TABLE habitacion (
    id                SERIAL       PRIMARY KEY,
    numero_habitacion VARCHAR(10)  NOT NULL UNIQUE,   -- clave de negocio (ej: "101", "PH1")
    piso              SMALLINT     NOT NULL CHECK (piso >= 0),
    codigo_tipo       VARCHAR(40)  NOT NULL
        REFERENCES tipo_habitacion(codigo) ON UPDATE CASCADE,
    activa            BOOLEAN      NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE habitacion IS 'Registro físico de cada habitación. numero_habitacion es la clave de negocio global.';
CREATE INDEX idx_hab_tipo  ON habitacion(codigo_tipo);
CREATE INDEX idx_hab_piso  ON habitacion(piso);

-- Estado operacional de la habitación (LIMPIA, SUCIA, EN_MANTENIMIENTO, OCUPADA)
CREATE TABLE estado_habitacion (
    id                SERIAL       PRIMARY KEY,
    numero_habitacion VARCHAR(10)  NOT NULL UNIQUE
        REFERENCES habitacion(numero_habitacion) ON UPDATE CASCADE,
    estado            VARCHAR(30)  NOT NULL DEFAULT 'LIMPIA'
        CHECK (estado IN ('LIMPIA','SUCIA','EN_MANTENIMIENTO','OCUPADA','BLOQUEADA')),
    observacion       TEXT,
    actualizado_en    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE estado_habitacion IS 'Estado operacional actual de cada habitación. Actualizado por housekeeping y checkin.';
CREATE INDEX idx_estado_hab ON estado_habitacion(estado);

-- ------------------------------------------------------------
-- 3. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO proj_tarifa (tipo_habitacion, precio_base_usd) VALUES
    ('SIMPLE',  80.00),
    ('DOBLE',  120.00),
    ('SUITE',  250.00);

INSERT INTO tipo_habitacion (codigo, descripcion, capacidad_max, activo) VALUES
    ('SIMPLE',  'Habitación individual con cama matrimonial',       2, TRUE),
    ('DOBLE',   'Habitación con dos camas individuales',            4, TRUE),
    ('SUITE',   'Suite ejecutiva con sala de estar y jacuzzi',      2, TRUE),
    ('FAMILIAR','Habitación amplia con litera y cama matrimonial',  6, TRUE),
    ('BORDE',   'Tipo sin precio asignado (prueba borde)',          1, FALSE);  -- caso borde: inactivo

INSERT INTO habitacion (numero_habitacion, piso, codigo_tipo, activa) VALUES
    ('101', 1, 'SIMPLE',   TRUE),
    ('102', 1, 'SIMPLE',   TRUE),
    ('201', 2, 'DOBLE',    TRUE),
    ('202', 2, 'DOBLE',    TRUE),
    ('303', 3, 'SUITE',    TRUE),
    ('404', 4, 'SIMPLE',   FALSE),   -- caso borde: habitación inactiva
    ('PH1', 5, 'SUITE',    TRUE);    -- caso borde: numeración no numérica

INSERT INTO estado_habitacion (numero_habitacion, estado, observacion) VALUES
    ('101', 'LIMPIA',          NULL),
    ('102', 'SUCIA',           'Pendiente limpieza post checkout'),
    ('201', 'OCUPADA',         NULL),
    ('202', 'EN_MANTENIMIENTO','Fuga en baño, plomero programado'),
    ('303', 'LIMPIA',          NULL),
    ('404', 'BLOQUEADA',       'Habitación fuera de servicio indefinido'),   -- caso borde
    ('PH1', 'LIMPIA',          NULL);
