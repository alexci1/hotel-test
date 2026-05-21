package cl.hilton.reportes.repository;


import cl.hilton.reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    Optional<Reporte> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Reporte> findByTipo(String tipo);

    List<Reporte> findByFrecuencia(String frecuencia);

    List<Reporte> findByActivo(Boolean activo);

    List<Reporte> findByNombreContainingIgnoreCase(String nombre);
}