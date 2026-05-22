package cl.hilton.notificaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.notificaciones.model.Plantilla;

@Repository
public interface PlantillaRepository extends JpaRepository<Plantilla, Long> {

    Optional<Plantilla> findByCodigo(String codigo);
}