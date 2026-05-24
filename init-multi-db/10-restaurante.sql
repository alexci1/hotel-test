/* ============================================================
   ARCHIVO: 10-restaurante.sql
   Microservicio: restaurante
   Responsabilidad: administrar mesas, pedidos e items de pedidos.
   ============================================================ */

\c restaurante;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS items_pedidos CASCADE;
DROP TABLE IF EXISTS pedidos CASCADE;
DROP TABLE IF EXISTS mesas CASCADE;
DROP TABLE IF EXISTS proj_huespedes CASCADE;

-- 2. TABLAS DE PROYECCIÓN

CREATE TABLE proj_huespedes (
    email VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    numero_habitacion VARCHAR(10),
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 3. TABLAS MAESTRAS

CREATE TABLE mesas (
    id SERIAL PRIMARY KEY,
    numero_mesa VARCHAR(10) NOT NULL UNIQUE,
    capacidad INTEGER NOT NULL CHECK (capacidad BETWEEN 1 AND 20),
    zona VARCHAR(40) NOT NULL DEFAULT 'SALON'
        CHECK (zona IN ('SALON','TERRAZA','PRIVADO','BARRA','ROOM_SERVICE')),
    disponible BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    numero_pedido VARCHAR(20) NOT NULL UNIQUE,
    numero_mesa VARCHAR(10) REFERENCES mesas(numero_mesa) ON UPDATE CASCADE,
    email_huesped VARCHAR(120) REFERENCES proj_huespedes(email),
    estado VARCHAR(20) NOT NULL DEFAULT 'ABIERTO'
        CHECK (estado IN ('ABIERTO','EN_COCINA','SERVIDO','PAGADO','CANCELADO')),
    total_usd INTEGER NOT NULL DEFAULT 0 CHECK (total_usd >= 0),
    creado_en DATE NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT chk_pedidos_origen CHECK (numero_mesa IS NOT NULL OR email_huesped IS NOT NULL)
);

CREATE TABLE items_pedidos (
    id SERIAL PRIMARY KEY,
    numero_pedido VARCHAR(20) NOT NULL REFERENCES pedidos(numero_pedido) ON UPDATE CASCADE ON DELETE CASCADE,
    nombre_producto VARCHAR(80) NOT NULL,
    cantidad INTEGER NOT NULL DEFAULT 1 CHECK (cantidad > 0),
    precio_unit_usd INTEGER NOT NULL CHECK (precio_unit_usd >= 0),
    observacion VARCHAR(255)
);

CREATE INDEX idx_proj_huespedes_nombre_completo ON proj_huespedes(nombre_completo);
CREATE INDEX idx_proj_huespedes_numero_habitacion ON proj_huespedes(numero_habitacion);

CREATE INDEX idx_mesas_numero_mesa ON mesas(numero_mesa);
CREATE INDEX idx_mesas_zona ON mesas(zona);
CREATE INDEX idx_mesas_disponible ON mesas(disponible);

CREATE INDEX idx_pedidos_numero_pedido ON pedidos(numero_pedido);
CREATE INDEX idx_pedidos_numero_mesa ON pedidos(numero_mesa);
CREATE INDEX idx_pedidos_email_huesped ON pedidos(email_huesped);
CREATE INDEX idx_pedidos_estado ON pedidos(estado);
CREATE INDEX idx_pedidos_creado_en ON pedidos(creado_en);

CREATE INDEX idx_items_pedidos_numero_pedido ON items_pedidos(numero_pedido);
CREATE INDEX idx_items_pedidos_nombre_producto ON items_pedidos(nombre_producto);

-- 4. DATOS DE PRUEBA

INSERT INTO proj_huespedes (email, nombre_completo, numero_habitacion) VALUES
('ana.garcia@email.com', 'Ana García López', '101'),
('carlos.m@email.com', 'Carlos Martínez', '202'),
('borde@test.com', 'Usuario Borde', NULL);

INSERT INTO mesas (numero_mesa, capacidad, zona, disponible) VALUES
('M01', 4, 'SALON', TRUE),
('M02', 2, 'SALON', FALSE),
('M03', 8, 'PRIVADO', TRUE),
('T01', 4, 'TERRAZA', TRUE),
('B01', 1, 'BARRA', TRUE),
('RS', 1, 'ROOM_SERVICE', TRUE);

INSERT INTO pedidos (numero_pedido, numero_mesa, email_huesped, estado, total_usd) VALUES
('PED-20240601-001', 'M01', 'ana.garcia@email.com', 'PAGADO', 58),
('PED-20240601-002', 'M02', 'carlos.m@email.com', 'SERVIDO', 34),
('PED-20240602-001', NULL, 'ana.garcia@email.com', 'ABIERTO', 22),
('PED-20240602-002', 'T01', NULL, 'ABIERTO', 15),
('PED-20240602-003', 'M01', 'borde@test.com', 'CANCELADO', 0);

INSERT INTO items_pedidos (numero_pedido, nombre_producto, cantidad, precio_unit_usd, observacion) VALUES
('PED-20240601-001', 'Lomo al jugo', 1, 28, 'Termino medio'),
('PED-20240601-001', 'Pisco Sour', 2, 8, NULL),
('PED-20240601-001', 'Postre del dia', 1, 5, NULL),
('PED-20240601-002', 'Ensalada Cesar', 1, 14, 'Sin crutones'),
('PED-20240601-002', 'Agua mineral', 2, 5, NULL),
('PED-20240602-001', 'Desayuno completo', 1, 22, 'Subir a hab 101 antes de las 8am'),
('PED-20240602-002', 'Cafe americano', 2, 7, NULL);
