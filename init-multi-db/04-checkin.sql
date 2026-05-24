/* ============================================================
   ARCHIVO: 04-checkin.sql
   Microservicio: checkin
   Responsabilidad: administrar checkins, checkouts y llaves.
   ============================================================ */

\c checkin;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS llaves CASCADE;
DROP TABLE IF EXISTS checkouts CASCADE;
DROP TABLE IF EXISTS checkins CASCADE;
DROP TABLE IF EXISTS proj_reservas CASCADE;
DROP TABLE IF EXISTS proj_huespedes CASCADE;

-- 2. TABLAS DE PROYECCIÓN

CREATE TABLE proj_reservas (
    codigo_reserva VARCHAR(20) PRIMARY KEY,
    email_huesped VARCHAR(120) NOT NULL,
    numero_habitacion VARCHAR(10) NOT NULL,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE proj_huespedes (
    email VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE checkins (
    id SERIAL PRIMARY KEY,
    codigo_reserva VARCHAR(20) NOT NULL UNIQUE REFERENCES proj_reservas(codigo_reserva),
    email_huesped VARCHAR(120) NOT NULL REFERENCES proj_huespedes(email),
    numero_habitacion VARCHAR(10) NOT NULL,
    fecha_hora DATE NOT NULL DEFAULT CURRENT_DATE,
    realizado_por VARCHAR(80) NOT NULL
);

CREATE TABLE checkouts (
    id SERIAL PRIMARY KEY,
    codigo_reserva VARCHAR(20) NOT NULL UNIQUE REFERENCES proj_reservas(codigo_reserva),
    fecha_hora DATE NOT NULL DEFAULT CURRENT_DATE,
    realizado_por VARCHAR(80) NOT NULL,
    observaciones VARCHAR(255)
);

CREATE TABLE llaves (
    id SERIAL PRIMARY KEY,
    numero_habitacion VARCHAR(10) NOT NULL,
    codigo_llave VARCHAR(40) NOT NULL UNIQUE,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    codigo_reserva VARCHAR(20) REFERENCES proj_reservas(codigo_reserva),
    emitida_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE INDEX idx_proj_reservas_email_huesped ON proj_reservas(email_huesped);
CREATE INDEX idx_proj_reservas_numero_habitacion ON proj_reservas(numero_habitacion);
CREATE INDEX idx_proj_reservas_estado ON proj_reservas(estado);

CREATE INDEX idx_checkins_codigo_reserva ON checkins(codigo_reserva);
CREATE INDEX idx_checkins_email_huesped ON checkins(email_huesped);
CREATE INDEX idx_checkins_numero_habitacion ON checkins(numero_habitacion);

CREATE INDEX idx_checkouts_codigo_reserva ON checkouts(codigo_reserva);
CREATE INDEX idx_checkouts_fecha_hora ON checkouts(fecha_hora);

CREATE INDEX idx_llaves_numero_habitacion ON llaves(numero_habitacion);
CREATE INDEX idx_llaves_codigo_reserva ON llaves(codigo_reserva);
CREATE INDEX idx_llaves_activas ON llaves(activa);

-- 3. DATOS DE PRUEBA

INSERT INTO proj_reservas (codigo_reserva, email_huesped, numero_habitacion, fecha_entrada, fecha_salida, estado) VALUES
('RES-20240601-0001', 'ana.garcia@email.com', '101', '2024-06-01', '2024-06-05', 'CONFIRMADA'),
('RES-20240615-0002', 'carlos.m@email.com', '202', '2024-06-15', '2024-06-20', 'CONFIRMADA'),
('RES-20240701-0003', 'borde@test.com', '303', '2024-07-01', '2024-07-02', 'PENDIENTE'),
('RES-20240801-0004', 'empresa@corp.com', '202', '2024-08-01', '2024-08-10', 'CANCELADA');

INSERT INTO proj_huespedes (email, nombre_completo) VALUES
('ana.garcia@email.com', 'Ana García López'),
('carlos.m@email.com', 'Carlos Martínez Ruiz'),
('borde@test.com', 'Usuario Borde Sin Tel'),
('empresa@corp.com', 'Reserva Corporativa SA');

INSERT INTO checkins (codigo_reserva, email_huesped, numero_habitacion, realizado_por) VALUES
('RES-20240601-0001', 'ana.garcia@email.com', '101', 'recepcion@hotel.com'),
('RES-20240615-0002', 'carlos.m@email.com', '202', 'recepcion@hotel.com');

INSERT INTO checkouts (codigo_reserva, realizado_por, observaciones) VALUES
('RES-20240601-0001', 'recepcion@hotel.com', 'Salida sin novedades'),
('RES-20240615-0002', 'recepcion@hotel.com', NULL);

INSERT INTO llaves (numero_habitacion, codigo_llave, activa, codigo_reserva) VALUES
('101', 'CARD-101-A', FALSE, 'RES-20240601-0001'),
('202', 'CARD-202-B', FALSE, 'RES-20240615-0002'),
('303', 'CARD-303-A', TRUE, 'RES-20240701-0003'),
('303', 'CARD-303-B', FALSE, NULL);
