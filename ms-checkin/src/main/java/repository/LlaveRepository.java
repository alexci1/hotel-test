package cl.hilton.checkin.repository;

import cl.hilton.checkin.model.Llave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LlaveRepository extends JpaRepository<Llave, Long> {

    Optional<Llave> findByCodigoLlave(String codigoLlave);

    boolean existsByCodigoLlave(String codigoLlave);

    List<Llave> findByNumeroHabitacion(String numeroHabitacion);

    List<Llave> findByActiva(Boolean activa);

    List<Llave> findByReservaCodigoReserva(String codigoReserva);
}