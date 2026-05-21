package cl.hilton.pagos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.pagos.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago,Long>{
    Optional<Pago> findByNumeroFactura(String factura);
    boolean existsByNumeroFactura(String factura);
}
