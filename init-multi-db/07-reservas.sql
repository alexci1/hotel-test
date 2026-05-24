/* ============================================================
   ARCHIVO: 07-reservas.sql
   Microservicio: reservas
   Responsabilidad: administrar reservas, disponibilidades y cancelaciones.
   ============================================================ */

\c reservas;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS cancelaciones CASCADE;
DROP TABLE IF EXISTS disponibilidades CASCADE;
DROP TABLE IF EXISTS reservas CASCADE;
DROP TABLE IF EXISTS proj_habitaciones CASCADE;
DROP TABLE IF EXISTS proj_huespedes CASCADE;

-- 2. TABLAS DE PROYECCIÓN

CREATE TABLE proj_huespedes (
    email VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE proj_habitaciones (
    numero_habitacion VARCHAR(10) PRIMARY KEY,
    tipo VARCHAR(40) NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 3. TABLAS MAESTRAS

CREATE TABLE reservas (
    id SERIAL PRIMARY KEY,
    codigo_reserva VARCHAR(20) NOT NULL UNIQUE,
    email_huesped VARCHAR(120) NOT NULL REFERENCES proj_huespedes(email) ON UPDATE CASCADE,
    numero_habitacion VARCHAR(10) NOT NULL REFERENCES proj_habitaciones(numero_habitacion) ON UPDATE CASCADE,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
        CHECK (estado IN ('PENDIENTE','CONFIRMADA','CANCELADA','COMPLETADA')),
    creado_en DATE NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT chk_reservas_fechas CHECK (fecha_salida > fecha_entrada)
);

CREATE TABLE disponibilidades (
    id SERIAL PRIMARY KEY,
    numero_habitacion VARCHAR(10) NOT NULL REFERENCES proj_habitaciones(numero_habitacion) ON UPDATE CASCADE,
    fecha DATE NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_disponibilidades UNIQUE (numero_habitacion, fecha)
);

CREATE TABLE cancelaciones (
    id SERIAL PRIMARY KEY,
    codigo_reserva VARCHAR(20) NOT NULL UNIQUE REFERENCES reservas(codigo_reserva) ON UPDATE CASCADE,
    motivo VARCHAR(200),
    cancelado_por VARCHAR(80),
    cancelado_en DATE NOT NULL DEFAULT CURRENT_DATE,
    penalidad_usd INTEGER NOT NULL DEFAULT 0 CHECK (penalidad_usd >= 0)
);

CREATE INDEX idx_proj_huespedes_nombre_completo ON proj_huespedes(nombre_completo);
CREATE INDEX idx_proj_habitaciones_tipo ON proj_habitaciones(tipo);
CREATE INDEX idx_proj_habitaciones_activa ON proj_habitaciones(activa);

CREATE INDEX idx_reservas_codigo_reserva ON reservas(codigo_reserva);
CREATE INDEX idx_reservas_email_huesped ON reservas(email_huesped);
CREATE INDEX idx_reservas_numero_habitacion ON reservas(numero_habitacion);
CREATE INDEX idx_reservas_estado ON reservas(estado);
CREATE INDEX idx_reservas_fecha_entrada ON reservas(fecha_entrada);
CREATE INDEX idx_reservas_fecha_salida ON reservas(fecha_salida);

CREATE INDEX idx_disponibilidades_numero_habitacion ON disponibilidades(numero_habitacion);
CREATE INDEX idx_disponibilidades_fecha ON disponibilidades(fecha);
CREATE INDEX idx_disponibilidades_disponible ON disponibilidades(disponible);

CREATE INDEX idx_cancelaciones_codigo_reserva ON cancelaciones(codigo_reserva);
CREATE INDEX idx_cancelaciones_cancelado_en ON cancelaciones(cancelado_en);

-- 4. DATOS DE PRUEBA

INSERT INTO proj_huespedes (email, nombre_completo, telefono) VALUES
('ana.garcia@email.com', 'Ana Garcia Lopez', '+56912345678'),
('carlos.m@email.com', 'Carlos Martinez', '+56998765432'),
('borde@test.com', 'Usuario Borde', NULL),
('empresa@corp.com', 'Reserva Corporativa', '+56900000001');

INSERT INTO proj_habitaciones (numero_habitacion, tipo, activa) VALUES
('101', 'SIMPLE', TRUE),
('202', 'DOBLE', TRUE),
('303', 'SUITE', TRUE),
('404', 'SIMPLE', FALSE);

INSERT INTO reservas (codigo_reserva, email_huesped, numero_habitacion, fecha_entrada, fecha_salida, estado) VALUES
('RES-20240601-0001', 'ana.garcia@email.com', '101', '2024-06-01', '2024-06-05', 'CONFIRMADA'),
('RES-20240615-0002', 'carlos.m@email.com', '202', '2024-06-15', '2024-06-20', 'CONFIRMADA'),
('RES-20240701-0003', 'borde@test.com', '303', '2024-07-01', '2024-07-02', 'PENDIENTE'),
('RES-20240801-0004', 'empresa@corp.com', '202', '2024-08-01', '2024-08-10', 'CANCELADA');

INSERT INTO disponibilidades (numero_habitacion, fecha, disponible) VALUES
('101', '2024-06-01', FALSE),
('101', '2024-06-02', FALSE),
('101', '2024-06-03', FALSE),
('101', '2024-06-04', FALSE),
('202', '2024-06-15', FALSE),
('202', '2024-06-16', FALSE),
('303', '2024-07-01', FALSE),
('101', '2024-07-15', TRUE);

INSERT INTO cancelaciones (codigo_reserva, motivo, cancelado_por, penalidad_usd) VALUES
('RES-20240801-0004', 'Cambio de planes del cliente', 'agente@hotel.com', 50);
