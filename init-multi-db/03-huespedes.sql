-- ============================================================
-- 03-huespedes.sql
-- Microservicio: Huéspedes
-- Base de datos: huespedes
-- Tablas maestras : huesped, documento, preferencia
-- Tablas proyección (recibidas vía Kafka): ninguna requerida
-- Nota: huesped publica sus eventos al topic huesped.events
--       para que reservas, checkin y notificaciones proyecten.
-- ============================================================

\c huespedes

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS preferencia CASCADE;
DROP TABLE IF EXISTS documento   CASCADE;
DROP TABLE IF EXISTS huesped     CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Huésped: entidad central del microservicio
-- email es la clave de negocio global (no se comparte el ID interno)
CREATE TABLE huesped (
    id              SERIAL       PRIMARY KEY,
    email           VARCHAR(120) NOT NULL UNIQUE,        -- clave de negocio
    nombre_completo VARCHAR(100) NOT NULL,
    telefono        VARCHAR(20),
    activo          BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en       DATE         NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE huesped IS 'Registro maestro de huéspedes. Publicado en Kafka topic: huesped.events';
CREATE INDEX idx_huesped_email  ON huesped(email);
CREATE INDEX idx_huesped_nombre ON huesped(nombre_completo);

-- Documentos de identidad del huésped
CREATE TABLE documento (
    id            SERIAL       PRIMARY KEY,
    email_huesped VARCHAR(120) NOT NULL
        REFERENCES huesped(email) ON UPDATE CASCADE ON DELETE CASCADE,
    tipo          VARCHAR(20)  NOT NULL
        CHECK (tipo IN ('PASAPORTE','DNI','RUT','CEDULA','OTRO')),
    numero        VARCHAR(40)  NOT NULL,
    pais_emisor   VARCHAR(2)   NOT NULL,                  -- código ISO 3166-1 alpha-2
    vencimiento   DATE,
    CONSTRAINT uq_doc UNIQUE (tipo, numero, pais_emisor)
);
COMMENT ON TABLE documento IS 'Documentos de identidad por huésped. Un huésped puede tener múltiples documentos.';
CREATE INDEX idx_doc_email ON documento(email_huesped);

-- Preferencias del huésped para personalizar la estancia
CREATE TABLE preferencia (
    id             SERIAL       PRIMARY KEY,
    email_huesped  VARCHAR(120) NOT NULL UNIQUE
        REFERENCES huesped(email) ON UPDATE CASCADE ON DELETE CASCADE,
    piso_preferido INTEGER,
    tipo_cama      VARCHAR(30)  CHECK (tipo_cama IN ('MATRIMONIAL','TWIN','KING','QUEEN',NULL)),
    alergias       VARCHAR(255),                                    -- texto libre, ej: 'mariscos, polvo'
    observaciones  VARCHAR(255)
);
COMMENT ON TABLE preferencia IS 'Preferencias de estadía por huésped. Relación 1:1 con huesped.';

-- ------------------------------------------------------------
-- 3. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO huesped (email, nombre_completo, telefono, activo) VALUES
    ('ana.garcia@email.com',  'Ana García López',       '+56912345678', TRUE),
    ('carlos.m@email.com',    'Carlos Martínez Ruiz',   '+56998765432', TRUE),
    ('borde@test.com',        'Usuario Borde Sin Tel',   NULL,           TRUE),   -- caso borde: sin teléfono
    ('empresa@corp.com',      'Reserva Corporativa SA',  '+56900000001', TRUE),
    ('inactivo@old.com',      'Huésped Dado de Baja',    NULL,           FALSE);  -- caso borde: inactivo

INSERT INTO documento (email_huesped, tipo, numero, pais_emisor, vencimiento) VALUES
    ('ana.garcia@email.com', 'RUT',       '12345678-9', 'CL', NULL),
    ('ana.garcia@email.com', 'PASAPORTE', 'AA123456',   'CL', '2028-03-15'),  -- caso: múltiples documentos
    ('carlos.m@email.com',   'DNI',       '87654321X',  'ES', '2027-11-30'),
    ('borde@test.com',       'OTRO',      'SIN-DOC-01', 'CL', NULL),          -- caso borde: tipo OTRO
    ('empresa@corp.com',     'RUT',       '76543210-K', 'CL', NULL),
    ('inactivo@old.com',     'PASAPORTE', 'ZZ999999',   'AR', '2021-01-01');  -- caso borde: doc vencido

INSERT INTO preferencia (email_huesped, piso_preferido, tipo_cama, alergias, observaciones) VALUES
    ('ana.garcia@email.com', 3,    'KING',         'mariscos',        'Prefiere habitación silenciosa'),
    ('carlos.m@email.com',   NULL, 'TWIN',          NULL,             'Viaja con mascota pequeña'),
    ('borde@test.com',       NULL,  NULL,            NULL,             NULL),  -- caso borde: sin preferencias
    ('empresa@corp.com',     1,    'MATRIMONIAL',  'polvo, látex',    'Requiere factura empresa');