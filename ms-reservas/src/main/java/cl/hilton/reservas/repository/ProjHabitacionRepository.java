package cl.hilton.reservas.repository;

import cl.hilton.reservas.model.ProjHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjHabitacionRepository extends JpaRepository<ProjHabitacion, String> {

}