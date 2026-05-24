/* ============================================================
   ARCHIVO: 13-tarifas.sql
   Microservicio: tarifas
   Responsabilidad: administrar temporadas, tarifas y descuentos.
   ============================================================ */

\c tarifas;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS descuentos CASCADE;
DROP TABLE IF EXISTS tarifas CASCADE;
DROP TABLE IF EXISTS temporadas CASCADE;
DROP TABLE IF EXISTS proj_tipos_habitacion CASCADE;

-- 2. TABLAS DE PROYECCIÓN

CREATE TABLE proj_tipos_habitacion (
    codigo VARCHAR(40) PRIMARY KEY,
    descripcion VARCHAR(100),
    capacidad_max INTEGER NOT NULL,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 3. TABLAS MAESTRAS

CREATE TABLE temporadas (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(80) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    CONSTRAINT chk_temporadas_fechas CHECK (fecha_fin >= fecha_inicio)
);

CREATE TABLE tarifas (
    id SERIAL PRIMARY KEY,
    codigo_temporada VARCHAR(30) NOT NULL REFERENCES temporadas(codigo) ON UPDATE CASCADE,
    tipo_habitacion VARCHAR(40) NOT NULL REFERENCES proj_tipos_habitacion(codigo) ON UPDATE CASCADE,
    precio_noche_usd INTEGER NOT NULL CHECK (precio_noche_usd > 0),
    incluye_desayuno BOOLEAN NOT NULL DEFAULT FALSE,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_tarifas UNIQUE (codigo_temporada, tipo_habitacion)
);

CREATE TABLE descuentos (
    id SERIAL PRIMARY KEY,
    codigo_descuento VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(100),
    porcentaje INTEGER NOT NULL CHECK (porcentaje > 0 AND porcentaje <= 100),
    aplica_a VARCHAR(40),
    valido_desde DATE NOT NULL,
    valido_hasta DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_descuentos_fechas CHECK (valido_hasta >= valido_desde)
);

CREATE INDEX idx_proj_tipos_habitacion_codigo ON proj_tipos_habitacion(codigo);
CREATE INDEX idx_proj_tipos_habitacion_capacidad_max ON proj_tipos_habitacion(capacidad_max);

CREATE INDEX idx_temporadas_codigo ON temporadas(codigo);
CREATE INDEX idx_temporadas_fecha_inicio ON temporadas(fecha_inicio);
CREATE INDEX idx_temporadas_fecha_fin ON temporadas(fecha_fin);

CREATE INDEX idx_tarifas_codigo_temporada ON tarifas(codigo_temporada);
CREATE INDEX idx_tarifas_tipo_habitacion ON tarifas(tipo_habitacion);
CREATE INDEX idx_tarifas_activa ON tarifas(activa);
CREATE INDEX idx_tarifas_incluye_desayuno ON tarifas(incluye_desayuno);

CREATE INDEX idx_descuentos_codigo_descuento ON descuentos(codigo_descuento);
CREATE INDEX idx_descuentos_aplica_a ON descuentos(aplica_a);
CREATE INDEX idx_descuentos_activo ON descuentos(activo);
CREATE INDEX idx_descuentos_valido_desde ON descuentos(valido_desde);
CREATE INDEX idx_descuentos_valido_hasta ON descuentos(valido_hasta);

-- 4. DATOS DE PRUEBA

INSERT INTO proj_tipos_habitacion (codigo, descripcion, capacidad_max) VALUES
('SIMPLE', 'Habitacion individual', 2),
('DOBLE', 'Habitacion doble', 4),
('SUITE', 'Suite ejecutiva', 2),
('FAMILIAR', 'Habitacion familiar', 6);

INSERT INTO temporadas (codigo, nombre, fecha_inicio, fecha_fin) VALUES
('BAJA-2024', 'Temporada baja 2024', '2024-03-01', '2024-06-14'),
('ALTA-2024', 'Temporada alta verano 2024', '2024-06-15', '2024-08-31'),
('FIESTAS-2024', 'Fiestas patrias 2024', '2024-09-15', '2024-09-20'),
('BORDE-1DIA', 'Temporada de un solo dia', '2024-10-01', '2024-10-01');

INSERT INTO tarifas (codigo_temporada, tipo_habitacion, precio_noche_usd, incluye_desayuno, activa) VALUES
('BAJA-2024', 'SIMPLE', 70, FALSE, TRUE),
('BAJA-2024', 'DOBLE', 100, FALSE, TRUE),
('BAJA-2024', 'SUITE', 200, TRUE, TRUE),
('ALTA-2024', 'SIMPLE', 95, FALSE, TRUE),
('ALTA-2024', 'DOBLE', 140, TRUE, TRUE),
('ALTA-2024', 'SUITE', 300, TRUE, TRUE),
('ALTA-2024', 'FAMILIAR', 170, TRUE, TRUE),
('FIESTAS-2024', 'SIMPLE', 120, TRUE, TRUE),
('FIESTAS-2024', 'SUITE', 400, TRUE, TRUE),
('BORDE-1DIA', 'SIMPLE', 1, FALSE, FALSE);

INSERT INTO descuentos (codigo_descuento, descripcion, porcentaje, aplica_a, valido_desde, valido_hasta, activo) VALUES
('CORP-10', 'Descuento corporativo', 10, NULL, '2024-01-01', '2024-12-31', TRUE),
('FIDELIDAD-15', 'Huesped frecuente', 15, NULL, '2024-01-01', '2024-12-31', TRUE),
('SUITE-5', 'Promo suite temporada baja', 5, 'SUITE', '2024-03-01', '2024-06-14', TRUE),
('VENCIDO', 'Descuento caducado', 20, NULL, '2023-01-01', '2023-12-31', FALSE),
('MAXIMO-100', 'Descuento borde 100%', 100, 'SIMPLE', '2024-06-01', '2024-06-30', FALSE);
