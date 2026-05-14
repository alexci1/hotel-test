-- ============================================================
-- 12-autenticacion.sql
-- Microservicio: Autenticación
-- Base de datos: autenticacion
-- Tablas maestras : rol, usuario, sesion
-- Tablas proyección: ninguna
--   (este microservicio es fuente de verdad de identidad;
--    publica eventos al topic auth.events para que otros MS
--    puedan sincronizar permisos si lo necesitan)
-- ============================================================

\c autenticacion

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS sesion  CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS rol     CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Roles del sistema (RBAC básico)
CREATE TABLE rol (
    id          SERIAL       PRIMARY KEY,
    codigo      VARCHAR(30)  NOT NULL UNIQUE,               -- ej: ADMIN, RECEPCION, HOUSEKEEPING
    descripcion VARCHAR(100),
    activo      BOOLEAN      NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE rol IS 'Roles del sistema para control de acceso basado en roles (RBAC). Publicado en Kafka topic: auth.events';

-- Usuarios del sistema (empleados o integraciones)
CREATE TABLE usuario (
    id              SERIAL       PRIMARY KEY,
    email           VARCHAR(120) NOT NULL UNIQUE,           -- clave de negocio
    nombre_completo VARCHAR(100) NOT NULL,
    codigo_rol      VARCHAR(30)  NOT NULL
        REFERENCES rol(codigo) ON UPDATE CASCADE,
    hash_password   VARCHAR(255) NOT NULL,                  -- bcrypt hash, nunca texto plano
    activo          BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    ultimo_acceso   TIMESTAMPTZ
);
COMMENT ON TABLE usuario IS 'Usuarios del sistema. Nunca almacenar password en texto plano.';
CREATE INDEX idx_usuario_rol    ON usuario(codigo_rol);
CREATE INDEX idx_usuario_activo ON usuario(activo);

-- Sesiones activas (tokens JWT o de sesión)
CREATE TABLE sesion (
    id              SERIAL       PRIMARY KEY,
    email_usuario   VARCHAR(120) NOT NULL
        REFERENCES usuario(email) ON UPDATE CASCADE ON DELETE CASCADE,
    token_hash      VARCHAR(255) NOT NULL UNIQUE,           -- hash SHA-256 del JWT, no el token real
    ip_origen       INET         NOT NULL,
    user_agent      VARCHAR(250),
    expira_en       TIMESTAMPTZ  NOT NULL,
    creada_en       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    invalidada      BOOLEAN      NOT NULL DEFAULT FALSE
);
COMMENT ON TABLE sesion IS 'Registro de sesiones activas. Limpiar sesiones expiradas periódicamente.';
CREATE INDEX idx_sesion_usuario   ON sesion(email_usuario);
CREATE INDEX idx_sesion_expira    ON sesion(expira_en);
CREATE INDEX idx_sesion_activa    ON sesion(invalidada) WHERE NOT invalidada;

-- ------------------------------------------------------------
-- 3. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO rol (codigo, descripcion, activo) VALUES
    ('ADMIN',           'Administrador del sistema con acceso total',              TRUE),
    ('GERENCIA',        'Gerente de hotel: acceso a reportes y configuración',     TRUE),
    ('RECEPCION',       'Recepcionista: reservas, checkin, checkout',              TRUE),
    ('HOUSEKEEPING',    'Camarero/a: asignaciones de limpieza',                    TRUE),
    ('RESTAURANTE',     'Personal de restaurante: pedidos y mesas',                TRUE),
    ('BODEGA',          'Encargado de inventario',                                  TRUE),
    ('SOLO_LECTURA',    'Acceso de solo lectura para auditoría',                   TRUE),
    ('INACTIVO',        'Rol dado de baja',                                        FALSE); -- caso borde: inactivo

-- Contraseñas hasheadas con bcrypt (los valores son hashes ficticios para pruebas)
INSERT INTO usuario (email, nombre_completo, codigo_rol, hash_password, activo, ultimo_acceso) VALUES
    ('admin@hotel.com',       'Administrador Principal',    'ADMIN',
     '$2b$12$ADMINHASHADMINHASHADMINHASHADMINHASHADMIN', TRUE, NOW()),
    ('gerente@hotel.com',     'María Fernández',            'GERENCIA',
     '$2b$12$GERENTEGERENTEGERENTEGERENTEGERENTEGERENT', TRUE, NOW() - INTERVAL '2 hours'),
    ('recepcion@hotel.com',   'Juan Recepcionista',         'RECEPCION',
     '$2b$12$RECEPCIONHASHRECEPCIONHASHRECEPCIONHASHRE', TRUE, NOW() - INTERVAL '30 minutes'),
    ('supervisor@hotel.com',  'Supervisora HK',             'HOUSEKEEPING',
     '$2b$12$SUPERVHASHSUPERVHASHSUPERVHASHSUPERVHASH', TRUE, NOW() - INTERVAL '1 hour'),
    ('agente@hotel.com',      'Agente de Ventas',           'RECEPCION',
     '$2b$12$AGENTEHASHAGENTEHASHAGENTEHASHAGENTEHASH', TRUE, NOW() - INTERVAL '3 days'),
    ('bodega@hotel.com',      'Encargado Bodega',           'BODEGA',
     '$2b$12$BODEGAHASHBODEGAHASHBODEGAHASHBODEGAHASH', TRUE, NULL),          -- caso borde: nunca ha accedido
    ('baja@hotel.com',        'Empleado Dado de Baja',      'SOLO_LECTURA',
     '$2b$12$BAJAHASHBAJAHASHBAJAHASHBAJAHASHBAJAHASH', FALSE, '2023-12-01 08:00:00+00'); -- caso borde: inactivo

INSERT INTO sesion (email_usuario, token_hash, ip_origen, user_agent, expira_en, invalidada) VALUES
    ('admin@hotel.com',
     'a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2',
     '192.168.1.10', 'Chrome/124 Linux',
     NOW() + INTERVAL '8 hours', FALSE),
    ('recepcion@hotel.com',
     'b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3',
     '192.168.1.25', 'Firefox/125 Windows',
     NOW() + INTERVAL '4 hours', FALSE),
    ('agente@hotel.com',
     'c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4',
     '10.0.0.5',     'Safari/17 macOS',
     NOW() - INTERVAL '2 days', TRUE),                                        -- caso borde: sesión expirada e invalidada
    ('gerente@hotel.com',
     'd4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5',
     '0.0.0.0',       NULL,
     NOW() - INTERVAL '1 hour', FALSE);                                       -- caso borde: sesión expirada no invalidada (limpieza pendiente)
