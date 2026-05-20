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
    codigo      VARCHAR(30)  NOT NULL UNIQUE,
    descripcion VARCHAR(100),
    activo      BOOLEAN      NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE rol IS 'Roles del sistema para control de acceso basado en roles (RBAC). Publicado en Kafka topic: auth.events';

-- Usuarios del sistema (empleados o integraciones)
CREATE TABLE usuario (
    id              SERIAL       PRIMARY KEY,
    email           VARCHAR(120) NOT NULL UNIQUE,
    nombre_completo VARCHAR(100) NOT NULL,
    codigo_rol      VARCHAR(30)  NOT NULL
        REFERENCES rol(codigo) ON UPDATE CASCADE,
    hash_password   VARCHAR(255) NOT NULL,
    activo          BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en       DATE         NOT NULL DEFAULT CURRENT_DATE,
    ultimo_acceso   DATE
);
COMMENT ON TABLE usuario IS 'Usuarios del sistema. Nunca almacenar password en texto plano.';
CREATE INDEX idx_usuario_rol    ON usuario(codigo_rol);
CREATE INDEX idx_usuario_activo ON usuario(activo);

-- Sesiones activas (tokens JWT o de sesión)
CREATE TABLE sesion (
    id              SERIAL       PRIMARY KEY,
    email_usuario   VARCHAR(120) NOT NULL
        REFERENCES usuario(email) ON UPDATE CASCADE ON DELETE CASCADE,
    token_hash      VARCHAR(255) NOT NULL UNIQUE,
    ip_origen       VARCHAR(45)  NOT NULL,
    user_agent      VARCHAR(250),
    expira_en       DATE         NOT NULL,
    creada_en       DATE         NOT NULL DEFAULT CURRENT_DATE,
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
    ('INACTIVO',        'Rol dado de baja',                                        FALSE);

INSERT INTO usuario (email, nombre_completo, codigo_rol, hash_password, activo, ultimo_acceso) VALUES
    ('admin@hotel.com',       'Administrador Principal',    'ADMIN',
     '$2b$12$ADMINHASHADMINHASHADMINHASHADMINHASHADMIN', TRUE, '2026-05-19'),
    ('gerente@hotel.com',     'María Fernández',            'GERENCIA',
     '$2b$12$GERENTEGERENTEGERENTEGERENTEGERENTEGERENT', TRUE, '2026-05-19'),
    ('recepcion@hotel.com',   'Juan Recepcionista',         'RECEPCION',
     '$2b$12$RECEPCIONHASHRECEPCIONHASHRECEPCIONHASHRE', TRUE, '2026-05-19'),
    ('supervisor@hotel.com',  'Supervisora HK',             'HOUSEKEEPING',
     '$2b$12$SUPERVHASHSUPERVHASHSUPERVHASHSUPERVHASH', TRUE, '2026-05-19'),
    ('agente@hotel.com',      'Agente de Ventas',           'RECEPCION',
     '$2b$12$AGENTEHASHAGENTEHASHAGENTEHASHAGENTEHASH', TRUE, '2026-05-16'),
    ('bodega@hotel.com',      'Encargado Bodega',           'BODEGA',
     '$2b$12$BODEGAHASHBODEGAHASHBODEGAHASHBODEGAHASH', TRUE, NULL),
    ('baja@hotel.com',        'Empleado Dado de Baja',      'SOLO_LECTURA',
     '$2b$12$BAJAHASHBAJAHASHBAJAHASHBAJAHASHBAJAHASH', FALSE, '2023-12-01');

INSERT INTO sesion (email_usuario, token_hash, ip_origen, user_agent, expira_en, invalidada) VALUES
    ('admin@hotel.com',
     'a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2',
     '192.168.1.10', 'Chrome/124 Linux',
     '2026-05-27', FALSE),
    ('recepcion@hotel.com',
     'b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3',
     '192.168.1.25', 'Firefox/125 Windows',
     '2026-05-23', FALSE),
    ('agente@hotel.com',
     'c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4',
     '10.0.0.5',     'Safari/17 macOS',
     '2026-05-17', TRUE),
    ('gerente@hotel.com',
     'd4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5',
     '0.0.0.0',       NULL,
     '2026-05-18', FALSE);