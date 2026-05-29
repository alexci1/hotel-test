/* ============================================================
   ARCHIVO: 11-reportes.sql
   Microservicio: reportes
   Responsabilidad: administrar reportes, metricas y kpis.
   ============================================================ */

\c reportes;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS kpis CASCADE;
DROP TABLE IF EXISTS metricas CASCADE;
DROP TABLE IF EXISTS reportes CASCADE;

-- 2. TABLAS MAESTRAS

CREATE TABLE reportes (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255),
    tipo VARCHAR(30) NOT NULL
        CHECK (tipo IN ('OPERACIONAL','FINANCIERO','HOUSEKEEPING','RESTAURANTE','EJECUTIVO')),
    frecuencia VARCHAR(20) NOT NULL DEFAULT 'DIARIO'
        CHECK (frecuencia IN ('TIEMPO_REAL','DIARIO','SEMANAL','MENSUAL','ANUAL')),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE metricas (
    id SERIAL PRIMARY KEY,
    codigo_reporte VARCHAR(50) NOT NULL REFERENCES reportes(codigo) ON UPDATE CASCADE,
    periodo DATE NOT NULL,
    nombre_metrica VARCHAR(80) NOT NULL,
    valor INTEGER NOT NULL,
    unidad VARCHAR(30),
    calculado_en DATE NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT uq_metricas UNIQUE (codigo_reporte, periodo, nombre_metrica)
);

CREATE TABLE kpis (
    id SERIAL PRIMARY KEY,
    codigo_reporte VARCHAR(50) NOT NULL REFERENCES reportes(codigo) ON UPDATE CASCADE,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    valor_actual INTEGER,
    valor_objetivo INTEGER,
    unidad VARCHAR(30),
    periodo VARCHAR(20) NOT NULL DEFAULT 'MENSUAL'
        CHECK (periodo IN ('DIARIO','SEMANAL','MENSUAL','ANUAL')),
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE INDEX idx_reportes_codigo ON reportes(codigo);
CREATE INDEX idx_reportes_tipo ON reportes(tipo);
CREATE INDEX idx_reportes_frecuencia ON reportes(frecuencia);
CREATE INDEX idx_reportes_activo ON reportes(activo);

CREATE INDEX idx_metricas_codigo_reporte ON metricas(codigo_reporte);
CREATE INDEX idx_metricas_periodo ON metricas(periodo);
CREATE INDEX idx_metricas_nombre_metrica ON metricas(nombre_metrica);

CREATE INDEX idx_kpis_codigo_reporte ON kpis(codigo_reporte);
CREATE INDEX idx_kpis_nombre ON kpis(nombre);
CREATE INDEX idx_kpis_periodo ON kpis(periodo);
CREATE INDEX idx_kpis_actualizado_en ON kpis(actualizado_en);

-- 3. DATOS DE PRUEBA

INSERT INTO reportes (codigo, nombre, descripcion, tipo, frecuencia) VALUES
('OCUPACION_DIARIA', 'Ocupacion diaria', 'Porcentaje de habitaciones ocupadas por dia', 'OPERACIONAL', 'DIARIO'),
('INGRESOS_DIARIOS', 'Ingresos diarios', 'Total de ingresos por pagos del dia', 'FINANCIERO', 'DIARIO'),
('RENDIMIENTO_HK', 'Rendimiento Housekeeping', 'Tareas completadas vs programadas', 'HOUSEKEEPING', 'DIARIO'),
('VENTAS_RESTAURANTE', 'Ventas restaurante', 'Ingresos y pedidos del restaurante', 'RESTAURANTE', 'DIARIO'),
('EJECUTIVO_MENSUAL', 'Resumen ejecutivo mensual', 'KPIs consolidados para gerencia', 'EJECUTIVO', 'MENSUAL'),
('BORDE_TIEMPO_REAL', 'Monitor tiempo real', 'Metricas en tiempo real', 'OPERACIONAL', 'TIEMPO_REAL');

INSERT INTO metricas (codigo_reporte, periodo, nombre_metrica, valor, unidad) VALUES
('OCUPACION_DIARIA', '2024-06-01', 'habitaciones_ocupadas', 4, 'UNIDADES'),
('OCUPACION_DIARIA', '2024-06-01', 'habitaciones_total', 7, 'UNIDADES'),
('OCUPACION_DIARIA', '2024-06-01', 'porcentaje_ocupacion', 57, 'PORCENTAJE'),
('INGRESOS_DIARIOS', '2024-06-01', 'ingresos_habitaciones', 380, 'USD'),
('INGRESOS_DIARIOS', '2024-06-01', 'ingresos_restaurante', 58, 'USD'),
('INGRESOS_DIARIOS', '2024-06-01', 'ingresos_total', 438, 'USD'),
('RENDIMIENTO_HK', '2024-06-05', 'tareas_programadas', 5, 'UNIDADES'),
('RENDIMIENTO_HK', '2024-06-05', 'tareas_completadas', 3, 'UNIDADES'),
('RENDIMIENTO_HK', '2024-06-05', 'tasa_completitud', 60, 'PORCENTAJE'),
('OCUPACION_DIARIA', '2024-06-01', 'duracion_media_estancia', 4, 'NOCHES'),
('INGRESOS_DIARIOS', '2024-07-01', 'ingresos_total', 90, 'USD');

INSERT INTO kpis (codigo_reporte, nombre, descripcion, valor_actual, valor_objetivo, unidad, periodo) VALUES
('EJECUTIVO_MENSUAL', 'OCUPACION_PROMEDIO', 'Porcentaje promedio de ocupacion mensual', 57, 80, 'PORCENTAJE', 'MENSUAL'),
('EJECUTIVO_MENSUAL', 'ADR', 'Average Daily Rate: ingreso promedio por noche', 95, 110, 'USD', 'MENSUAL'),
('EJECUTIVO_MENSUAL', 'REVPAR', 'Revenue per Available Room', 54, 88, 'USD', 'MENSUAL'),
('EJECUTIVO_MENSUAL', 'NPS', 'Net Promoter Score de huespedes', 72, 80, 'PUNTOS', 'MENSUAL'),
('RENDIMIENTO_HK', 'TASA_COMPLETITUD_HK', 'Tasa de completitud de tareas housekeeping', 60, 95, 'PORCENTAJE', 'DIARIO'),
('BORDE_TIEMPO_REAL', 'KPI_SIN_OBJETIVO', 'KPI sin meta definida', 42, NULL, 'UNIDADES', 'ANUAL');