package cl.hilton.tarifas.repository;

import cl.hilton.tarifas.model.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {

    Optional<Descuento> findByCodigoDescuento(String codigoDescuento);

    boolean existsByCodigoDescuento(String codigoDescuento);

    List<Descuento> findByActivo(Boolean activo);

}
