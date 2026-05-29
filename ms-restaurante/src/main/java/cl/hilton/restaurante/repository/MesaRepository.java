package cl.hilton.restaurante.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.restaurante.model.Mesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    Optional<Mesa> findByNumeroMesa(String numeroMesa);

    boolean existsByNumeroMesa(String numeroMesa);

    List<Mesa> findByZona(String zona);

    List<Mesa> findByDisponible(Boolean disponible);

    List<Mesa> findByCapacidadGreaterThanEqual(Integer capacidad);

    List<Mesa> findByZonaAndDisponible(String zona, Boolean disponible);
}
