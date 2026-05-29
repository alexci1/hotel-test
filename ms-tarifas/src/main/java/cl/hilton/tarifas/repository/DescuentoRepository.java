package cl.hilton.tarifas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.tarifas.model.Descuento;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {

    Optional<Descuento> findByCodigoDescuento(String codigoDescuento);

    boolean existsByCodigoDescuento(String codigoDescuento);

    List<Descuento> findByAplicaA(String aplicaA);

    List<Descuento> findByActivo(Boolean activo);

    List<Descuento> findByValidoDesde(LocalDate validoDesde);

    List<Descuento> findByValidoHasta(LocalDate validoHasta);
}
