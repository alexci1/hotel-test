/* ============================================================
   ARCHIVO: 05-pagos.sql
   Microservicio: pagos
   Responsabilidad: administrar facturas, pagos y cargos.
   ============================================================ */

\c pagos;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS cargos CASCADE;
DROP TABLE IF EXISTS pagos CASCADE;
DROP TABLE IF EXISTS facturas CASCADE;
DROP TABLE IF EXISTS proj_reservas CASCADE;
DROP TABLE IF EXISTS proj_huespedes CASCADE;

-- 2. TABLAS DE PROYECCIÓN

CREATE TABLE proj_reservas (
    codigo_reserva VARCHAR(20) PRIMARY KEY,
    email_huesped VARCHAR(120) NOT NULL,
    numero_habitacion VARCHAR(10) NOT NULL,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE proj_huespedes (
    email VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 3. TABLAS MAESTRAS

CREATE TABLE facturas (
    id SERIAL PRIMARY KEY,
    numero_factura VARCHAR(20) NOT NULL UNIQUE,
    codigo_reserva VARCHAR(20) NOT NULL UNIQUE REFERENCES proj_reservas(codigo_reserva),
    email_huesped VARCHAR(120) NOT NULL REFERENCES proj_huespedes(email),
    total_usd INTEGER NOT NULL DEFAULT 0 CHECK (total_usd >= 0),
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
        CHECK (estado IN ('PENDIENTE','PARCIAL','PAGADA','ANULADA')),
    emitida_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE pagos (
    id SERIAL PRIMARY KEY,
    numero_factura VARCHAR(20) NOT NULL REFERENCES facturas(numero_factura) ON UPDATE CASCADE,
    monto_usd INTEGER NOT NULL CHECK (monto_usd > 0),
    metodo VARCHAR(30) NOT NULL
        CHECK (metodo IN ('EFECTIVO','TARJETA_CREDITO','TARJETA_DEBITO','TRANSFERENCIA','OTRO')),
    referencia VARCHAR(80),
    pagado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE cargos (
    id SERIAL PRIMARY KEY,
    numero_factura VARCHAR(20) NOT NULL REFERENCES facturas(numero_factura) ON UPDATE CASCADE,
    concepto VARCHAR(100) NOT NULL,
    monto_usd INTEGER NOT NULL CHECK (monto_usd > 0),
    origen VARCHAR(30) NOT NULL DEFAULT 'HOTEL'
        CHECK (origen IN ('HOTEL','RESTAURANTE','MINIBAR','DANO','OTRO')),
    registrado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE INDEX idx_proj_reservas_email_huesped ON proj_reservas(email_huesped);
CREATE INDEX idx_proj_reservas_numero_habitacion ON proj_reservas(numero_habitacion);
CREATE INDEX idx_proj_huespedes_nombre_completo ON proj_huespedes(nombre_completo);

CREATE INDEX idx_facturas_codigo_reserva ON facturas(codigo_reserva);
CREATE INDEX idx_facturas_email_huesped ON facturas(email_huesped);
CREATE INDEX idx_facturas_estado ON facturas(estado);

CREATE INDEX idx_pagos_numero_factura ON pagos(numero_factura);
CREATE INDEX idx_pagos_metodo ON pagos(metodo);
CREATE INDEX idx_pagos_pagado_en ON pagos(pagado_en);

CREATE INDEX idx_cargos_numero_factura ON cargos(numero_factura);
CREATE INDEX idx_cargos_origen ON cargos(origen);
CREATE INDEX idx_cargos_registrado_en ON cargos(registrado_en);

-- 4. DATOS DE PRUEBA

INSERT INTO proj_reservas (codigo_reserva, email_huesped, numero_habitacion, fecha_entrada, fecha_salida) VALUES
('RES-20240601-0001', 'ana.garcia@email.com', '101', '2024-06-01', '2024-06-05'),
('RES-20240615-0002', 'carlos.m@email.com', '202', '2024-06-15', '2024-06-20'),
('RES-20240701-0003', 'borde@test.com', '303', '2024-07-01', '2024-07-02'),
('RES-20240801-0004', 'empresa@corp.com', '202', '2024-08-01', '2024-08-10');

INSERT INTO proj_huespedes (email, nombre_completo) VALUES
('ana.garcia@email.com', 'Ana García López'),
('carlos.m@email.com', 'Carlos Martínez Ruiz'),
('borde@test.com', 'Usuario Borde'),
('empresa@corp.com', 'Reserva Corporativa SA');

INSERT INTO facturas (numero_factura, codigo_reserva, email_huesped, total_usd, estado) VALUES
('FAC-2024-00001', 'RES-20240601-0001', 'ana.garcia@email.com', 380, 'PAGADA'),
('FAC-2024-00002', 'RES-20240615-0002', 'carlos.m@email.com', 650, 'PARCIAL'),
('FAC-2024-00003', 'RES-20240701-0003', 'borde@test.com', 90, 'PENDIENTE'),
('FAC-2024-00004', 'RES-20240801-0004', 'empresa@corp.com', 0, 'ANULADA');

INSERT INTO pagos (numero_factura, monto_usd, metodo, referencia) VALUES
('FAC-2024-00001', 380, 'TARJETA_CREDITO', 'TXN-VISA-001234'),
('FAC-2024-00002', 300, 'TRANSFERENCIA', 'TRF-BAN-005678'),
('FAC-2024-00002', 50, 'EFECTIVO', NULL);

INSERT INTO cargos (numero_factura, concepto, monto_usd, origen) VALUES
('FAC-2024-00001', 'Cena gourmet noche del 02-jun', 45, 'RESTAURANTE'),
('FAC-2024-00001', 'Consumo minibar habitacion 101', 13, 'MINIBAR'),
('FAC-2024-00002', 'Dano en silla de escritorio', 80, 'DANO'),
('FAC-2024-00003', 'Desayuno buffet', 18, 'RESTAURANTE');
