package cl.hilton.reportes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.reportes.model.Metrica;

@Repository
public interface MetricaRepository extends JpaRepository<Metrica, Long> {

    List<Metrica> findByReporteCodigo(String codigoReporte);

    List<Metrica> findByPeriodo(LocalDate periodo);

    List<Metrica> findByPeriodoBetween(LocalDate desde, LocalDate hasta);

    List<Metrica> findByNombreMetrica(String nombreMetrica);

    List<Metrica> findByCalculadoEn(LocalDate calculadoEn);

    boolean existsByReporteCodigoAndPeriodoAndNombreMetrica(
            String codigoReporte,
            LocalDate periodo,
            String nombreMetrica
    );
}
