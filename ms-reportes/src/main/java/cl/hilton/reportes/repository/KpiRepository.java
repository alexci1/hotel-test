package cl.hilton.reportes.repository;

import cl.hilton.reportes.model.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, Integer> {

    Optional<Kpi> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Kpi> findByPeriodo(String periodo);

    List<Kpi> findByUnidad(String unidad);

    List<Kpi> findByNombreContainingIgnoreCase(String nombre);
}
