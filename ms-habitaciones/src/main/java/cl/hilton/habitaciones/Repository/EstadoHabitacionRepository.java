package cl.hilton.habitaciones.repository;

import cl.hilton.habitaciones.model.EstadoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoHabitacionRepository extends JpaRepository<EstadoHabitacion, Long> {

    Optional<EstadoHabitacion> findByHabitacionNumeroHabitacion(String numeroHabitacion);

    boolean existsByHabitacionNumeroHabitacion(String numeroHabitacion);

    List<EstadoHabitacion> findByEstado(String estado);
}