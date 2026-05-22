package cl.hilton.checkin.repository;

import cl.hilton.checkin.model.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    Optional<Checkin> findByReservaCodigoReserva(String codigoReserva);

    List<Checkin> findByHuespedEmail(String emailHuesped);

    List<Checkin> findByNumeroHabitacion(String numeroHabitacion);
}