package cl.hilton.habitaciones.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.habitaciones.model.EstadoHabitacion;

@Repository
public interface EstadoHabitacionRepository extends JpaRepository<EstadoHabitacion, Long> {

    Optional<EstadoHabitacion> findByHabitacionNumeroHabitacion(String numeroHabitacion);

    boolean existsByHabitacionNumeroHabitacion(String numeroHabitacion);

    void deleteByHabitacionNumeroHabitacion(String numeroHabitacion);

    List<EstadoHabitacion> findByEstado(String estado);

    List<EstadoHabitacion> findByActualizadoEn(LocalDate actualizadoEn);
}
