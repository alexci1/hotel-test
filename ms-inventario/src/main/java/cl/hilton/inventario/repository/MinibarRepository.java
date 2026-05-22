package cl.hilton.inventario.repository;

import cl.hilton.inventario.model.Minibar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MinibarRepository extends JpaRepository<Minibar, Long> {

    List<Minibar> findByHabitacionNumeroHabitacion(String numeroHabitacion);

    List<Minibar> findByProductoCodigoProducto(String codigoProducto);

    Optional<Minibar> findByHabitacionNumeroHabitacionAndProductoCodigoProducto(
            String numeroHabitacion,
            String codigoProducto
    );

    boolean existsByHabitacionNumeroHabitacionAndProductoCodigoProducto(
            String numeroHabitacion,
            String codigoProducto
    );

    List<Minibar> findByCantidadGreaterThan(Short cantidad);

    List<Minibar> findByCantidadEquals(Short cantidad);
}