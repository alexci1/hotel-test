-- ============================================================
-- 11-reportes.sql
-- Microservicio: Reportes
-- Base de datos: reportes
-- Tablas maestras : reporte, metrica, kpi
-- Tablas proyección: ninguna
-- ============================================================

\c reportes

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS kpi CASCADE;
DROP TABLE IF EXISTS metrica CASCADE;
DROP TABLE IF EXISTS reporte CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Definición de reportes del sistema
CREATE TABLE reporte (
    id          SERIAL       PRIMARY KEY,
    codigo      VARCHAR(50)  NOT NULL UNIQUE,
    nombre      VARCHAR(120) NOT NULL,
    descripcion TEXT,
    tipo        VARCHAR(30)  NOT NULL
        CHECK (tipo IN ('OPERACIONAL','FINANCIERO','HOUSEKEEPING','RESTAURANTE','EJECUTIVO')),
    frecuencia  VARCHAR(20)  NOT NULL DEFAULT 'DIARIO'
        CHECK (frecuencia IN ('TIEMPO_REAL','DIARIO','SEMANAL','MENSUAL','ANUAL')),
    activo      BOOLEAN      NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE reporte IS 'Catálogo de reportes disponibles en el sistema.';
CREATE INDEX idx_reporte_tipo ON reporte(tipo);

-- Métricas agregadas calculadas a partir de eventos Kafka
CREATE TABLE metrica (
    id             SERIAL        PRIMARY KEY,
    codigo_reporte VARCHAR(50)   NOT NULL
        REFERENCES reporte(codigo) ON UPDATE CASCADE,
    periodo        DATE          NOT NULL,
    nombre_metrica VARCHAR(80)   NOT NULL,
    valor          NUMERIC(15,4) NOT NULL,
    unidad         VARCHAR(30),
    calculado_en   DATE          NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT uq_metrica UNIQUE (codigo_reporte, periodo, nombre_metrica)
);
COMMENT ON TABLE metrica IS 'Valores de métricas por período. Insertados por consumidores Kafka.';
CREATE INDEX idx_metrica_reporte ON metrica(codigo_reporte);
CREATE INDEX idx_metrica_periodo ON metrica(periodo);

-- KPIs consolidados del hotel
CREATE TABLE kpi (
    id             SERIAL        PRIMARY KEY,
    nombre         VARCHAR(80)   NOT NULL UNIQUE,
    descripcion    TEXT,
    valor_actual   NUMERIC(15,4),
    valor_objetivo NUMERIC(15,4),
    unidad         VARCHAR(30),
    periodo        VARCHAR(20)   NOT NULL DEFAULT 'MENSUAL'
        CHECK (periodo IN ('DIARIO','SEMANAL','MENSUAL','ANUAL')),
    actualizado_en DATE          NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE kpi IS 'Indicadores clave de rendimiento del hotel. Actualizados por workers Kafka.';

-- ------------------------------------------------------------
-- 3. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO reporte (codigo, nombre, descripcion, tipo, frecuencia) VALUES
    ('OCUPACION_DIARIA',   'Ocupación diaria',           'Porcentaje de habitaciones ocupadas por día', 'OPERACIONAL',  'DIARIO'),
    ('INGRESOS_DIARIOS',   'Ingresos diarios',           'Total de ingresos por pagos del día',         'FINANCIERO',   'DIARIO'),
    ('RENDIMIENTO_HK',     'Rendimiento Housekeeping',   'Tareas completadas vs programadas',           'HOUSEKEEPING', 'DIARIO'),
    ('VENTAS_RESTAURANTE', 'Ventas restaurante',         'Ingresos y pedidos del restaurante',          'RESTAURANTE',  'DIARIO'),
    ('EJECUTIVO_MENSUAL',  'Resumen ejecutivo mensual',  'KPIs consolidados para gerencia',             'EJECUTIVO',    'MENSUAL'),
    ('BORDE_TIEMPO_REAL',  'Monitor tiempo real',        'Métricas en tiempo real',                     'OPERACIONAL',  'TIEMPO_REAL');

INSERT INTO metrica (codigo_reporte, periodo, nombre_metrica, valor, unidad) VALUES
    ('OCUPACION_DIARIA', '2024-06-01', 'habitaciones_ocupadas',  4.0000,  'UNIDADES'),
    ('OCUPACION_DIARIA', '2024-06-01', 'habitaciones_total',     7.0000,  'UNIDADES'),
    ('OCUPACION_DIARIA', '2024-06-01', 'porcentaje_ocupacion',   57.1429, 'PORCENTAJE'),
    ('INGRESOS_DIARIOS', '2024-06-01', 'ingresos_habitaciones',  380.00,  'USD'),
    ('INGRESOS_DIARIOS', '2024-06-01', 'ingresos_restaurante',   58.50,   'USD'),
    ('INGRESOS_DIARIOS', '2024-06-01', 'ingresos_total',         438.50,  'USD'),
    ('RENDIMIENTO_HK',   '2024-06-05', 'tareas_programadas',     5.0000,  'UNIDADES'),
    ('RENDIMIENTO_HK',   '2024-06-05', 'tareas_completadas',     3.0000,  'UNIDADES'),
    ('RENDIMIENTO_HK',   '2024-06-05', 'tasa_completitud',       60.000,  'PORCENTAJE'),
    ('OCUPACION_DIARIA', '2024-06-01', 'duracion_media_estancia', 4.2000, 'NOCHES'),
    ('INGRESOS_DIARIOS', '2024-07-01', 'ingresos_total',         90.00,   'USD');

INSERT INTO kpi (nombre, descripcion, valor_actual, valor_objetivo, unidad, periodo) VALUES
    ('OCUPACION_PROMEDIO',  'Porcentaje promedio de ocupación mensual', 57.14, 80.00, 'PORCENTAJE', 'MENSUAL'),
    ('ADR',                 'Average Daily Rate: ingreso promedio por noche', 95.00, 110.00, 'USD', 'MENSUAL'),
    ('REVPAR',              'Revenue per Available Room', 54.28, 88.00, 'USD', 'MENSUAL'),
    ('NPS',                 'Net Promoter Score de huéspedes', 72.00, 80.00, 'PUNTOS', 'MENSUAL'),
    ('TASA_COMPLETITUD_HK', 'Tasa de completitud de tareas housekeeping', 60.00, 95.00, 'PORCENTAJE', 'DIARIO'),
    ('KPI_SIN_OBJETIVO',    'KPI sin meta definida', 42.00, NULL, 'UNIDADES', 'ANUAL');