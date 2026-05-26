package cl.hilton.pagos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.pagos.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    List<Cargo> findByFacturaNumeroFactura(String numeroFactura);

    List<Cargo> findByOrigen(String origen);

    List<Cargo> findByRegistradoEn(LocalDate registradoEn);
}
