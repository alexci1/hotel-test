package cl.hilton.restaurante.repository;



import cl.hilton.restaurante.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    List<ItemPedido> findByPedidoNumeroPedido(String numeroPedido);

    List<ItemPedido> findByNombreProductoContainingIgnoreCase(String nombreProducto);

    List<ItemPedido> findByCantidadGreaterThan(Short cantidad);
}

