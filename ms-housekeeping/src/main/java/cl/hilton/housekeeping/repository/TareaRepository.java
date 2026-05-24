package cl.hilton.housekeeping.repository;

import cl.hilton.housekeeping.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    Optional<Tarea> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Tarea> findByActiva(Boolean activa);

    List<Tarea> findByDescripcionContainingIgnoreCase(String descripcion);
}