-- ============================================================
-- 08-inventario.sql
-- Microservicio: Inventario
-- Base de datos: inventario
-- Tablas maestras : producto, movimiento, minibar
-- Tablas proyección (recibidas vía Kafka):
--   · proj_habitacion ← publicada por microservicio habitaciones
-- ============================================================

\c inventario

-- ------------------------------------------------------------
-- 1. ELIMINACIÓN en jerarquía inversa
-- ------------------------------------------------------------
DROP TABLE IF EXISTS minibar          CASCADE;
DROP TABLE IF EXISTS movimiento       CASCADE;
DROP TABLE IF EXISTS producto         CASCADE;
DROP TABLE IF EXISTS proj_habitacion  CASCADE;

-- ------------------------------------------------------------
-- 2. TABLAS DE PROYECCIÓN
-- ------------------------------------------------------------

CREATE TABLE proj_habitacion (
    numero_habitacion VARCHAR(10)  PRIMARY KEY,
    tipo              VARCHAR(40)  NOT NULL,
    actualizado_en    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE proj_habitacion IS 'Réplica mínima de habitaciones recibida vía Kafka. Solo lectura.';

-- ------------------------------------------------------------
-- 3. TABLAS MAESTRAS
-- ------------------------------------------------------------

-- Catálogo de productos del inventario del hotel
CREATE TABLE producto (
    id               SERIAL        PRIMARY KEY,
    codigo_producto  VARCHAR(30)   NOT NULL UNIQUE,         -- SKU interno
    nombre           VARCHAR(100)  NOT NULL,
    categoria        VARCHAR(40)   NOT NULL
        CHECK (categoria IN ('AMENITY','MINIBAR','LIMPIEZA','LENCERIA','MANTENIMIENTO','OTRO')),
    stock_actual     INTEGER       NOT NULL DEFAULT 0 CHECK (stock_actual >= 0),
    stock_minimo     INTEGER       NOT NULL DEFAULT 5 CHECK (stock_minimo >= 0),
    unidad           VARCHAR(20)   NOT NULL DEFAULT 'UNIDAD'
);
COMMENT ON TABLE producto IS 'Catálogo maestro de productos del inventario hotelero.';
CREATE INDEX idx_prod_categoria ON producto(categoria);
CREATE INDEX idx_prod_stock     ON producto(stock_actual);

-- Movimientos de inventario (entradas y salidas)
CREATE TABLE movimiento (
    id              SERIAL        PRIMARY KEY,
    codigo_producto VARCHAR(30)   NOT NULL
        REFERENCES producto(codigo_producto) ON UPDATE CASCADE,
    tipo            VARCHAR(20)   NOT NULL
        CHECK (tipo IN ('ENTRADA','SALIDA','AJUSTE','DEVOLUCION')),
    cantidad        INTEGER       NOT NULL CHECK (cantidad != 0),  -- negativo para salidas manuales
    motivo          VARCHAR(100),
    registrado_por  VARCHAR(120)  NOT NULL,
    registrado_en   TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE movimiento IS 'Trazabilidad de cada movimiento de stock.';
CREATE INDEX idx_mov_producto ON movimiento(codigo_producto);
CREATE INDEX idx_mov_tipo     ON movimiento(tipo);
CREATE INDEX idx_mov_fecha    ON movimiento(registrado_en);

-- Minibar por habitación: stock actual de productos
CREATE TABLE minibar (
    id                SERIAL       PRIMARY KEY,
    numero_habitacion VARCHAR(10)  NOT NULL
        REFERENCES proj_habitacion(numero_habitacion),
    codigo_producto   VARCHAR(30)  NOT NULL
        REFERENCES producto(codigo_producto) ON UPDATE CASCADE,
    cantidad          SMALLINT     NOT NULL DEFAULT 0 CHECK (cantidad >= 0),
    precio_unit_usd   NUMERIC(8,2) NOT NULL CHECK (precio_unit_usd >= 0),
    CONSTRAINT uq_minibar UNIQUE (numero_habitacion, codigo_producto)
);
COMMENT ON TABLE minibar IS 'Stock del minibar por habitación. Publicado en Kafka topic: inventario.events';
CREATE INDEX idx_minibar_habitacion ON minibar(numero_habitacion);

-- ------------------------------------------------------------
-- 4. DATOS DE PRUEBA
-- ------------------------------------------------------------

INSERT INTO proj_habitacion (numero_habitacion, tipo) VALUES
    ('101', 'SIMPLE'),
    ('202', 'DOBLE'),
    ('303', 'SUITE'),
    ('PH1', 'SUITE');

INSERT INTO producto (codigo_producto, nombre, categoria, stock_actual, stock_minimo, unidad) VALUES
    ('AME-SHAMPOO-100', 'Shampoo 100ml',             'AMENITY',      150, 30, 'UNIDAD'),
    ('AME-JABON-40',    'Jabón de tocador 40g',       'AMENITY',       80, 20, 'UNIDAD'),
    ('MIN-AGUA-500',    'Agua mineral 500ml',          'MINIBAR',      200, 50, 'BOTELLA'),
    ('MIN-VINO-750',    'Vino tinto reserva 750ml',    'MINIBAR',       40, 10, 'BOTELLA'),
    ('MIN-NUTS',        'Maní salado 50g',             'MINIBAR',      120, 30, 'BOLSA'),
    ('LIM-DETERGENTE',  'Detergente multiuso 1L',      'LIMPIEZA',      35,  5, 'LITRO'),
    ('LEN-SABANA-K',    'Sábana King size',            'LENCERIA',      60, 10, 'UNIDAD'),
    ('BORDE-SIN-STOCK', 'Producto sin stock',          'OTRO',           0,  5, 'UNIDAD'); -- caso borde: stock 0

INSERT INTO movimiento (codigo_producto, tipo, cantidad, motivo, registrado_por) VALUES
    ('MIN-AGUA-500',    'ENTRADA',    100, 'Reposición semanal',         'bodega@hotel.com'),
    ('MIN-VINO-750',    'ENTRADA',     20, 'Pedido proveedor',           'bodega@hotel.com'),
    ('AME-SHAMPOO-100', 'SALIDA',     -10, 'Reposición habitaciones',    'housekeeping@hotel.com'),
    ('LEN-SABANA-K',    'AJUSTE',      -3, 'Baja por deterioro',         'supervisor@hotel.com'),  -- caso borde: ajuste negativo
    ('BORDE-SIN-STOCK', 'SALIDA',      -1, 'Uso sin registro previo',    'bodega@hotel.com'),      -- caso borde: sin stock
    ('MIN-NUTS',        'DEVOLUCION',   5, 'Devolución minibar checkout','recepcion@hotel.com');

INSERT INTO minibar (numero_habitacion, codigo_producto, cantidad, precio_unit_usd) VALUES
    ('101', 'MIN-AGUA-500', 4,  2.50),
    ('101', 'MIN-NUTS',     2,  3.00),
    ('202', 'MIN-AGUA-500', 4,  2.50),
    ('202', 'MIN-VINO-750', 1, 18.00),
    ('303', 'MIN-AGUA-500', 6,  2.50),
    ('303', 'MIN-VINO-750', 2, 18.00),
    ('303', 'MIN-NUTS',     4,  3.00),
    ('PH1', 'MIN-AGUA-500', 6,  2.50),
    ('PH1', 'MIN-VINO-750', 3, 18.00),
    ('303', 'AME-SHAMPOO-100', 0, 0.00); -- caso borde: producto agotado en minibar
