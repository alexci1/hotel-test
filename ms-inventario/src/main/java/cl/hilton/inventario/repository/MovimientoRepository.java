package cl.hilton.inventario.repository;

import cl.hilton.inventario.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByProductoCodigoProductoOrderByRegistradoEnDesc(String codigoProducto);

    List<Movimiento> findByTipo(String tipo);

    List<Movimiento> findByRegistradoPor(String registradoPor);

    List<Movimiento> findByRegistradoEnBetween(LocalDate desde, LocalDate hasta);

    List<Movimiento> findByCantidadGreaterThan(Integer cantidad);

    List<Movimiento> findByCantidadLessThan(Integer cantidad);
}