package cl.hilton.habitaciones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.habitaciones.model.TipoHabitacion;

@Repository
public interface TipoHabitacionRepository extends JpaRepository<TipoHabitacion, Long> {

    Optional<TipoHabitacion> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<TipoHabitacion> findByActivo(Boolean activo);

    List<TipoHabitacion> findByCapacidadMax(Integer capacidadMax);

    List<TipoHabitacion> findByCapacidadMaxGreaterThanEqual(Integer capacidadMax);

    List<TipoHabitacion> findByDescripcionContainingIgnoreCase(String descripcion);
}
