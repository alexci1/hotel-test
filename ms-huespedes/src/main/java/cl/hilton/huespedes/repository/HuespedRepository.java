package cl.hilton.huespedes.repository;

import cl.hilton.huespedes.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Huesped> findByActivo(Boolean activo);

    List<Huesped> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}