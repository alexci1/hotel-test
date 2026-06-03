package cl.hilton.inventario.repository;

import cl.hilton.inventario.model.MiniBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MinibarRepository extends JpaRepository<MiniBar, Long> {

    List<MiniBar> findByHabitacionNumeroHabitacion(String numeroHabitacion);

    List<MiniBar> findByProductoCodigoProducto(String codigoProducto);

    Optional<MiniBar> findByHabitacionNumeroHabitacionAndProductoCodigoProducto(
            String numeroHabitacion,
            String codigoProducto
    );

    boolean existsByHabitacionNumeroHabitacionAndProductoCodigoProducto(
            String numeroHabitacion,
            String codigoProducto
    );

    List<MiniBar> findByCantidad(Integer cantidad);

    List<MiniBar> findByCantidadGreaterThan(Integer cantidad);

    List<MiniBar> findByPrecioUnitUsdGreaterThan(Integer precioUnitUsd);
}
