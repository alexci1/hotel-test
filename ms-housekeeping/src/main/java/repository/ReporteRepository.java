package cl.hilton.housekeeping.repository;

import cl.hilton.housekeeping.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    Optional<Reporte> findByAsignacionId(Long asignacionId);

    boolean existsByAsignacionId(Long asignacionId);

    List<Reporte> findByAprobado(Boolean aprobado);

    List<Reporte> findByInspector(String inspector);
}