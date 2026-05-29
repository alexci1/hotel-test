package cl.hilton.huespedes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.huespedes.model.Huesped;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Huesped> findByActivo(Boolean activo);

    List<Huesped> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);

    List<Huesped> findByTelefono(String telefono);

    List<Huesped> findByCreadoEn(LocalDate creadoEn);
}
