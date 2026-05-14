-- ============================================================
-- 04-checkin.sql
-- Microservicio: Check-in / Check-out
-- Base de datos: checkin
-- Tablas maestras : checkin, checkout, llave
-- Tablas proyección (recibidas vía Kafka):
--   · proj_reserva  ← publicada por microservicio reservas
--   · proj_huesped  ← publicada por microservicio huespedes
-- ============================================================

\c checkin

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS llave        CASCADE;
DROP TABLE IF EXISTS checkout     CASCADE;
DROP TABLE IF EXISTS checkin      CASCADE;
DROP TABLE IF EXISTS proj_reserva CASCADE;
DROP TABLE IF EXISTS proj_huesped CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS DE PROYECCIÓN (sincronizadas por Kafka)
-- ------------------------------------------------------------

-- Proyección mínima de reservas: solo los campos necesarios para validar ingreso
CREATE TABLE proj_reserva (
    codigo_reserva    VARCHAR(20)  PRIMARY KEY,
    email_huesped     VARCHAR(120) NOT NULL,
    numero_habitacion VARCHAR(10)  NOT NULL,
    fecha_entrada     DATE         NOT NULL,
    fecha_salida      DATE         NOT NULL,
    estado            VARCHAR(20)  NOT NULL,
    actualizado_en    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE proj_reserva IS 'Réplica mínima de reservas recibida vía Kafka. Solo lectura.';
CREATE INDEX idx_presereva_email ON proj_reserva(email_huesped);
CREATE INDEX idx_presereva_hab   ON proj_reserva(numero_habitacion);

-- Proyección mínima de huéspedes: nombre y email para bienvenida
CREATE TABLE proj_huesped (
    email           VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    actualizado_en  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE proj_huesped IS 'Réplica mínima de huéspedes recibida vía Kafka. Solo lectura.';

-- ------------------------------------------------------------
-- 3. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Registro de check-in
CREATE TABLE checkin (
    id                SERIAL       PRIMARY KEY,
    codigo_reserva    VARCHAR(20)  NOT NULL UNIQUE
        REFERENCES proj_reserva(codigo_reserva),
    email_huesped     VARCHAR(120) NOT NULL
        REFERENCES proj_huesped(email),
    numero_habitacion VARCHAR(10)  NOT NULL,
    fecha_hora        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    realizado_por     VARCHAR(80)  NOT NULL                 -- email del recepcionista
);
COMMENT ON TABLE checkin IS 'Registro de ingresos al hotel. Publicado en Kafka topic: checkin.events';
CREATE INDEX idx_checkin_reserva  ON checkin(codigo_reserva);
CREATE INDEX idx_checkin_habitacion ON checkin(numero_habitacion);

-- Registro de check-out
CREATE TABLE checkout (
    id             SERIAL       PRIMARY KEY,
    codigo_reserva VARCHAR(20)  NOT NULL UNIQUE
        REFERENCES proj_reserva(codigo_reserva),
    fecha_hora     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    realizado_por  VARCHAR(80)  NOT NULL,
    observaciones  TEXT
);
COMMENT ON TABLE checkout IS 'Registro de salidas del hotel. Publicado en Kafka topic: checkout.events';

-- Llaves/tarjetas magnéticas asignadas a la habitación
CREATE TABLE llave (
    id                SERIAL      PRIMARY KEY,
    numero_habitacion VARCHAR(10) NOT NULL,
    codigo_llave      VARCHAR(40) NOT NULL UNIQUE,         -- código físico de la tarjeta
    activa            BOOLEAN     NOT NULL DEFAULT TRUE,
    codigo_reserva    VARCHAR(20)
        REFERENCES proj_reserva(codigo_reserva),
    emitida_en        TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE llave IS 'Control de tarjetas/llaves por habitación. Se desactivan en checkout.';
CREATE INDEX idx_llave_habitacion ON llave(numero_habitacion);
CREATE INDEX idx_llave_activa     ON llave(activa);

-- ------------------------------------------------------------
-- 4. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO proj_reserva (codigo_reserva, email_huesped, numero_habitacion, fecha_entrada, fecha_salida, estado) VALUES
    ('RES-20240601-0001', 'ana.garcia@email.com', '101', '2024-06-01', '2024-06-05', 'CONFIRMADA'),
    ('RES-20240615-0002', 'carlos.m@email.com',   '202', '2024-06-15', '2024-06-20', 'CONFIRMADA'),
    ('RES-20240701-0003', 'borde@test.com',        '303', '2024-07-01', '2024-07-02', 'PENDIENTE'),   -- caso borde: pendiente
    ('RES-20240801-0004', 'empresa@corp.com',      '202', '2024-08-01', '2024-08-10', 'CANCELADA');   -- caso borde: cancelada

INSERT INTO proj_huesped (email, nombre_completo) VALUES
    ('ana.garcia@email.com', 'Ana García López'),
    ('carlos.m@email.com',   'Carlos Martínez Ruiz'),
    ('borde@test.com',       'Usuario Borde Sin Tel'),
    ('empresa@corp.com',     'Reserva Corporativa SA');

INSERT INTO checkin (codigo_reserva, email_huesped, numero_habitacion, realizado_por) VALUES
    ('RES-20240601-0001', 'ana.garcia@email.com', '101', 'recepcion@hotel.com'),
    ('RES-20240615-0002', 'carlos.m@email.com',   '202', 'recepcion@hotel.com');

INSERT INTO checkout (codigo_reserva, realizado_por, observaciones) VALUES
    ('RES-20240601-0001', 'recepcion@hotel.com', 'Salida sin novedades'),
    ('RES-20240615-0002', 'recepcion@hotel.com', NULL);   -- caso borde: sin observaciones

INSERT INTO llave (numero_habitacion, codigo_llave, activa, codigo_reserva) VALUES
    ('101', 'CARD-101-A', FALSE, 'RES-20240601-0001'),   -- desactivada en checkout
    ('202', 'CARD-202-B', FALSE, 'RES-20240615-0002'),   -- desactivada en checkout
    ('303', 'CARD-303-A', TRUE,  'RES-20240701-0003'),   -- activa (huésped aún no llega)
    ('303', 'CARD-303-B', FALSE, NULL);                  -- caso borde: llave huérfana inactiva
