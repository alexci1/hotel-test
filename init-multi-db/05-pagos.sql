-- ============================================================
-- 05-pagos.sql
-- Microservicio: Pagos
-- Base de datos: pagos
-- Tablas maestras : pago, factura, cargo
-- Tablas proyección (recibidas vía Kafka):
--   · proj_reserva  ← publicada por microservicio reservas
--   · proj_huesped  ← publicada por microservicio huespedes
-- ============================================================

\c pagos

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS cargo         CASCADE;
DROP TABLE IF EXISTS pago          CASCADE;
DROP TABLE IF EXISTS factura       CASCADE;
DROP TABLE IF EXISTS proj_reserva  CASCADE;
DROP TABLE IF EXISTS proj_huesped  CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS DE PROYECCIÓN
-- ------------------------------------------------------------

CREATE TABLE proj_reserva (
    codigo_reserva    VARCHAR(20)  PRIMARY KEY,
    email_huesped     VARCHAR(120) NOT NULL,
    numero_habitacion VARCHAR(10)  NOT NULL,
    fecha_entrada     DATE         NOT NULL,
    fecha_salida      DATE         NOT NULL,
    actualizado_en    DATE         NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE proj_reserva IS 'Réplica mínima de reservas recibida vía Kafka. Solo lectura.';

CREATE TABLE proj_huesped (
    email           VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    actualizado_en  DATE         NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE proj_huesped IS 'Réplica mínima de huéspedes recibida vía Kafka. Solo lectura.';

-- ------------------------------------------------------------
-- 3. TABLAS MAESTRAS
-- ------------------------------------------------------------

CREATE TABLE factura (
    id              SERIAL       PRIMARY KEY,
    numero_factura  VARCHAR(20)  NOT NULL UNIQUE,
    codigo_reserva  VARCHAR(20)  NOT NULL UNIQUE
        REFERENCES proj_reserva(codigo_reserva),
    email_huesped   VARCHAR(120) NOT NULL
        REFERENCES proj_huesped(email),
    total_usd       INT          NOT NULL DEFAULT 0 CHECK (total_usd >= 0),
    estado          VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE'
        CHECK (estado IN ('PENDIENTE','PARCIAL','PAGADA','ANULADA')),
    emitida_en      DATE         NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE factura IS 'Factura global por estancia. Publicada en Kafka topic: pago.events';
CREATE INDEX idx_factura_reserva ON factura(codigo_reserva);
CREATE INDEX idx_factura_estado  ON factura(estado);

CREATE TABLE pago (
    id              SERIAL       PRIMARY KEY,
    numero_factura  VARCHAR(20)  NOT NULL
        REFERENCES factura(numero_factura) ON UPDATE CASCADE,
    monto_usd       INT          NOT NULL CHECK (monto_usd > 0),
    metodo          VARCHAR(30)  NOT NULL
        CHECK (metodo IN ('EFECTIVO','TARJETA_CREDITO','TARJETA_DEBITO','TRANSFERENCIA','OTRO')),
    referencia      VARCHAR(80),
    pagado_en       DATE         NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE pago IS 'Pagos realizados contra una factura. Una factura puede recibir múltiples pagos.';
CREATE INDEX idx_pago_factura ON pago(numero_factura);
CREATE INDEX idx_pago_metodo  ON pago(metodo);

CREATE TABLE cargo (
    id              SERIAL       PRIMARY KEY,
    numero_factura  VARCHAR(20)  NOT NULL
        REFERENCES factura(numero_factura) ON UPDATE CASCADE,
    concepto        VARCHAR(100) NOT NULL,
    monto_usd       INT          NOT NULL CHECK (monto_usd > 0),
    origen          VARCHAR(30)  NOT NULL DEFAULT 'HOTEL'
        CHECK (origen IN ('HOTEL','RESTAURANTE','MINIBAR','DANO','OTRO')),
    registrado_en   DATE         NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE cargo IS 'Cargos extra agregados a la factura durante la estadía.';
CREATE INDEX idx_cargo_factura ON cargo(numero_factura);
CREATE INDEX idx_cargo_origen  ON cargo(origen);

-- ------------------------------------------------------------
-- 4. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO proj_reserva (codigo_reserva, email_huesped, numero_habitacion, fecha_entrada, fecha_salida) VALUES
    ('RES-20240601-0001', 'ana.garcia@email.com', '101', '2024-06-01', '2024-06-05'),
    ('RES-20240615-0002', 'carlos.m@email.com',   '202', '2024-06-15', '2024-06-20'),
    ('RES-20240701-0003', 'borde@test.com',        '303', '2024-07-01', '2024-07-02'),
    ('RES-20240801-0004', 'empresa@corp.com',      '202', '2024-08-01', '2024-08-10');

INSERT INTO proj_huesped (email, nombre_completo) VALUES
    ('ana.garcia@email.com', 'Ana García López'),
    ('carlos.m@email.com',   'Carlos Martínez Ruiz'),
    ('borde@test.com',       'Usuario Borde'),
    ('empresa@corp.com',     'Reserva Corporativa SA');

INSERT INTO factura (numero_factura, codigo_reserva, email_huesped, total_usd, estado) VALUES
    ('FAC-2024-00001', 'RES-20240601-0001', 'ana.garcia@email.com', 380, 'PAGADA'),
    ('FAC-2024-00002', 'RES-20240615-0002', 'carlos.m@email.com',   650, 'PARCIAL'),
    ('FAC-2024-00003', 'RES-20240701-0003', 'borde@test.com',        90, 'PENDIENTE'),
    ('FAC-2024-00004', 'RES-20240801-0004', 'empresa@corp.com',       0, 'ANULADA');

INSERT INTO pago (numero_factura, monto_usd, metodo, referencia) VALUES
    ('FAC-2024-00001', 380, 'TARJETA_CREDITO', 'TXN-VISA-001234'),
    ('FAC-2024-00002', 300, 'TRANSFERENCIA',   'TRF-BAN-005678'),
    ('FAC-2024-00002',  50, 'EFECTIVO',         NULL);

INSERT INTO cargo (numero_factura, concepto, monto_usd, origen) VALUES
    ('FAC-2024-00001', 'Cena gourmet noche del 02-jun',  45, 'RESTAURANTE'),
    ('FAC-2024-00001', 'Consumo minibar habitación 101', 13, 'MINIBAR'),
    ('FAC-2024-00002', 'Daño en silla de escritorio',    80, 'DANO'),
    ('FAC-2024-00003', 'Desayuno buffet',                18, 'RESTAURANTE');