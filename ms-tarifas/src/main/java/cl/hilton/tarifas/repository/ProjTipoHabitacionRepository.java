package cl.hilton.tarifas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.tarifas.model.ProjTipoHabitacion;

@Repository
public interface ProjTipoHabitacionRepository extends JpaRepository<ProjTipoHabitacion, String> {

    Optional<ProjTipoHabitacion> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<ProjTipoHabitacion> findByCapacidadMax(Integer capacidadMax);

    List<ProjTipoHabitacion> findByCapacidadMaxGreaterThanEqual(Integer capacidadMax);

    List<ProjTipoHabitacion> findByDescripcionContainingIgnoreCase(String descripcion);

    List<ProjTipoHabitacion> findByActualizadoEn(LocalDate actualizadoEn);
}
