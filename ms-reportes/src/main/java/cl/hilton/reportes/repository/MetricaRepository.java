package cl.hilton.reportes.repository;

import cl.hilton.reportes.model.Metrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MetricaRepository extends JpaRepository<Metrica, Integer> {

    List<Metrica> findByReporteCodigo(String codigoReporte);

    List<Metrica> findByPeriodo(LocalDate periodo);

    List<Metrica> findByPeriodoBetween(LocalDate desde, LocalDate hasta);

    List<Metrica> findByNombreMetrica(String nombreMetrica);

    Optional<Metrica> findByReporteCodigoAndPeriodoAndNombreMetrica(
            String codigoReporte,
            LocalDate periodo,
            String nombreMetrica
    );

    boolean existsByReporteCodigoAndPeriodoAndNombreMetrica(
            String codigoReporte,
            LocalDate periodo,
            String nombreMetrica
    );
}
