package cl.hilton.reportes.repository;

import cl.hilton.reportes.model.Metrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MetricaRepository extends JpaRepository<Metrica, Long> {

    List<Metrica> findByReporteCodigo(String codigoReporte);

    List<Metrica> findByPeriodo(LocalDate periodo);

    List<Metrica> findByPeriodoBetween(LocalDate desde, LocalDate hasta);

    List<Metrica> findByNombreMetrica(String nombreMetrica);

    boolean existsByReporteCodigoAndPeriodoAndNombreMetrica(
            String codigoReporte,
            LocalDate periodo,
            String nombreMetrica
    );
}