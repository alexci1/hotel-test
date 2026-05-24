/* ============================================================
   ARCHIVO: 06-housekeeping.sql
   Microservicio: housekeeping
   Responsabilidad: administrar tareas, asignaciones y reportes de limpieza.
   ============================================================ */

\c housekeeping;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS reportes CASCADE;
DROP TABLE IF EXISTS asignaciones CASCADE;
DROP TABLE IF EXISTS tareas CASCADE;
DROP TABLE IF EXISTS proj_habitaciones CASCADE;

-- 2. TABLAS DE PROYECCIÓN

CREATE TABLE proj_habitaciones (
    numero_habitacion VARCHAR(10) PRIMARY KEY,
    tipo VARCHAR(40) NOT NULL,
    piso INTEGER NOT NULL,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 3. TABLAS MAESTRAS

CREATE TABLE tareas (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    duracion_min INTEGER NOT NULL CHECK (duracion_min > 0),
    activa BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE asignaciones (
    id SERIAL PRIMARY KEY,
    numero_habitacion VARCHAR(10) NOT NULL REFERENCES proj_habitaciones(numero_habitacion) ON UPDATE CASCADE,
    codigo_tarea VARCHAR(30) NOT NULL REFERENCES tareas(codigo) ON UPDATE CASCADE,
    email_camarero VARCHAR(120) NOT NULL,
    fecha_programada DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
        CHECK (estado IN ('PENDIENTE','EN_PROCESO','COMPLETADA','CANCELADA')),
    prioridad INTEGER NOT NULL DEFAULT 1 CHECK (prioridad BETWEEN 1 AND 5),
    iniciada_en DATE,
    completada_en DATE
);

CREATE TABLE reportes (
    id SERIAL PRIMARY KEY,
    asignacion_id INTEGER NOT NULL UNIQUE REFERENCES asignaciones(id) ON DELETE CASCADE,
    aprobado BOOLEAN NOT NULL DEFAULT FALSE,
    observaciones VARCHAR(255),
    inspector VARCHAR(120) NOT NULL,
    inspeccionado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE INDEX idx_proj_habitaciones_tipo ON proj_habitaciones(tipo);
CREATE INDEX idx_proj_habitaciones_piso ON proj_habitaciones(piso);

CREATE INDEX idx_tareas_codigo ON tareas(codigo);
CREATE INDEX idx_tareas_activa ON tareas(activa);

CREATE INDEX idx_asignaciones_numero_habitacion ON asignaciones(numero_habitacion);
CREATE INDEX idx_asignaciones_codigo_tarea ON asignaciones(codigo_tarea);
CREATE INDEX idx_asignaciones_email_camarero ON asignaciones(email_camarero);
CREATE INDEX idx_asignaciones_estado ON asignaciones(estado);
CREATE INDEX idx_asignaciones_fecha_programada ON asignaciones(fecha_programada);

CREATE INDEX idx_reportes_asignacion_id ON reportes(asignacion_id);
CREATE INDEX idx_reportes_aprobado ON reportes(aprobado);
CREATE INDEX idx_reportes_inspeccionado_en ON reportes(inspeccionado_en);

-- 4. DATOS DE PRUEBA

INSERT INTO proj_habitaciones (numero_habitacion, tipo, piso) VALUES
('101', 'SIMPLE', 1),
('102', 'SIMPLE', 1),
('201', 'DOBLE', 2),
('202', 'DOBLE', 2),
('303', 'SUITE', 3),
('404', 'SIMPLE', 4),
('PH1', 'SUITE', 5);

INSERT INTO tareas (codigo, descripcion, duracion_min, activa) VALUES
('LIMPIEZA_CHECKOUT', 'Limpieza completa despues de checkout', 45, TRUE),
('LIMPIEZA_DIARIA', 'Limpieza diaria de habitacion ocupada', 25, TRUE),
('REPOSICION_AMENITIES', 'Reposicion de amenities y minibar', 15, TRUE),
('INSPECCION_FINAL', 'Inspeccion final de calidad', 10, TRUE),
('TAREA_INACTIVA', 'Tarea deshabilitada de prueba', 30, FALSE);

INSERT INTO asignaciones (numero_habitacion, codigo_tarea, email_camarero, fecha_programada, estado, prioridad, iniciada_en, completada_en) VALUES
('101', 'LIMPIEZA_CHECKOUT', 'supervisor@hotel.com', '2024-06-05', 'COMPLETADA', 3, '2024-06-05', '2024-06-05'),
('102', 'LIMPIEZA_DIARIA', 'housekeeping@hotel.com', '2024-06-05', 'PENDIENTE', 2, NULL, NULL),
('202', 'LIMPIEZA_CHECKOUT', 'supervisor@hotel.com', '2024-06-20', 'EN_PROCESO', 4, '2024-06-20', NULL),
('303', 'REPOSICION_AMENITIES', 'bodega@hotel.com', '2024-07-01', 'PENDIENTE', 1, NULL, NULL),
('404', 'INSPECCION_FINAL', 'supervisor@hotel.com', '2024-06-10', 'CANCELADA', 1, NULL, NULL);

INSERT INTO reportes (asignacion_id, aprobado, observaciones, inspector, inspeccionado_en) VALUES
(1, TRUE, 'Habitacion lista para nueva reserva', 'supervisor@hotel.com', '2024-06-05'),
(5, FALSE, 'Habitacion bloqueada, no se realiza inspeccion', 'supervisor@hotel.com', '2024-06-10');
