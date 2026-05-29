/* ============================================================
   ARCHIVO: 02-habitaciones.sql
   Microservicio: habitaciones
   Responsabilidad: administrar tipos, habitaciones y estados de habitaciones.
   ============================================================ */

\c habitaciones;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS estados_habitacion CASCADE;
DROP TABLE IF EXISTS habitaciones CASCADE;
DROP TABLE IF EXISTS proj_tarifas CASCADE;
DROP TABLE IF EXISTS tipos_habitacion CASCADE;

-- 2. TABLAS MAESTRAS

CREATE TABLE tipos_habitacion (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(40) NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    capacidad_max INTEGER NOT NULL DEFAULT 2 CHECK (capacidad_max BETWEEN 1 AND 10),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE proj_tarifas (
    tipo_habitacion VARCHAR(40) PRIMARY KEY REFERENCES tipos_habitacion(codigo) ON UPDATE CASCADE,
    precio_base_usd INTEGER NOT NULL CHECK (precio_base_usd > 0),
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE habitaciones (
    id SERIAL PRIMARY KEY,
    numero_habitacion VARCHAR(10) NOT NULL UNIQUE,
    piso INTEGER NOT NULL CHECK (piso >= 0),
    codigo_tipo VARCHAR(40) NOT NULL REFERENCES tipos_habitacion(codigo) ON UPDATE CASCADE,
    activa BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE estados_habitacion (
    id SERIAL PRIMARY KEY,
    numero_habitacion VARCHAR(10) NOT NULL UNIQUE REFERENCES habitaciones(numero_habitacion) ON UPDATE CASCADE,
    estado VARCHAR(30) NOT NULL DEFAULT 'LIMPIA'
        CHECK (estado IN ('LIMPIA','SUCIA','EN_MANTENIMIENTO','OCUPADA','BLOQUEADA')),
    observacion VARCHAR(200),
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE INDEX idx_tipos_habitacion_activo ON tipos_habitacion(activo);
CREATE INDEX idx_proj_tarifas_tipo_habitacion ON proj_tarifas(tipo_habitacion);
CREATE INDEX idx_habitaciones_codigo_tipo ON habitaciones(codigo_tipo);
CREATE INDEX idx_habitaciones_activa ON habitaciones(activa);
CREATE INDEX idx_estados_habitacion_numero_habitacion ON estados_habitacion(numero_habitacion);
CREATE INDEX idx_estados_habitacion_estado ON estados_habitacion(estado);

-- 3. DATOS DE PRUEBA

INSERT INTO tipos_habitacion (codigo, descripcion, capacidad_max, activo) VALUES
('SIMPLE', 'Habitacion individual con cama matrimonial', 2, TRUE),
('DOBLE', 'Habitacion con dos camas individuales', 4, TRUE),
('SUITE', 'Suite ejecutiva con sala de estar y jacuzzi', 2, TRUE),
('FAMILIAR', 'Habitacion amplia con litera y cama matrimonial', 6, TRUE),
('BORDE', 'Tipo sin precio asignado prueba borde', 1, FALSE);

INSERT INTO proj_tarifas (tipo_habitacion, precio_base_usd) VALUES
('SIMPLE', 80),
('DOBLE', 120),
('SUITE', 250);

INSERT INTO habitaciones (numero_habitacion, piso, codigo_tipo, activa) VALUES
('101', 1, 'SIMPLE', TRUE),
('102', 1, 'SIMPLE', TRUE),
('201', 2, 'DOBLE', TRUE),
('202', 2, 'DOBLE', TRUE),
('303', 3, 'SUITE', TRUE),
('404', 4, 'SIMPLE', FALSE),
('PH1', 5, 'SUITE', TRUE);

INSERT INTO estados_habitacion (numero_habitacion, estado, observacion) VALUES
('101', 'LIMPIA', NULL),
('102', 'SUCIA', 'Pendiente limpieza post checkout'),
('201', 'OCUPADA', NULL),
('202', 'EN_MANTENIMIENTO', 'Fuga en bano, plomero programado'),
('303', 'LIMPIA', NULL),
('404', 'BLOQUEADA', 'Habitacion fuera de servicio indefinido'),
('PH1', 'LIMPIA', NULL);