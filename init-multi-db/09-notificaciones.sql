/* ============================================================
   ARCHIVO: 09-notificaciones.sql
   Microservicio: notificaciones
   Responsabilidad: administrar plantillas, notificaciones y envios.
   ============================================================ */

\c notificaciones;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS envios CASCADE;
DROP TABLE IF EXISTS notificaciones CASCADE;
DROP TABLE IF EXISTS plantillas CASCADE;
DROP TABLE IF EXISTS proj_huespedes CASCADE;

-- 2. TABLAS DE PROYECCIÓN

CREATE TABLE proj_huespedes (
    email VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 3. TABLAS MAESTRAS

CREATE TABLE plantillas (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    canal VARCHAR(20) NOT NULL CHECK (canal IN ('EMAIL','SMS','PUSH','WHATSAPP')),
    asunto VARCHAR(200),
    cuerpo VARCHAR(1000) NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE notificaciones (
    id SERIAL PRIMARY KEY,
    codigo_plantilla VARCHAR(50) NOT NULL REFERENCES plantillas(codigo) ON UPDATE CASCADE,
    email_huesped VARCHAR(120) NOT NULL REFERENCES proj_huespedes(email),
    evento_origen VARCHAR(80) NOT NULL,
    payload_json VARCHAR(500),
    creado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE envios (
    id SERIAL PRIMARY KEY,
    notificacion_id INTEGER NOT NULL UNIQUE REFERENCES notificaciones(id) ON DELETE CASCADE,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
        CHECK (estado IN ('PENDIENTE','ENVIADO','FALLIDO','RECHAZADO')),
    intentos INTEGER NOT NULL DEFAULT 0,
    enviado_en DATE,
    error_msg VARCHAR(255)
);

CREATE INDEX idx_proj_huespedes_nombre_completo ON proj_huespedes(nombre_completo);

CREATE INDEX idx_plantillas_codigo ON plantillas(codigo);
CREATE INDEX idx_plantillas_canal ON plantillas(canal);
CREATE INDEX idx_plantillas_activa ON plantillas(activa);

CREATE INDEX idx_notificaciones_codigo_plantilla ON notificaciones(codigo_plantilla);
CREATE INDEX idx_notificaciones_email_huesped ON notificaciones(email_huesped);
CREATE INDEX idx_notificaciones_evento_origen ON notificaciones(evento_origen);
CREATE INDEX idx_notificaciones_creado_en ON notificaciones(creado_en);

CREATE INDEX idx_envios_notificacion_id ON envios(notificacion_id);
CREATE INDEX idx_envios_estado ON envios(estado);
CREATE INDEX idx_envios_enviado_en ON envios(enviado_en);

-- 4. DATOS DE PRUEBA

INSERT INTO proj_huespedes (email, nombre_completo) VALUES
('ana.garcia@email.com', 'Ana García López'),
('carlos.m@email.com', 'Carlos Martínez Ruiz'),
('borde@test.com', 'Usuario Borde'),
('empresa@corp.com', 'Reserva Corporativa SA');

INSERT INTO plantillas (codigo, canal, asunto, cuerpo, activa) VALUES
('CONFIRMACION_RESERVA', 'EMAIL', 'Confirmación de su reserva {{codigo_reserva}}', 'Estimado/a {{nombre}}, su reserva {{codigo_reserva}} para el {{fecha_entrada}} ha sido confirmada.', TRUE),
('BIENVENIDA_CHECKIN', 'SMS', NULL, 'Bienvenido/a {{nombre}}! Su habitacion {{habitacion}} esta lista. Disfrute su estadia.', TRUE),
('RECORDATORIO_CHECKOUT', 'PUSH', NULL, '{{nombre}}, recuerde que su checkout es manana a las 12:00. Necesita mas dias?', TRUE),
('FACTURA_DISPONIBLE', 'EMAIL', 'Su factura {{numero_factura}} esta disponible', 'Adjuntamos su factura por un total de ${{total_usd}}. Gracias por hospedarse con nosotros!', TRUE),
('OBSOLETA_WHATSAPP', 'WHATSAPP', NULL, 'Plantilla obsoleta', FALSE);

INSERT INTO notificaciones (codigo_plantilla, email_huesped, evento_origen, payload_json) VALUES
('CONFIRMACION_RESERVA', 'ana.garcia@email.com', 'RESERVA_CONFIRMADA', '{"nombre":"Ana García","codigo_reserva":"RES-20240601-0001","fecha_entrada":"2024-06-01"}'),
('BIENVENIDA_CHECKIN', 'ana.garcia@email.com', 'CHECKIN_COMPLETADO', '{"nombre":"Ana García","habitacion":"101"}'),
('RECORDATORIO_CHECKOUT', 'carlos.m@email.com', 'CHECKOUT_PROXIMO', '{"nombre":"Carlos Martínez"}'),
('FACTURA_DISPONIBLE', 'empresa@corp.com', 'CHECKOUT_COMPLETADO', '{"numero_factura":"FAC-2024-00004","total_usd":"0.00"}'),
('CONFIRMACION_RESERVA', 'borde@test.com', 'RESERVA_CONFIRMADA', NULL);

INSERT INTO envios (notificacion_id, estado, intentos, enviado_en, error_msg) VALUES
(1, 'ENVIADO', 1, '2024-06-01', NULL),
(2, 'ENVIADO', 1, '2024-06-01', NULL),
(3, 'FALLIDO', 3, NULL, 'Numero de telefono invalido'),
(4, 'PENDIENTE', 0, NULL, NULL),
(5, 'RECHAZADO', 1, NULL, 'Email no valido para huesped borde@test.com');
