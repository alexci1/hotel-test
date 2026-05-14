-- ============================================================
-- 00-init.sql
-- Ecosistema Digital de Gestión Hotelera - SaaS Microservicios
-- Descripción: Crea todas las bases de datos independientes,
--              una por microservicio, si aún no existen.
-- Sincronización entre microservicios: Apache Kafka
-- ============================================================

SELECT 'CREATE DATABASE reservas'       WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'reservas')       \gexec
SELECT 'CREATE DATABASE habitaciones'   WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'habitaciones')   \gexec
SELECT 'CREATE DATABASE huespedes'      WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'huespedes')      \gexec
SELECT 'CREATE DATABASE checkin'        WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'checkin')        \gexec
SELECT 'CREATE DATABASE pagos'          WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'pagos')          \gexec
SELECT 'CREATE DATABASE housekeeping'   WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'housekeeping')   \gexec
SELECT 'CREATE DATABASE restaurante'    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'restaurante')    \gexec
SELECT 'CREATE DATABASE inventario'     WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'inventario')     \gexec
SELECT 'CREATE DATABASE notificaciones' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'notificaciones') \gexec
SELECT 'CREATE DATABASE tarifas'        WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'tarifas')        \gexec
SELECT 'CREATE DATABASE reportes'       WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'reportes')       \gexec
SELECT 'CREATE DATABASE autenticacion'  WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'autenticacion')  \gexec
