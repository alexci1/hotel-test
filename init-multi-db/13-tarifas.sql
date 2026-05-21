-- ============================================================
-- 10-tarifas.sql
-- Microservicio: Tarifas
-- Base de datos: tarifas
-- Tablas maestras : temporada, tarifa, descuento
-- Tablas proyección (recibidas vía Kafka):
--   · proj_tipo_habitacion ← publicada por microservicio habitaciones
-- ============================================================


\c tarifas

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------

DROP TABLE IF EXISTS descuento           CASCADE;
DROP TABLE IF EXISTS tarifa              CASCADE;
DROP TABLE IF EXISTS temporada           CASCADE;
DROP TABLE IF EXISTS proj_tipo_habitacion CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS DE PROYECCIÓN
-- ------------------------------------------------------------

-- ============================================================
-- MICROSERVICIO: TARIFAS
-- ============================================================

DROP TABLE IF EXISTS descuento CASCADE;
DROP TABLE IF EXISTS tarifa CASCADE;
DROP TABLE IF EXISTS temporada CASCADE;
DROP TABLE IF EXISTS proj_tipo_habitacion CASCADE;

-- ============================================================
-- TABLA: PROYECCION TIPO HABITACION
-- ============================================================

CREATE TABLE proj_tipo_habitacion (
    codigo            VARCHAR(40) PRIMARY KEY,
    descripcion       VARCHAR(100),
    capacidad_max     INTEGER NOT NULL,
    actualizado_en    DATE NOT NULL DEFAULT CURRENT_DATE
);

-- ============================================================
-- TABLA: TEMPORADA
-- ============================================================

CREATE TABLE temporada (
    id               SERIAL PRIMARY KEY,
    codigo           VARCHAR(30) NOT NULL UNIQUE,
    nombre           VARCHAR(80) NOT NULL,
    fecha_inicio     DATE NOT NULL,
    fecha_fin        DATE NOT NULL,

    CONSTRAINT chk_temporada_fechas
    CHECK (fecha_fin >= fecha_inicio)
);

-- ============================================================
-- TABLA: TARIFA
-- ============================================================

CREATE TABLE tarifa (
    id                    SERIAL PRIMARY KEY,

    codigo_temporada      VARCHAR(30) NOT NULL,
    tipo_habitacion       VARCHAR(40) NOT NULL,

    precio_noche_usd      NUMERIC(10,2) NOT NULL,
    incluye_desayuno      BOOLEAN NOT NULL DEFAULT FALSE,
    activa                BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_tarifa_temporada
    FOREIGN KEY (codigo_temporada)
    REFERENCES temporada(codigo)
    ON UPDATE CASCADE,

    CONSTRAINT fk_tarifa_tipo_habitacion
    FOREIGN KEY (tipo_habitacion)
    REFERENCES proj_tipo_habitacion(codigo)
    ON UPDATE CASCADE,

    CONSTRAINT uq_tarifa
    UNIQUE (codigo_temporada, tipo_habitacion),

    CONSTRAINT chk_precio
    CHECK (precio_noche_usd > 0)
);

-- ============================================================
-- TABLA: DESCUENTO
-- ============================================================

CREATE TABLE descuento (
    id                    SERIAL PRIMARY KEY,

    codigo_descuento      VARCHAR(30) NOT NULL UNIQUE,
    descripcion           VARCHAR(100),

    porcentaje            NUMERIC(5,2) NOT NULL,
    aplica_a              VARCHAR(40),

    valido_desde          DATE NOT NULL,
    valido_hasta          DATE NOT NULL,

    activo                BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_porcentaje
    CHECK (porcentaje > 0 AND porcentaje <= 100),

    CONSTRAINT chk_desc_fechas
    CHECK (valido_hasta >= valido_desde)
);

-- ============================================================
-- INSERTS: PROJ_TIPO_HABITACION
-- ============================================================

INSERT INTO proj_tipo_habitacion
(codigo, descripcion, capacidad_max)
VALUES
('SIMPLE', 'Habitacion individual', 2),

('DOBLE', 'Habitacion doble', 4),

('SUITE', 'Suite ejecutiva', 2),

('FAMILIAR', 'Habitacion familiar', 6);

-- ============================================================
-- INSERTS: TEMPORADA
-- ============================================================

INSERT INTO temporada
(codigo, nombre, fecha_inicio, fecha_fin)
VALUES
('BAJA-2024',
'Temporada baja 2024',
'2024-03-01',
'2024-06-14'),

('ALTA-2024',
'Temporada alta verano 2024',
'2024-06-15',
'2024-08-31'),

('FIESTAS-2024',
'Fiestas patrias 2024',
'2024-09-15',
'2024-09-20'),

('BORDE-1DIA',
'Temporada de un solo dia',
'2024-10-01',
'2024-10-01');

-- ============================================================
-- INSERTS: TARIFA
-- ============================================================

INSERT INTO tarifa
(codigo_temporada,
tipo_habitacion,
precio_noche_usd,
incluye_desayuno,
activa)
VALUES

('BAJA-2024',
'SIMPLE',
70.00,
FALSE,
TRUE),

('BAJA-2024',
'DOBLE',
100.00,
FALSE,
TRUE),

('BAJA-2024',
'SUITE',
200.00,
TRUE,
TRUE),

('ALTA-2024',
'SIMPLE',
95.00,
FALSE,
TRUE),

('ALTA-2024',
'DOBLE',
140.00,
TRUE,
TRUE),

('ALTA-2024',
'SUITE',
300.00,
TRUE,
TRUE),

('ALTA-2024',
'FAMILIAR',
170.00,
TRUE,
TRUE),

('FIESTAS-2024',
'SIMPLE',
120.00,
TRUE,
TRUE),

('FIESTAS-2024',
'SUITE',
400.00,
TRUE,
TRUE),

('BORDE-1DIA',
'SIMPLE',
1.00,
FALSE,
FALSE);

-- ============================================================
-- INSERTS: DESCUENTO
-- ============================================================

INSERT INTO descuento
(codigo_descuento,
descripcion,
porcentaje,
aplica_a,
valido_desde,
valido_hasta,
activo)
VALUES

('CORP-10',
'Descuento corporativo',
10.00,
NULL,
'2024-01-01',
'2024-12-31',
TRUE),

('FIDELIDAD-15',
'Huesped frecuente',
15.00,
NULL,
'2024-01-01',
'2024-12-31',
TRUE),

('SUITE-5',
'Promo suite temporada baja',
5.00,
'SUITE',
'2024-03-01',
'2024-06-14',
TRUE),

('VENCIDO',
'Descuento caducado',
20.00,
NULL,
'2023-01-01',
'2023-12-31',
FALSE),

('MAXIMO-100',
'Descuento borde 100%',
100.00,
'SIMPLE',
'2024-06-01',
'2024-06-30',
FALSE);