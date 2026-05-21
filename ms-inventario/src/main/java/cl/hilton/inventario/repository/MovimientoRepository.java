package cl.hilton.inventario.repository;
import cl.triskledu.inventario.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    List<Movimiento> findByProductoCodigoProducto(String codigoProducto);

    List<Movimiento> findByProductoCodigoProductoOrderByRegistradoEnDesc(String codigoProducto);

    List<Movimiento> findByTipo(String tipo);

    List<Movimiento> findByRegistradoPor(String registradoPor);

    List<Movimiento> findByRegistradoEnBetween(OffsetDateTime desde, OffsetDateTime hasta);

    List<Movimiento> findByProductoCodigoProductoAndTipo(String codigoProducto, String tipo);

    List<Movimiento> findByCantidadLessThan(Integer cantidad);

    List<Movimiento> findByCantidadGreaterThan(Integer cantidad);
}

