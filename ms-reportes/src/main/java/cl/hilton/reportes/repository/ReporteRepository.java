package cl.hilton.reportes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.reportes.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    Optional<Reporte> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Reporte> findByTipo(String tipo);

    List<Reporte> findByFrecuencia(String frecuencia);

    List<Reporte> findByActivo(Boolean activo);

    List<Reporte> findByNombreContainingIgnoreCase(String nombre);
}
