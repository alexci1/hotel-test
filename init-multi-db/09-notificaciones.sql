-- ============================================================
-- 09-notificaciones.sql
-- Microservicio: Notificaciones
-- Base de datos: notificaciones
-- Tablas maestras : plantilla, notificacion, envio
-- Tablas proyección (recibidas vía Kafka):
--   · proj_huesped  ← publicada por microservicio huespedes
-- ============================================================

\c notificaciones

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS envio         CASCADE;
DROP TABLE IF EXISTS notificacion  CASCADE;
DROP TABLE IF EXISTS plantilla     CASCADE;
DROP TABLE IF EXISTS proj_huesped  CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS DE PROYECCIÓN
-- ------------------------------------------------------------

CREATE TABLE proj_huesped (
    email           VARCHAR(120) PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    actualizado_en  DATE         NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE proj_huesped IS 'Réplica mínima de huéspedes recibida vía Kafka. Solo lectura.';

-- ------------------------------------------------------------
-- 3. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Plantillas reutilizables de mensajes (email, SMS, push)
CREATE TABLE plantilla (
    id              SERIAL        PRIMARY KEY,
    codigo          VARCHAR(50)   NOT NULL UNIQUE,
    canal           VARCHAR(20)   NOT NULL
        CHECK (canal IN ('EMAIL','SMS','PUSH','WHATSAPP')),
    asunto          VARCHAR(200),
    cuerpo          VARCHAR(1000) NOT NULL,
    activa          BOOLEAN       NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE plantilla IS 'Plantillas de mensajes parametrizadas por canal.';
CREATE INDEX idx_plantilla_canal ON plantilla(canal);

-- Notificación generada por evento del sistema
CREATE TABLE notificacion (
    id               SERIAL        PRIMARY KEY,
    codigo_plantilla VARCHAR(50)   NOT NULL
        REFERENCES plantilla(codigo) ON UPDATE CASCADE,
    email_huesped    VARCHAR(120)  NOT NULL
        REFERENCES proj_huesped(email),
    evento_origen    VARCHAR(80)   NOT NULL,
    payload_json     VARCHAR(500),
    creado_en        DATE          NOT NULL DEFAULT CURRENT_DATE
);
COMMENT ON TABLE notificacion IS 'Cola de notificaciones a enviar. Generada por eventos Kafka.';
CREATE INDEX idx_noti_huesped ON notificacion(email_huesped);
CREATE INDEX idx_noti_evento  ON notificacion(evento_origen);

-- Registro del envío de cada notificación
CREATE TABLE envio (
    id               SERIAL       PRIMARY KEY,
    notificacion_id  INTEGER      NOT NULL UNIQUE
        REFERENCES notificacion(id) ON DELETE CASCADE,
    estado           VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE'
        CHECK (estado IN ('PENDIENTE','ENVIADO','FALLIDO','RECHAZADO')),
    intentos         SMALLINT     NOT NULL DEFAULT 0,
    enviado_en       DATE,
    error_msg        VARCHAR(255)
);
COMMENT ON TABLE envio IS 'Estado de entrega de cada notificación. Máx 3 intentos automáticos.';
CREATE INDEX idx_envio_estado ON envio(estado);

-- ------------------------------------------------------------
-- 4. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO proj_huesped (email, nombre_completo) VALUES
    ('ana.garcia@email.com', 'Ana García López'),
    ('carlos.m@email.com',   'Carlos Martínez Ruiz'),
    ('borde@test.com',       'Usuario Borde'),
    ('empresa@corp.com',     'Reserva Corporativa SA');

INSERT INTO plantilla (codigo, canal, asunto, cuerpo, activa) VALUES
    ('CONFIRMACION_RESERVA', 'EMAIL',
     'Confirmación de su reserva {{codigo_reserva}}',
     'Estimado/a {{nombre}}, su reserva {{codigo_reserva}} para el {{fecha_entrada}} ha sido confirmada.',
     TRUE),
    ('BIENVENIDA_CHECKIN', 'SMS',
     NULL,
     'Bienvenido/a {{nombre}}! Su habitación {{habitacion}} está lista. Disfrute su estadía.',
     TRUE),
    ('RECORDATORIO_CHECKOUT', 'PUSH',
     NULL,
     '{{nombre}}, recuerde que su checkout es mañana a las 12:00. ¿Necesita más días?',
     TRUE),
    ('FACTURA_DISPONIBLE', 'EMAIL',
     'Su factura {{numero_factura}} está disponible',
     'Adjuntamos su factura por un total de ${{total_usd}}. ¡Gracias por hospedarse con nosotros!',
     TRUE),
    ('OBSOLETA_WHATSAPP', 'WHATSAPP',
     NULL,
     'Plantilla obsoleta',
     FALSE);

INSERT INTO notificacion (codigo_plantilla, email_huesped, evento_origen, payload_json) VALUES
    ('CONFIRMACION_RESERVA', 'ana.garcia@email.com', 'RESERVA_CONFIRMADA',
        '{"nombre":"Ana García","codigo_reserva":"RES-20240601-0001","fecha_entrada":"2024-06-01"}'),
    ('BIENVENIDA_CHECKIN',   'ana.garcia@email.com', 'CHECKIN_COMPLETADO',
        '{"nombre":"Ana García","habitacion":"101"}'),
    ('RECORDATORIO_CHECKOUT','carlos.m@email.com',   'CHECKOUT_PROXIMO',
        '{"nombre":"Carlos Martínez"}'),
    ('FACTURA_DISPONIBLE',   'empresa@corp.com',     'CHECKOUT_COMPLETADO',
        '{"numero_factura":"FAC-2024-00004","total_usd":"0.00"}'),
    ('CONFIRMACION_RESERVA', 'borde@test.com',       'RESERVA_CONFIRMADA',
        NULL);

INSERT INTO envio (notificacion_id, estado, intentos, enviado_en, error_msg) VALUES
    (1, 'ENVIADO',   1, '2024-06-01', NULL),
    (2, 'ENVIADO',   1, '2024-06-01', NULL),
    (3, 'FALLIDO',   3, NULL, 'Número de teléfono inválido'),
    (4, 'PENDIENTE', 0, NULL, NULL),
    (5, 'RECHAZADO', 1, NULL, 'Email no válido para huésped borde@test.com');