-- ============================================================
-- 01-reservas.sql
-- Microservicio: Reservas
-- Base de datos: reservas
-- Tablas maestras : reserva, disponibilidad, cancelacion
-- Tablas proyección (recibidas vía Kafka):
--   · proj_habitacion   ← publicada por microservicio habitaciones
--   · proj_huesped      ← publicada por microservicio huespedes
-- ============================================================

\c reservas

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa (hijos antes que padres)
-- ------------------------------------------------------------
DROP TABLE IF EXISTS cancelacion      CASCADE;
DROP TABLE IF EXISTS disponibilidad   CASCADE;
DROP TABLE IF EXISTS reserva          CASCADE;
-- Proyecciones
DROP TABLE IF EXISTS proj_habitacion  CASCADE;
DROP TABLE IF EXISTS proj_huesped     CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Proyección local de huéspedes sincronizada desde Kafka
-- Clave de negocio: email (no usamos IDs externos)
CREATE TABLE proj_huesped (
    email           VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    telefono        VARCHAR(20),
    actualizado_en  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE proj_huesped IS 'Réplica simplificada de huéspedes recibida vía Kafka. Solo lectura.';

-- Proyección local de habitaciones sincronizada desde Kafka
-- Clave de negocio: numero_habitacion
CREATE TABLE proj_habitacion (
    numero_habitacion VARCHAR(10)  PRIMARY KEY,
    tipo              VARCHAR(40)  NOT NULL,   -- SIMPLE, DOBLE, SUITE, etc.
    activa            BOOLEAN      NOT NULL DEFAULT TRUE,
    actualizado_en    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE proj_habitacion IS 'Réplica simplificada de habitaciones recibida vía Kafka. Solo lectura.';

-- Tabla maestra de reservas
-- codigo_reserva: clave de negocio usada por todos los microservicios
CREATE TABLE reserva (
    id               SERIAL       PRIMARY KEY,
    codigo_reserva   VARCHAR(20)  NOT NULL UNIQUE,            -- ej: RES-20240501-0001
    email_huesped    VARCHAR(120) NOT NULL
        REFERENCES proj_huesped(email) ON UPDATE CASCADE,
    numero_habitacion VARCHAR(10) NOT NULL
        REFERENCES proj_habitacion(numero_habitacion) ON UPDATE CASCADE,
    fecha_entrada    DATE         NOT NULL,
    fecha_salida     DATE         NOT NULL,
    estado           VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE' -- PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA
        CHECK (estado IN ('PENDIENTE','CONFIRMADA','CANCELADA','COMPLETADA')),
    creado_en        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_fechas CHECK (fecha_salida > fecha_entrada)
);
COMMENT ON TABLE reserva IS 'Tabla maestra de reservas hoteleras. Publicada en Kafka topic: reserva.events';
CREATE INDEX idx_reserva_email      ON reserva(email_huesped);
CREATE INDEX idx_reserva_habitacion ON reserva(numero_habitacion);
CREATE INDEX idx_reserva_fechas     ON reserva(fecha_entrada, fecha_salida);
CREATE INDEX idx_reserva_estado     ON reserva(estado);

-- Disponibilidad por habitación y fecha
CREATE TABLE disponibilidad (
    id                SERIAL      PRIMARY KEY,
    numero_habitacion VARCHAR(10) NOT NULL
        REFERENCES proj_habitacion(numero_habitacion) ON UPDATE CASCADE,
    fecha             DATE        NOT NULL,
    disponible        BOOLEAN     NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_disp UNIQUE (numero_habitacion, fecha)
);
COMMENT ON TABLE disponibilidad IS 'Bloquea/libera habitaciones por día. Actualizada al confirmar/cancelar reservas.';
CREATE INDEX idx_disp_fecha ON disponibilidad(fecha);

-- Cancelaciones de reservas
CREATE TABLE cancelacion (
    id               SERIAL       PRIMARY KEY,
    codigo_reserva   VARCHAR(20)  NOT NULL UNIQUE
        REFERENCES reserva(codigo_reserva) ON UPDATE CASCADE,
    motivo           TEXT,
    cancelado_por    VARCHAR(80),                             -- email del agente o sistema
    cancelado_en     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    penalidad_usd    NUMERIC(10,2) NOT NULL DEFAULT 0.00
        CHECK (penalidad_usd >= 0)
);
COMMENT ON TABLE cancelacion IS 'Registro de cancelaciones. Una reserva solo puede cancelarse una vez.';
CREATE INDEX idx_cancel_fecha ON cancelacion(cancelado_en);

-- ------------------------------------------------------------
-- 3. DATOS DE PRUEBA
-- ------------------------------------------------------------

-- Proyecciones (simulan llegada de eventos Kafka)
INSERT INTO proj_huesped (email, nombre_completo, telefono) VALUES
    ('ana.garcia@email.com',   'Ana García López',    '+56912345678'),
    ('carlos.m@email.com',     'Carlos Martínez',     '+56998765432'),
    ('borde@test.com',         'Usuario Borde',        NULL),            -- caso borde: sin teléfono
    ('empresa@corp.com',       'Reserva Corporativa', '+56900000001');

INSERT INTO proj_habitacion (numero_habitacion, tipo, activa) VALUES
    ('101', 'SIMPLE',   TRUE),
    ('202', 'DOBLE',    TRUE),
    ('303', 'SUITE',    TRUE),
    ('404', 'SIMPLE',   FALSE);   -- caso borde: habitación inactiva

-- Reservas normales
INSERT INTO reserva (codigo_reserva, email_huesped, numero_habitacion, fecha_entrada, fecha_salida, estado) VALUES
    ('RES-20240601-0001', 'ana.garcia@email.com', '101', '2024-06-01', '2024-06-05', 'CONFIRMADA'),
    ('RES-20240615-0002', 'carlos.m@email.com',   '202', '2024-06-15', '2024-06-20', 'CONFIRMADA'),
    ('RES-20240701-0003', 'borde@test.com',        '303', '2024-07-01', '2024-07-02', 'PENDIENTE'),   -- caso borde: 1 noche
    ('RES-20240801-0004', 'empresa@corp.com',      '202', '2024-08-01', '2024-08-10', 'CANCELADA');

-- Disponibilidad (bloqueada durante reservas activas)
INSERT INTO disponibilidad (numero_habitacion, fecha, disponible) VALUES
    ('101', '2024-06-01', FALSE), ('101', '2024-06-02', FALSE),
    ('101', '2024-06-03', FALSE), ('101', '2024-06-04', FALSE),
    ('202', '2024-06-15', FALSE), ('202', '2024-06-16', FALSE),
    ('303', '2024-07-01', FALSE),
    ('101', '2024-07-15', TRUE);   -- caso borde: fecha futura libre

-- Cancelación
INSERT INTO cancelacion (codigo_reserva, motivo, cancelado_por, penalidad_usd) VALUES
    ('RES-20240801-0004', 'Cambio de planes del cliente', 'agente@hotel.com', 50.00);
