-- ============================================================
-- 06-housekeeping.sql
-- Microservicio: Housekeeping
-- Base de datos: housekeeping
-- Tablas maestras : tarea, asignacion, reporte
-- Tablas proyección (recibidas vía Kafka):
--   · proj_habitacion ← publicada por microservicio habitaciones
-- ============================================================

\c housekeeping

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS reporte       CASCADE;
DROP TABLE IF EXISTS asignacion    CASCADE;
DROP TABLE IF EXISTS tarea         CASCADE;
DROP TABLE IF EXISTS proj_habitacion CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS DE PROYECCIÓN
-- ------------------------------------------------------------

CREATE TABLE proj_habitacion (
    numero_habitacion VARCHAR(10)  PRIMARY KEY,
    tipo              VARCHAR(40)  NOT NULL,
    piso              SMALLINT     NOT NULL,
    actualizado_en    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE proj_habitacion IS 'Réplica mínima de habitaciones recibida vía Kafka. Solo lectura.';

-- ------------------------------------------------------------
-- 3. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Catálogo de tareas de housekeeping
CREATE TABLE tarea (
    id              SERIAL       PRIMARY KEY,
    codigo          VARCHAR(30)  NOT NULL UNIQUE,           -- clave de negocio: LIMPIEZA_COMPLETA, etc.
    descripcion     TEXT,
    duracion_min    SMALLINT     NOT NULL DEFAULT 30 CHECK (duracion_min > 0),
    activa          BOOLEAN      NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE tarea IS 'Catálogo de tipos de tarea de limpieza y mantenimiento.';

-- Asignación de tarea a habitación y camarero
CREATE TABLE asignacion (
    id                SERIAL       PRIMARY KEY,
    numero_habitacion VARCHAR(10)  NOT NULL
        REFERENCES proj_habitacion(numero_habitacion),
    codigo_tarea      VARCHAR(30)  NOT NULL
        REFERENCES tarea(codigo) ON UPDATE CASCADE,
    email_camarero    VARCHAR(120) NOT NULL,                -- empleado responsable
    fecha_programada  DATE         NOT NULL,
    estado            VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE'
        CHECK (estado IN ('PENDIENTE','EN_PROCESO','COMPLETADA','OMITIDA')),
    prioridad         SMALLINT     NOT NULL DEFAULT 3 CHECK (prioridad BETWEEN 1 AND 5),  -- 1=urgente, 5=baja
    iniciada_en       TIMESTAMPTZ,
    completada_en     TIMESTAMPTZ
);
COMMENT ON TABLE asignacion IS 'Asignación de tareas de housekeeping por habitación y fecha.';
CREATE INDEX idx_asig_habitacion ON asignacion(numero_habitacion);
CREATE INDEX idx_asig_camarero   ON asignacion(email_camarero);
CREATE INDEX idx_asig_fecha      ON asignacion(fecha_programada);
CREATE INDEX idx_asig_estado     ON asignacion(estado);

-- Reporte de inspección post-limpieza
CREATE TABLE reporte (
    id              SERIAL        PRIMARY KEY,
    asignacion_id   INTEGER       NOT NULL UNIQUE
        REFERENCES asignacion(id) ON DELETE CASCADE,
    aprobado        BOOLEAN       NOT NULL DEFAULT FALSE,
    observaciones   TEXT,
    inspector       VARCHAR(120)  NOT NULL,
    inspeccionado_en TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE reporte IS 'Reporte de inspección de calidad post-tarea. 1:1 con asignacion.';
CREATE INDEX idx_reporte_aprobado ON reporte(aprobado);

-- ------------------------------------------------------------
-- 4. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO proj_habitacion (numero_habitacion, tipo, piso) VALUES
    ('101', 'SIMPLE', 1),
    ('102', 'SIMPLE', 1),
    ('201', 'DOBLE',  2),
    ('202', 'DOBLE',  2),
    ('303', 'SUITE',  3),
    ('PH1', 'SUITE',  5);

INSERT INTO tarea (codigo, descripcion, duracion_min, activa) VALUES
    ('LIMPIEZA_COMPLETA',   'Limpieza profunda con cambio de ropa de cama y toallas', 60, TRUE),
    ('LIMPIEZA_RAPIDA',     'Tendido de cama, vaciado de basura y aseo de baño',      30, TRUE),
    ('MANTENIMIENTO',       'Revisión y reparación de equipos o instalaciones',        90, TRUE),
    ('INSPECCION',          'Revisión de habitación para ingreso de nuevo huésped',    15, TRUE),
    ('OBSOLETA',            'Tarea descontinuada',                                     30, FALSE); -- caso borde: inactiva

INSERT INTO asignacion
    (numero_habitacion, codigo_tarea, email_camarero, fecha_programada, estado, prioridad, iniciada_en, completada_en) VALUES
    ('101', 'LIMPIEZA_COMPLETA', 'maria.h@hotel.com', '2024-06-05', 'COMPLETADA', 2,
        '2024-06-05 09:00:00+00', '2024-06-05 10:05:00+00'),
    ('102', 'LIMPIEZA_RAPIDA',   'juan.p@hotel.com',  '2024-06-05', 'COMPLETADA', 3,
        '2024-06-05 10:30:00+00', '2024-06-05 11:00:00+00'),
    ('202', 'MANTENIMIENTO',     'pedro.t@hotel.com', '2024-06-05', 'EN_PROCESO', 1,  -- caso borde: urgente
        '2024-06-05 08:00:00+00', NULL),
    ('303', 'INSPECCION',        'maria.h@hotel.com', '2024-07-01', 'PENDIENTE',  3, NULL, NULL),
    ('PH1', 'LIMPIEZA_COMPLETA', 'juan.p@hotel.com',  '2024-06-01', 'OMITIDA',   5, NULL, NULL); -- caso borde: omitida

INSERT INTO reporte (asignacion_id, aprobado, observaciones, inspector) VALUES
    (1, TRUE,  NULL,                              'supervisor@hotel.com'),
    (2, FALSE, 'Espejo del baño con manchas',      'supervisor@hotel.com'), -- caso borde: rechazada
    (3, FALSE, 'En progreso, pendiente cierre',    'supervisor@hotel.com');  -- caso borde: mantenimiento abierto
