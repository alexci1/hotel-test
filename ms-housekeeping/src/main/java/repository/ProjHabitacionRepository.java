package cl.hilton.housekeeping.repository;

import cl.hilton.housekeeping.model.ProjHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjHabitacionRepository extends JpaRepository<ProjHabitacion, String> {

    List<ProjHabitacion> findByTipo(String tipo);

    List<ProjHabitacion> findByPiso(Long piso);
}