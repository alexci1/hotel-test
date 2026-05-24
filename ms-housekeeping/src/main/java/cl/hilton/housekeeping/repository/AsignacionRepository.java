package cl.hilton.housekeeping.repository;

import cl.hilton.housekeeping.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    List<Asignacion> findByHabitacionNumeroHabitacion(String numeroHabitacion);

    List<Asignacion> findByTareaCodigo(String codigoTarea);

    List<Asignacion> findByEmailCamarero(String emailCamarero);

    List<Asignacion> findByFechaProgramada(LocalDate fechaProgramada);

    List<Asignacion> findByEstado(String estado);

    List<Asignacion> findByPrioridad(Long prioridad);
}