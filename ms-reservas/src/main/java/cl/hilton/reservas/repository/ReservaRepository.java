package cl.hilton.reservas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.reservas.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findByCodigoReserva(String codigoReserva);

    boolean existsByCodigoReserva(String codigoReserva);

    List<Reserva> findByHuespedEmail(String emailHuesped);

    List<Reserva> findByHabitacionNumeroHabitacion(String numeroHabitacion);

    List<Reserva> findByEstado(String estado);

    List<Reserva> findByFechaEntrada(LocalDate fechaEntrada);

    List<Reserva> findByFechaSalida(LocalDate fechaSalida);

    List<Reserva> findByFechaEntradaBetween(LocalDate desde, LocalDate hasta);
}
