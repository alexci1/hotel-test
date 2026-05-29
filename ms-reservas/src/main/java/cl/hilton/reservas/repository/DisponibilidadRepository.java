package cl.hilton.reservas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.reservas.model.Disponibilidad;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    Optional<Disponibilidad> findByHabitacionNumeroHabitacionAndFecha(String numeroHabitacion, LocalDate fecha);

    boolean existsByHabitacionNumeroHabitacionAndFecha(String numeroHabitacion, LocalDate fecha);

    List<Disponibilidad> findByHabitacionNumeroHabitacion(String numeroHabitacion);

    List<Disponibilidad> findByFecha(LocalDate fecha);

    List<Disponibilidad> findByFechaBetween(LocalDate desde, LocalDate hasta);

    List<Disponibilidad> findByDisponible(Boolean disponible);
}
