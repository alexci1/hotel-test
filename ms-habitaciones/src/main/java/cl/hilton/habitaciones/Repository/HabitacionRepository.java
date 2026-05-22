package cl.hilton.habitaciones.repository;

import cl.hilton.habitaciones.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNumeroHabitacion(String numeroHabitacion);

    boolean existsByNumeroHabitacion(String numeroHabitacion);

    List<Habitacion> findByPiso(Integer piso);

    List<Habitacion> findByActiva(Boolean activa);
}