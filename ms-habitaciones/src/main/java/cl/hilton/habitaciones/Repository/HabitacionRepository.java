package cl.hilton.habitaciones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.habitaciones.model.Habitacion;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNumeroHabitacion(String numeroHabitacion);

    boolean existsByNumeroHabitacion(String numeroHabitacion);

    List<Habitacion> findByPiso(Integer piso);

    List<Habitacion> findByActiva(Boolean activa);

    List<Habitacion> findByTipoHabitacionCodigo(String codigoTipo);

    List<Habitacion> findByTipoHabitacionCodigoAndActiva(String codigoTipo, Boolean activa);
}
