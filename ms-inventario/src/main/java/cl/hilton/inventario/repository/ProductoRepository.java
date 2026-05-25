package cl.hilton.inventario.repository;

import cl.hilton.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigoProducto(String codigoProducto);

    boolean existsByCodigoProducto(String codigoProducto);

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByUnidad(String unidad);

    List<Producto> findByStockActualLessThanEqual(Integer stockActual);

    List<Producto> findByStockActualLessThan(Integer stockActual);

    List<Producto> findByStockActualGreaterThan(Integer stockActual);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}