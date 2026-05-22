package cl.hilton.habitaciones.repository;

import cl.hilton.habitaciones.model.ProjTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjTarifaRepository extends JpaRepository<ProjTarifa, String> {

    Optional<ProjTarifa> findByTipoHabitacion(String tipoHabitacion);

    boolean existsByTipoHabitacion(String tipoHabitacion);
}