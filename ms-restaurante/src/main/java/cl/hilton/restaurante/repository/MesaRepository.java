package cl.hilton.restaurante.repository;


import cl.hilton.restaurante.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    Optional<Mesa> findByNumeroMesa(String numeroMesa);

    boolean existsByNumeroMesa(String numeroMesa);

    List<Mesa> findByZona(String zona);

    List<Mesa> findByDisponible(Boolean disponible);

    List<Mesa> findByCapacidadGreaterThanEqual(Short capacidad);

    List<Mesa> findByZonaAndDisponible(String zona, Boolean disponible);
}