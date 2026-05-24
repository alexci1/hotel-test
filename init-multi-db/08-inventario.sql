/* ============================================================
   ARCHIVO: 08-inventario.sql
   Microservicio: inventario
   Responsabilidad: administrar productos, movimientos y minibares.
   ============================================================ */

\c inventario;

-- 1. ELIMINACIÓN EN JERARQUÍA INVERSA
DROP TABLE IF EXISTS minibares CASCADE;
DROP TABLE IF EXISTS movimientos CASCADE;
DROP TABLE IF EXISTS productos CASCADE;
DROP TABLE IF EXISTS proj_habitaciones CASCADE;

-- 2. TABLAS DE PROYECCIÓN

CREATE TABLE proj_habitaciones (
    numero_habitacion VARCHAR(10) PRIMARY KEY,
    tipo VARCHAR(40) NOT NULL,
    actualizado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

-- 3. TABLAS MAESTRAS

CREATE TABLE productos (
    id SERIAL PRIMARY KEY,
    codigo_producto VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(40) NOT NULL
        CHECK (categoria IN ('AMENITY','MINIBAR','LIMPIEZA','LENCERIA','MANTENIMIENTO','OTRO')),
    stock_actual INTEGER NOT NULL DEFAULT 0 CHECK (stock_actual >= 0),
    stock_minimo INTEGER NOT NULL DEFAULT 5 CHECK (stock_minimo >= 0),
    unidad VARCHAR(20) NOT NULL DEFAULT 'UNIDAD'
);

CREATE TABLE movimientos (
    id SERIAL PRIMARY KEY,
    codigo_producto VARCHAR(30) NOT NULL REFERENCES productos(codigo_producto) ON UPDATE CASCADE,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('ENTRADA','SALIDA','AJUSTE','DEVOLUCION')),
    cantidad INTEGER NOT NULL CHECK (cantidad != 0),
    motivo VARCHAR(100),
    registrado_por VARCHAR(120) NOT NULL,
    registrado_en DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE minibares (
    id SERIAL PRIMARY KEY,
    numero_habitacion VARCHAR(10) NOT NULL REFERENCES proj_habitaciones(numero_habitacion),
    codigo_producto VARCHAR(30) NOT NULL REFERENCES productos(codigo_producto) ON UPDATE CASCADE,
    cantidad INTEGER NOT NULL DEFAULT 0 CHECK (cantidad >= 0),
    precio_unit_usd INTEGER NOT NULL CHECK (precio_unit_usd >= 0),
    CONSTRAINT uq_minibares UNIQUE (numero_habitacion, codigo_producto)
);

CREATE INDEX idx_proj_habitaciones_tipo ON proj_habitaciones(tipo);

CREATE INDEX idx_productos_codigo_producto ON productos(codigo_producto);
CREATE INDEX idx_productos_categoria ON productos(categoria);
CREATE INDEX idx_productos_stock_actual ON productos(stock_actual);

CREATE INDEX idx_movimientos_codigo_producto ON movimientos(codigo_producto);
CREATE INDEX idx_movimientos_tipo ON movimientos(tipo);
CREATE INDEX idx_movimientos_registrado_en ON movimientos(registrado_en);

CREATE INDEX idx_minibares_numero_habitacion ON minibares(numero_habitacion);
CREATE INDEX idx_minibares_codigo_producto ON minibares(codigo_producto);

-- 4. DATOS DE PRUEBA

INSERT INTO proj_habitaciones (numero_habitacion, tipo) VALUES
('101', 'SIMPLE'),
('202', 'DOBLE'),
('303', 'SUITE'),
('PH1', 'SUITE');

INSERT INTO productos (codigo_producto, nombre, categoria, stock_actual, stock_minimo, unidad) VALUES
('AME-SHAMPOO-100', 'Shampoo 100ml', 'AMENITY', 150, 30, 'UNIDAD'),
('AME-JABON-40', 'Jabon de tocador 40g', 'AMENITY', 80, 20, 'UNIDAD'),
('MIN-AGUA-500', 'Agua mineral 500ml', 'MINIBAR', 200, 50, 'BOTELLA'),
('MIN-VINO-750', 'Vino tinto reserva 750ml', 'MINIBAR', 40, 10, 'BOTELLA'),
('MIN-NUTS', 'Mani salado 50g', 'MINIBAR', 120, 30, 'BOLSA'),
('LIM-DETERGENTE', 'Detergente multiuso 1L', 'LIMPIEZA', 35, 5, 'LITRO'),
('LEN-SABANA-K', 'Sabana King size', 'LENCERIA', 60, 10, 'UNIDAD'),
('BORDE-SIN-STOCK', 'Producto sin stock', 'OTRO', 0, 5, 'UNIDAD');

INSERT INTO movimientos (codigo_producto, tipo, cantidad, motivo, registrado_por) VALUES
('MIN-AGUA-500', 'ENTRADA', 100, 'Reposicion semanal', 'bodega@hotel.com'),
('MIN-VINO-750', 'ENTRADA', 20, 'Pedido proveedor', 'bodega@hotel.com'),
('AME-SHAMPOO-100', 'SALIDA', -10, 'Reposicion habitaciones', 'housekeeping@hotel.com'),
('LEN-SABANA-K', 'AJUSTE', -3, 'Baja por deterioro', 'supervisor@hotel.com'),
('BORDE-SIN-STOCK', 'SALIDA', -1, 'Uso sin registro previo', 'bodega@hotel.com'),
('MIN-NUTS', 'DEVOLUCION', 5, 'Devolucion minibar checkout', 'recepcion@hotel.com');

INSERT INTO minibares (numero_habitacion, codigo_producto, cantidad, precio_unit_usd) VALUES
('101', 'MIN-AGUA-500', 4, 2),
('101', 'MIN-NUTS', 2, 3),
('202', 'MIN-AGUA-500', 4, 2),
('202', 'MIN-VINO-750', 1, 18),
('303', 'MIN-AGUA-500', 6, 2),
('303', 'MIN-VINO-750', 2, 18),
('303', 'MIN-NUTS', 4, 3),
('PH1', 'MIN-AGUA-500', 6, 2),
('PH1', 'MIN-VINO-750', 3, 18),
('303', 'AME-SHAMPOO-100', 0, 0);
