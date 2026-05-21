package cl.hilton.tarifas.repository;

import cl.hilton.tarifas.model.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {

    Optional<Temporada> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Temporada> findByNombreContainingIgnoreCase(String nombre);

    List<Temporada> findByFechaInicioBefore(LocalDate fechaInicio);

    List<Temporada> findByFechaFinAfter(LocalDate fechaFin);

}