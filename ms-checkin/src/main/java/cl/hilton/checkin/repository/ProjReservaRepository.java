package cl.hilton.checkin.repository;

import cl.hilton.checkin.model.ProjReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjReservaRepository extends JpaRepository<ProjReserva, String> {

    List<ProjReserva> findByEmailHuesped(String emailHuesped);

    List<ProjReserva> findByNumeroHabitacion(String numeroHabitacion);

    List<ProjReserva> findByEstado(String estado);
}