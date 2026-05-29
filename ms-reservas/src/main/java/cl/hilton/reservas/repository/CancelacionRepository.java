package cl.hilton.reservas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.reservas.model.Cancelacion;

@Repository
public interface CancelacionRepository extends JpaRepository<Cancelacion, Long> {

    Optional<Cancelacion> findByReservaCodigoReserva(String codigoReserva);

    boolean existsByReservaCodigoReserva(String codigoReserva);

    List<Cancelacion> findByCanceladoEn(LocalDate canceladoEn);
}
