package cl.hilton.reservas.repository;

import cl.hilton.reservas.model.Cancelacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CancelacionRepository extends JpaRepository<Cancelacion, Long> {

    Optional<Cancelacion> findByReservaCodigoReserva(String codigoReserva);

}