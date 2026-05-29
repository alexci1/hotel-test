package cl.hilton.inventario.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.inventario.model.ProjHabitacion;

@Repository
public interface ProjHabitacionRepository extends JpaRepository<ProjHabitacion, String> {

    Optional<ProjHabitacion> findByNumeroHabitacion(String numeroHabitacion);

    boolean existsByNumeroHabitacion(String numeroHabitacion);

    List<ProjHabitacion> findByTipo(String tipo);

    List<ProjHabitacion> findByActualizadoEn(LocalDate actualizadoEn);
}
