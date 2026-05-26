package cl.hilton.pagos.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.pagos.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByNumeroFactura(String numeroFactura);

    boolean existsByNumeroFactura(String numeroFactura);

    Optional<Factura> findByReservaCodigoReserva(String codigoReserva);

    boolean existsByReservaCodigoReserva(String codigoReserva);

    List<Factura> findByHuespedEmail(String emailHuesped);

    List<Factura> findByEstado(String estado);

    List<Factura> findByEmitidaEn(LocalDate emitidaEn);
}
