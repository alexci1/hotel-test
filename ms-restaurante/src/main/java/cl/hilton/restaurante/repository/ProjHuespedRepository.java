package cl.hilton.restaurante.repository;


import cl.hilton.restaurante.model.ProjHuesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjHuespedRepository extends JpaRepository<ProjHuesped, String> {

    boolean existsByEmail(String email);

    List<ProjHuesped> findByNumeroHabitacion(String numeroHabitacion);

    List<ProjHuesped> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}
