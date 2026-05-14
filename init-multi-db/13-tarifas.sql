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

CREATE TABLE proj_tipo_habitacion (
    codigo         VARCHAR(40)  PRIMARY KEY,
    descripcion    VARCHAR(100),
    capacidad_max  SMALLINT     NOT NULL,
    actualizado_en TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE proj_tipo_habitacion IS 'Réplica mínima de tipos de habitación recibida vía Kafka. Solo lectura.';

-- ------------------------------------------------------------
-- 3. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Temporadas tarifarias del año
CREATE TABLE temporada (
    id            SERIAL       PRIMARY KEY,
    codigo        VARCHAR(30)  NOT NULL UNIQUE,             -- ej: ALTA-2024, BAJA-2024, FIESTAS-2024
    nombre        VARCHAR(80)  NOT NULL,
    fecha_inicio  DATE         NOT NULL,
    fecha_fin     DATE         NOT NULL,
    CONSTRAINT chk_temporada_fechas CHECK (fecha_fin >= fecha_inicio)
);
COMMENT ON TABLE temporada IS 'Periodos tarifarios (alta, baja, fiestas, etc.). Publicado en Kafka topic: tarifa.events';
CREATE INDEX idx_temp_fechas ON temporada(fecha_inicio, fecha_fin);

-- Tarifa por tipo de habitación y temporada
CREATE TABLE tarifa (
    id                  SERIAL        PRIMARY KEY,
    codigo_temporada    VARCHAR(30)   NOT NULL
        REFERENCES temporada(codigo) ON UPDATE CASCADE,
    tipo_habitacion     VARCHAR(40)   NOT NULL
        REFERENCES proj_tipo_habitacion(codigo) ON UPDATE CASCADE,
    precio_noche_usd    NUMERIC(10,2) NOT NULL CHECK (precio_noche_usd > 0),
    incluye_desayuno    BOOLEAN       NOT NULL DEFAULT FALSE,
    activa              BOOLEAN       NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_tarifa UNIQUE (codigo_temporada, tipo_habitacion)
);
COMMENT ON TABLE tarifa IS 'Precio por noche según temporada y tipo de habitación.';
CREATE INDEX idx_tarifa_temporada ON tarifa(codigo_temporada);
CREATE INDEX idx_tarifa_tipo      ON tarifa(tipo_habitacion);

-- Descuentos aplicables sobre tarifas
CREATE TABLE descuento (
    id               SERIAL        PRIMARY KEY,
    codigo_descuento VARCHAR(30)   NOT NULL UNIQUE,         -- ej: CORP-10, FIDELIDAD-15
    descripcion      VARCHAR(100),
    porcentaje       NUMERIC(5,2)  NOT NULL
        CHECK (porcentaje > 0 AND porcentaje <= 100),
    aplica_a         VARCHAR(40),                           -- NULL = aplica a todos los tipos
    valido_desde     DATE          NOT NULL,
    valido_hasta     DATE          NOT NULL,
    activo           BOOLEAN       NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_desc_fechas CHECK (valido_hasta >= valido_desde)
);
COMMENT ON TABLE descuento IS 'Descuentos aplicables. El microservicio de reservas los consulta vía API.';
CREATE INDEX idx_desc_fechas ON descuento(valido_desde, valido_hasta);

-- ------------------------------------------------------------
-- 4. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO proj_tipo_habitacion (codigo, descripcion, capacidad_max) VALUES
    ('SIMPLE',   'Habitación individual',    2),
    ('DOBLE',    'Habitación doble',          4),
    ('SUITE',    'Suite ejecutiva',           2),
    ('FAMILIAR', 'Habitación familiar',       6);

INSERT INTO temporada (codigo, nombre, fecha_inicio, fecha_fin) VALUES
    ('BAJA-2024',    'Temporada baja 2024',           '2024-03-01', '2024-06-14'),
    ('ALTA-2024',    'Temporada alta verano 2024',    '2024-06-15', '2024-08-31'),
    ('FIESTAS-2024', 'Fiestas patrias 2024',          '2024-09-15', '2024-09-20'),
    ('BORDE-1DIA',   'Temporada de un solo día',      '2024-10-01', '2024-10-01');  -- caso borde: 1 día

INSERT INTO tarifa (codigo_temporada, tipo_habitacion, precio_noche_usd, incluye_desayuno, activa) VALUES
    ('BAJA-2024', 'SIMPLE',    70.00, FALSE, TRUE),
    ('BAJA-2024', 'DOBLE',    100.00, FALSE, TRUE),
    ('BAJA-2024', 'SUITE',    200.00,  TRUE, TRUE),
    ('ALTA-2024', 'SIMPLE',    95.00, FALSE, TRUE),
    ('ALTA-2024', 'DOBLE',    140.00,  TRUE, TRUE),
    ('ALTA-2024', 'SUITE',    300.00,  TRUE, TRUE),
    ('ALTA-2024', 'FAMILIAR', 170.00,  TRUE, TRUE),
    ('FIESTAS-2024','SIMPLE', 120.00,  TRUE, TRUE),
    ('FIESTAS-2024','SUITE',  400.00,  TRUE, TRUE),
    ('BORDE-1DIA', 'SIMPLE',    1.00, FALSE, FALSE);  -- caso borde: tarifa inactiva de 1 día

INSERT INTO descuento (codigo_descuento, descripcion, porcentaje, aplica_a, valido_desde, valido_hasta, activo) VALUES
    ('CORP-10',      'Descuento corporativo',          10.00, NULL,      '2024-01-01', '2024-12-31', TRUE),
    ('FIDELIDAD-15', 'Huésped frecuente',              15.00, NULL,      '2024-01-01', '2024-12-31', TRUE),
    ('SUITE-5',      'Promo suite temporada baja',      5.00, 'SUITE',   '2024-03-01', '2024-06-14', TRUE),
    ('VENCIDO',      'Descuento caducado',             20.00, NULL,      '2023-01-01', '2023-12-31', FALSE), -- caso borde: vencido
    ('MAXIMO-100',   'Descuento borde 100%',          100.00, 'SIMPLE',  '2024-06-01', '2024-06-30', FALSE); -- caso borde: 100%
