package cl.hilton.inventario.repository;

import cl.hilton.inventario.model.ProjHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjHabitacionRepository extends JpaRepository<ProjHabitacion, String> {

    List<ProjHabitacion> findByTipo(String tipo);

    boolean existsByNumeroHabitacion(String numeroHabitacion);
}
