package cl.hilton.restaurante.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.restaurante.model.ProjHuesped;

@Repository
public interface ProjHuespedRepository extends JpaRepository<ProjHuesped, String> {

    Optional<ProjHuesped> findByEmail(String email);

    boolean existsByEmail(String email);

    List<ProjHuesped> findByNumeroHabitacion(String numeroHabitacion);

    List<ProjHuesped> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}
