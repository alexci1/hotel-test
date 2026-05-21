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
    email              VARCHAR(120) PRIMARY KEY,

    nombre_completo    VARCHAR(100) NOT NULL,

    telefono           VARCHAR(20),

    actualizado_en     DATE NOT NULL DEFAULT CURRENT_DATE
);

-- ============================================================
-- TABLA: PROJ_HABITACION
-- ============================================================

CREATE TABLE proj_habitacion (
    numero_habitacion    VARCHAR(10) PRIMARY KEY,

    tipo                 VARCHAR(40) NOT NULL,

    activa               BOOLEAN NOT NULL DEFAULT TRUE,

    actualizado_en       DATE NOT NULL DEFAULT CURRENT_DATE
);

-- ============================================================
-- TABLA: RESERVA
-- ============================================================

CREATE TABLE reserva (
    id                     SERIAL PRIMARY KEY,

    codigo_reserva         VARCHAR(20) NOT NULL UNIQUE,

    email_huesped          VARCHAR(120) NOT NULL,

    numero_habitacion      VARCHAR(10) NOT NULL,

    fecha_entrada          DATE NOT NULL,

    fecha_salida           DATE NOT NULL,

    estado                 VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
        CHECK (
            estado IN (
                'PENDIENTE',
                'CONFIRMADA',
                'CANCELADA',
                'COMPLETADA'
            )
        ),

    creado_en              DATE NOT NULL DEFAULT CURRENT_DATE,

    CONSTRAINT fk_reserva_huesped
    FOREIGN KEY (email_huesped)
    REFERENCES proj_huesped(email)
    ON UPDATE CASCADE,

    CONSTRAINT fk_reserva_habitacion
    FOREIGN KEY (numero_habitacion)
    REFERENCES proj_habitacion(numero_habitacion)
    ON UPDATE CASCADE,

    CONSTRAINT chk_fechas
    CHECK (fecha_salida > fecha_entrada)
);

-- ============================================================
-- TABLA: DISPONIBILIDAD
-- ============================================================

CREATE TABLE disponibilidad (
    id                     SERIAL PRIMARY KEY,

    numero_habitacion      VARCHAR(10) NOT NULL,

    fecha                  DATE NOT NULL,

    disponible             BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_disponibilidad_habitacion
    FOREIGN KEY (numero_habitacion)
    REFERENCES proj_habitacion(numero_habitacion)
    ON UPDATE CASCADE,

    CONSTRAINT uq_disp
    UNIQUE (numero_habitacion, fecha)
);

-- ============================================================
-- TABLA: CANCELACION
-- ============================================================

CREATE TABLE cancelacion (
    id                    SERIAL PRIMARY KEY,

    codigo_reserva        VARCHAR(20) NOT NULL UNIQUE,

    motivo                VARCHAR(200),

    cancelado_por         VARCHAR(80),

    cancelado_en          DATE NOT NULL DEFAULT CURRENT_DATE,

    penalidad_usd         NUMERIC(10,2) NOT NULL DEFAULT 0.00
        CHECK (penalidad_usd >= 0),

    CONSTRAINT fk_cancelacion_reserva
    FOREIGN KEY (codigo_reserva)
    REFERENCES reserva(codigo_reserva)
    ON UPDATE CASCADE
);

-- ============================================================
-- INSERTS: PROJ_HUESPED
-- ============================================================

INSERT INTO proj_huesped
(email, nombre_completo, telefono)
VALUES

('ana.garcia@email.com',
'Ana Garcia Lopez',
'+56912345678'),

('carlos.m@email.com',
'Carlos Martinez',
'+56998765432'),

('borde@test.com',
'Usuario Borde',
NULL),

('empresa@corp.com',
'Reserva Corporativa',
'+56900000001');

-- ============================================================
-- INSERTS: PROJ_HABITACION
-- ============================================================

INSERT INTO proj_habitacion
(numero_habitacion, tipo, activa)
VALUES

('101',
'SIMPLE',
TRUE),

('202',
'DOBLE',
TRUE),

('303',
'SUITE',
TRUE),

('404',
'SIMPLE',
FALSE);

-- ============================================================
-- INSERTS: RESERVA
-- ============================================================

INSERT INTO reserva
(codigo_reserva,
email_huesped,
numero_habitacion,
fecha_entrada,
fecha_salida,
estado)
VALUES

('RES-20240601-0001',
'ana.garcia@email.com',
'101',
'2024-06-01',
'2024-06-05',
'CONFIRMADA'),

('RES-20240615-0002',
'carlos.m@email.com',
'202',
'2024-06-15',
'2024-06-20',
'CONFIRMADA'),

('RES-20240701-0003',
'borde@test.com',
'303',
'2024-07-01',
'2024-07-02',
'PENDIENTE'),

('RES-20240801-0004',
'empresa@corp.com',
'202',
'2024-08-01',
'2024-08-10',
'CANCELADA');

-- ============================================================
-- INSERTS: DISPONIBILIDAD
-- ============================================================

INSERT INTO disponibilidad
(numero_habitacion, fecha, disponible)
VALUES

('101',
'2024-06-01',
FALSE),

('101',
'2024-06-02',
FALSE),

('101',
'2024-06-03',
FALSE),

('101',
'2024-06-04',
FALSE),

('202',
'2024-06-15',
FALSE),

('202',
'2024-06-16',
FALSE),

('303',
'2024-07-01',
FALSE),

('101',
'2024-07-15',
TRUE);

-- ============================================================
-- INSERTS: CANCELACION
-- ============================================================

INSERT INTO cancelacion
(codigo_reserva,
motivo,
cancelado_por,
penalidad_usd)
VALUES

('RES-20240801-0004',
'Cambio de planes del cliente',
'agente@hotel.com',
50.00);
