package cl.hilton.checkin.repository;

import cl.hilton.checkin.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Optional<Checkout> findByReservaCodigoReserva(String codigoReserva);
}