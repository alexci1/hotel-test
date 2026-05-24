/* ============================================================
   ARCHIVO: 03-huespedes.sql
   Microservicio: huespedes
   Responsabilidad: administrar huespedes, documentos y preferencias.
   ============================================================ */

\c huespedes;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS preferencias CASCADE;
DROP TABLE IF EXISTS documentos CASCADE;
DROP TABLE IF EXISTS huespedes CASCADE;

-- 2. TABLAS MAESTRAS

CREATE TABLE huespedes (
    id SERIAL PRIMARY KEY,
    email VARCHAR(120) NOT NULL UNIQUE,
    nombre_completo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE documentos (
    id SERIAL PRIMARY KEY,
    email_huesped VARCHAR(120) NOT NULL REFERENCES huespedes(email) ON UPDATE CASCADE ON DELETE CASCADE,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('PASAPORTE','DNI','RUT','CEDULA','OTRO')),
    numero VARCHAR(40) NOT NULL,
    pais_emisor VARCHAR(2) NOT NULL,
    vencimiento DATE,
    CONSTRAINT uq_documentos UNIQUE (tipo, numero, pais_emisor)
);

CREATE TABLE preferencias (
    id SERIAL PRIMARY KEY,
    email_huesped VARCHAR(120) NOT NULL UNIQUE REFERENCES huespedes(email) ON UPDATE CASCADE ON DELETE CASCADE,
    piso_preferido INTEGER,
    tipo_cama VARCHAR(30) CHECK (tipo_cama IN ('MATRIMONIAL','TWIN','KING','QUEEN')),
    alergias VARCHAR(255),
    observaciones VARCHAR(255)
);

CREATE INDEX idx_huespedes_email ON huespedes(email);
CREATE INDEX idx_huespedes_nombre_completo ON huespedes(nombre_completo);
CREATE INDEX idx_huespedes_activo ON huespedes(activo);
CREATE INDEX idx_documentos_email_huesped ON documentos(email_huesped);
CREATE INDEX idx_documentos_tipo ON documentos(tipo);
CREATE INDEX idx_preferencias_email_huesped ON preferencias(email_huesped);

-- 3. DATOS DE PRUEBA

INSERT INTO huespedes (email, nombre_completo, telefono, activo) VALUES
('ana.garcia@email.com', 'Ana García López', '+56912345678', TRUE),
('carlos.m@email.com', 'Carlos Martínez Ruiz', '+56998765432', TRUE),
('borde@test.com', 'Usuario Borde Sin Tel', NULL, TRUE),
('empresa@corp.com', 'Reserva Corporativa SA', '+56900000001', TRUE),
('inactivo@old.com', 'Huésped Dado de Baja', NULL, FALSE);

INSERT INTO documentos (email_huesped, tipo, numero, pais_emisor, vencimiento) VALUES
('ana.garcia@email.com', 'RUT', '12345678-9', 'CL', NULL),
('ana.garcia@email.com', 'PASAPORTE', 'AA123456', 'CL', '2028-03-15'),
('carlos.m@email.com', 'DNI', '87654321X', 'ES', '2027-11-30'),
('borde@test.com', 'OTRO', 'SIN-DOC-01', 'CL', NULL),
('empresa@corp.com', 'RUT', '76543210-K', 'CL', NULL),
('inactivo@old.com', 'PASAPORTE', 'ZZ999999', 'AR', '2021-01-01');

INSERT INTO preferencias (email_huesped, piso_preferido, tipo_cama, alergias, observaciones) VALUES
('ana.garcia@email.com', 3, 'KING', 'mariscos', 'Prefiere habitación silenciosa'),
('carlos.m@email.com', NULL, 'TWIN', NULL, 'Viaja con mascota pequeña'),
('borde@test.com', NULL, NULL, NULL, NULL),
('empresa@corp.com', 1, 'MATRIMONIAL', 'polvo, látex', 'Requiere factura empresa');
