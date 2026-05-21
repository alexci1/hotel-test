-- ============================================================
-- 07-restaurante.sql
-- Microservicio: Restaurante
-- Base de datos: restaurante
-- Tablas maestras : mesa, pedido, item_pedido
-- Tablas proyección (recibidas vía Kafka):
--   · proj_huesped ← publicada por microservicio huespedes
-- ============================================================

\c restaurante

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS item_pedido CASCADE;
DROP TABLE IF EXISTS pedido CASCADE;
DROP TABLE IF EXISTS mesa CASCADE;
DROP TABLE IF EXISTS proj_huesped CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS DE PROYECCIÓN
-- ------------------------------------------------------------

CREATE TABLE proj_huesped (
    email             VARCHAR(120) PRIMARY KEY,
    nombre_completo   VARCHAR(100) NOT NULL,
    numero_habitacion VARCHAR(10),
    actualizado_en    DATE         NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE proj_huesped IS 'Réplica mínima de huéspedes recibida vía Kafka. Solo lectura.';

-- ------------------------------------------------------------
-- 3. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Mesas del restaurante
CREATE TABLE mesa (
    id          SERIAL      PRIMARY KEY,
    numero_mesa VARCHAR(10) NOT NULL UNIQUE,
    capacidad   SMALLINT    NOT NULL CHECK (capacidad BETWEEN 1 AND 20),
    zona        VARCHAR(40) NOT NULL DEFAULT 'SALON'
        CHECK (zona IN ('SALON','TERRAZA','PRIVADO','BARRA','ROOM_SERVICE')),
    disponible  BOOLEAN     NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE mesa IS 'Mesas del restaurante del hotel.';
CREATE INDEX idx_mesa_zona ON mesa(zona);

-- Pedido asociado a una mesa o habitación
CREATE TABLE pedido (
    id              SERIAL        PRIMARY KEY,
    numero_pedido   VARCHAR(20)   NOT NULL UNIQUE,
    numero_mesa     VARCHAR(10)
        REFERENCES mesa(numero_mesa) ON UPDATE CASCADE,
    email_huesped   VARCHAR(120)
        REFERENCES proj_huesped(email),
    estado          VARCHAR(20)   NOT NULL DEFAULT 'ABIERTO'
        CHECK (estado IN ('ABIERTO','EN_COCINA','SERVIDO','PAGADO','CANCELADO')),
    total_usd       NUMERIC(10,2) NOT NULL DEFAULT 0.00 CHECK (total_usd >= 0),
    creado_en       DATE          NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT chk_pedido_origen CHECK (numero_mesa IS NOT NULL OR email_huesped IS NOT NULL)
);
COMMENT ON TABLE pedido IS 'Pedido de restaurante. Puede ser en mesa o room service. Publicado en Kafka topic: restaurante.events';
CREATE INDEX idx_pedido_mesa   ON pedido(numero_mesa);
CREATE INDEX idx_pedido_estado ON pedido(estado);

-- Ítems de cada pedido
CREATE TABLE item_pedido (
    id              SERIAL       PRIMARY KEY,
    numero_pedido   VARCHAR(20)  NOT NULL
        REFERENCES pedido(numero_pedido) ON UPDATE CASCADE ON DELETE CASCADE,
    nombre_producto VARCHAR(80)  NOT NULL,
    cantidad        SMALLINT     NOT NULL DEFAULT 1 CHECK (cantidad > 0),
    precio_unit_usd NUMERIC(8,2) NOT NULL CHECK (precio_unit_usd >= 0),
    observacion     TEXT
);
COMMENT ON TABLE item_pedido IS 'Líneas de producto de un pedido.';
CREATE INDEX idx_item_pedido ON item_pedido(numero_pedido);

-- ------------------------------------------------------------
-- 4. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO proj_huesped (email, nombre_completo, numero_habitacion) VALUES
    ('ana.garcia@email.com', 'Ana García López', '101'),
    ('carlos.m@email.com',   'Carlos Martínez',  '202'),
    ('borde@test.com',       'Usuario Borde',    NULL);

INSERT INTO mesa (numero_mesa, capacidad, zona, disponible) VALUES
    ('M01', 4, 'SALON', TRUE),
    ('M02', 2, 'SALON', FALSE),
    ('M03', 8, 'PRIVADO', TRUE),
    ('T01', 4, 'TERRAZA', TRUE),
    ('B01', 1, 'BARRA', TRUE),
    ('RS',  1, 'ROOM_SERVICE', TRUE);

INSERT INTO pedido (numero_pedido, numero_mesa, email_huesped, estado, total_usd) VALUES
    ('PED-20240601-001', 'M01', 'ana.garcia@email.com', 'PAGADO',    58.50),
    ('PED-20240601-002', 'M02', 'carlos.m@email.com',   'SERVIDO',   34.00),
    ('PED-20240602-001', NULL,  'ana.garcia@email.com', 'ABIERTO',   22.00),
    ('PED-20240602-002', 'T01', NULL,                   'ABIERTO',   15.00),
    ('PED-20240602-003', 'M01', 'borde@test.com',       'CANCELADO',  0.00);

INSERT INTO item_pedido (numero_pedido, nombre_producto, cantidad, precio_unit_usd, observacion) VALUES
    ('PED-20240601-001', 'Lomo al jugo',       1, 28.00, 'Término medio'),
    ('PED-20240601-001', 'Pisco Sour',         2,  8.50, NULL),
    ('PED-20240601-001', 'Postre del día',     1,  5.00, NULL),
    ('PED-20240601-002', 'Ensalada César',     1, 14.00, 'Sin crutones'),
    ('PED-20240601-002', 'Agua mineral',       2,  5.00, NULL),
    ('PED-20240602-001', 'Desayuno completo',  1, 22.00, 'Subir a hab 101 antes de las 8am'),
    ('PED-20240602-002', 'Café americano',     2,  7.50, NULL);