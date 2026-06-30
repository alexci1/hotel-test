/* ============================================================
   ARCHIVO: 12-autenticacion.sql
   Microservicio: autenticacion
   Responsabilidad: administrar roles, usuarios autenticables y sesiones activas.
   Kafka publica eventos AuthUsuarioCreado/AuthUsuarioActualizado/AuthSesionCreada
   para que otros microservicios puedan sincronizar permisos si lo necesitan.
   ============================================================ */

\c autenticacion;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS sesiones;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;

-- 2. TABLAS MAESTRAS

CREATE TABLE roles (
    id          SERIAL       PRIMARY KEY,
    codigo      VARCHAR(30)  UNIQUE NOT NULL,
    descripcion VARCHAR(100),
    activo      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE usuarios (
    id              SERIAL       PRIMARY KEY,
    email           VARCHAR(120) UNIQUE NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    rol_codigo      VARCHAR(30)  NOT NULL REFERENCES roles(codigo) ON UPDATE CASCADE,
    hash_password   VARCHAR(255) NOT NULL,
    activo          BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en       DATE         NOT NULL DEFAULT CURRENT_DATE,
    ultimo_acceso   DATE
);

CREATE TABLE sesiones (
    id              SERIAL       PRIMARY KEY,
    usuario_email   VARCHAR(120) UNIQUE NOT NULL REFERENCES usuarios(email) ON UPDATE CASCADE ON DELETE CASCADE,
    token_hash      VARCHAR(255) UNIQUE NOT NULL,
    ip_origen       VARCHAR(45)  NOT NULL,
    user_agent      VARCHAR(250),
    expira_en       DATE         NOT NULL,
    creada_en       DATE         NOT NULL DEFAULT CURRENT_DATE,
    invalidada      BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_usuarios_rol_codigo ON usuarios(rol_codigo);
CREATE INDEX idx_usuarios_activo ON usuarios(activo);

CREATE INDEX idx_sesiones_usuario_email ON sesiones(usuario_email);
CREATE INDEX idx_sesiones_expira_en ON sesiones(expira_en);
CREATE INDEX idx_sesiones_activas ON sesiones(invalidada) WHERE NOT invalidada;

-- 3. DATOS DE PRUEBA

INSERT INTO roles (codigo, descripcion, activo) VALUES
('ADMIN',        'Administrador del sistema con acceso total',          TRUE),
('GERENCIA',     'Gerente de hotel: acceso a reportes y configuración', TRUE),
('RECEPCION',    'Recepcionista: reservas, checkin, checkout',          TRUE),
('HOUSEKEEPING', 'Camarero/a: asignaciones de limpieza',                TRUE),
('RESTAURANTE',  'Personal de restaurante: pedidos y mesas',            TRUE),
('BODEGA',       'Encargado de inventario',                             TRUE),
('SOLO_LECTURA', 'Acceso de solo lectura para auditoría',               TRUE),
('INACTIVO',     'Rol dado de baja',                                    FALSE);

--La password es: Biblio@2026

INSERT INTO usuarios (email, nombre_completo, rol_codigo, hash_password, activo, ultimo_acceso) VALUES
('admin@hotel.com',      'Administrador Principal', 'ADMIN',
 '$2b$10$1hnbaMR7iTsdn3D0gG5Q8eUw5aSh9O2at2e4u1iAlzdhD6m4dzVZO', TRUE, '2026-05-19'),

('gerente@hotel.com',    'María Fernández', 'GERENCIA',
 '$2b$10$1hnbaMR7iTsdn3D0gG5Q8eUw5aSh9O2at2e4u1iAlzdhD6m4dzVZO', TRUE, '2026-05-19'),

('recepcion@hotel.com',  'Juan Recepcionista', 'RECEPCION',
 '$2b$10$1hnbaMR7iTsdn3D0gG5Q8eUw5aSh9O2at2e4u1iAlzdhD6m4dzVZO', TRUE, '2026-05-19'),

('supervisor@hotel.com', 'Supervisora HK', 'HOUSEKEEPING',
 '$2b$10$1hnbaMR7iTsdn3D0gG5Q8eUw5aSh9O2at2e4u1iAlzdhD6m4dzVZO', TRUE, '2026-05-19'),

('agente@hotel.com',     'Agente de Ventas', 'RECEPCION',
 '$2b$10$1hnbaMR7iTsdn3D0gG5Q8eUw5aSh9O2at2e4u1iAlzdhD6m4dzVZO', TRUE, '2026-05-16'),

('bodega@hotel.com',     'Encargado Bodega', 'BODEGA',
 '$2b$10$1hnbaMR7iTsdn3D0gG5Q8eUw5aSh9O2at2e4u1iAlzdhD6m4dzVZO', TRUE, NULL),

('baja@hotel.com',       'Empleado Dado de Baja', 'SOLO_LECTURA',
 '$2b$10$1hnbaMR7iTsdn3D0gG5Q8eUw5aSh9O2at2e4u1iAlzdhD6m4dzVZO', FALSE, '2023-12-01');

INSERT INTO sesiones (usuario_email, token_hash, ip_origen, user_agent, expira_en, invalidada) VALUES
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
 '10.0.0.5', 'Safari/17 macOS',
 '2026-05-17', TRUE),

('gerente@hotel.com',
 'd4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5',
 '0.0.0.0', NULL,
 '2026-05-18', FALSE);