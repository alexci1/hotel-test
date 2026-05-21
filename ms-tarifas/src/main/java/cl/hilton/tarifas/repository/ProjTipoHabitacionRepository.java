package cl.hilton.tarifas.repository;

import cl.hilton.tarifas.model.ProjTipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjTipoHabitacionRepository extends JpaRepository<ProjTipoHabitacion, String> {

    Optional<ProjTipoHabitacion> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<ProjTipoHabitacion> findByCapacidadMax(Short capacidadMax);

    List<ProjTipoHabitacion> findByDescripcionContainingIgnoreCase(String descripcion);

}