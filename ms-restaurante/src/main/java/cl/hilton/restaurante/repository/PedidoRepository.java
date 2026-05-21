package cl.hilton.restaurante.repository;

import cl.hilton.restaurante.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Optional<Pedido> findByNumeroPedido(String numeroPedido);

    boolean existsByNumeroPedido(String numeroPedido);

    List<Pedido> findByEstado(String estado);

    List<Pedido> findByMesaNumeroMesa(String numeroMesa);

    List<Pedido> findByHuespedEmail(String emailHuesped);

    List<Pedido> findByCreadoEnBetween(OffsetDateTime desde, OffsetDateTime hasta);

    List<Pedido> findByMesaNumeroMesaAndEstado(String numeroMesa, String estado);

    List<Pedido> findByHuespedEmailAndEstado(String emailHuesped, String estado);
}
