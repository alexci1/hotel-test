package cl.hilton.reportes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.reportes.model.Kpi;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, Long> {

    Optional<Kpi> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Kpi> findByReporteCodigo(String codigoReporte);

    List<Kpi> findByPeriodo(String periodo);

    List<Kpi> findByUnidad(String unidad);

    List<Kpi> findByActualizadoEn(LocalDate actualizadoEn);

    List<Kpi> findByNombreContainingIgnoreCase(String nombre);
}