package cl.hilton.pagos.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.pagos.model.ProjReserva;

@Repository
public interface ProjReservaRepository extends JpaRepository<ProjReserva, String> {

    Optional<ProjReserva> findByCodigoReserva(String codigoReserva);

    boolean existsByCodigoReserva(String codigoReserva);

    List<ProjReserva> findByEmailHuesped(String emailHuesped);

    List<ProjReserva> findByNumeroHabitacion(String numeroHabitacion);

    List<ProjReserva> findByFechaEntrada(LocalDate fechaEntrada);

    List<ProjReserva> findByFechaSalida(LocalDate fechaSalida);
}
